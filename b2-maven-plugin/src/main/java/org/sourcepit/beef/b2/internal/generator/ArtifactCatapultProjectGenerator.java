/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.Profile;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.generator.GeneratorType;
import org.sourcepit.beef.b2.generator.IB2GenerationParticipant;
import org.sourcepit.beef.b2.model.builder.util.IB2SessionService;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.common.Annotatable;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.ProductDefinition;
import org.sourcepit.beef.b2.model.module.ProductsFacet;
import org.sourcepit.beef.b2.model.module.SiteProject;
import org.sourcepit.beef.b2.model.module.SitesFacet;
import org.sourcepit.beef.b2.model.session.Environment;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.maven.wrapper.internal.session.BootstrapSession;

/**
 * @author Bernd
 */
@Named
public class ArtifactCatapultProjectGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private BootstrapSession bootstrapSession;

   @Inject
   private IB2SessionService sessionService;

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(AbstractModule.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, IConverter converter, ITemplates templates)
   {
      // TODO ability to skip reactor projects
      if (converter.isSkipInterpolator())
      {
         return;
      }

      final AbstractModule module = (AbstractModule) inputElement;
      final File modulePom = resolvePomFile(module);

      final Model moduleModel = readMavenModel(modulePom);

      final IInterpolationLayout layout = getLayout(module.getLayoutId());

      final File projectDir = new File(layout.pathOfMetaDataFile(module, moduleModel.getArtifactId()
         + "-artifact-catapult"));
      projectDir.mkdirs();

      final File bootPom = new File(layout.pathOfMetaDataFile(module, "boot-pom.xml"));

      final Properties properties = new Properties();
      properties.setProperty("file", bootPom.getAbsolutePath());
      properties.setProperty("pomFile", bootPom.getAbsolutePath());

      final ArtifactRepository repo = bootstrapSession.getCurrentProject()
         .getDistributionManagementArtifactRepository();
      if (repo != null)
      {
         properties.setProperty("repositoryId", repo.getId());
         properties.setProperty("url", repo.getUrl());
      }

      final File pomFile = copyPomTemplate(templates, projectDir, properties);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(converter.getNameSpace());
      defaultModel.setArtifactId(projectDir.getName());

      final Model model = readMavenModel(pomFile);
      new FixedModelMerger().merge(model, defaultModel, false, null);

      Collection<ModuleArtifact> artifacts = gatherProductArtifacts(module);
      if (!artifacts.isEmpty())
      {
         Profile profile = new Profile();
         profile.setId("buildProducts");
         profile.setBuild(model.getBuild().clone());

         final List<Plugin> plugins = profile.getBuild().getPlugins();
         final Plugin installMojo = plugins.get(0);
         final Plugin deployMojo = plugins.get(1);

         configureInstallMojo(installMojo, moduleModel, artifacts);
         configureDeployMojo(deployMojo, artifacts);

         model.getProfiles().add(profile);
      }

      artifacts = gatherArtifacts(module);
      if (!artifacts.isEmpty())
      {
         final List<Plugin> plugins = model.getBuild().getPlugins();
         final Plugin installMojo = plugins.get(0);
         final Plugin deployMojo = plugins.get(1);

         configureInstallMojo(installMojo, moduleModel, artifacts);
         configureDeployMojo(deployMojo, artifacts);
      }

      final Collection<Dependency> dependencies = gatherDependencies(module);
      model.getDependencies().addAll(dependencies);

      connectMavenModels(modulePom, moduleModel, pomFile, model);
   }

   private void connectMavenModels(final File modulePom, final Model moduleModel, final File pomFile, final Model model)
   {
      setMavenParent(modulePom, moduleModel, pomFile, model);
      setMavenModule(modulePom, moduleModel, pomFile);
      writeMavenModel(pomFile, model);
      writeMavenModel(modulePom, moduleModel);
   }

   private void configureDeployMojo(final Plugin deployMojo, final Collection<ModuleArtifact> artifacts)
   {
      final StringBuilder files = new StringBuilder();
      final StringBuilder classifiers = new StringBuilder();
      final StringBuilder types = new StringBuilder();
      for (ModuleArtifact artifact : artifacts)
      {
         files.append(artifact.file.getAbsolutePath());
         files.append(',');
         classifiers.append(artifact.classifier == null ? "" : artifact.classifier);
         classifiers.append(',');
         types.append(artifact.type);
         types.append(',');
      }
      files.deleteCharAt(files.length() - 1);
      classifiers.deleteCharAt(classifiers.length() - 1);
      types.deleteCharAt(types.length() - 1);

      Xpp3Dom filesNode = new Xpp3Dom("files");
      filesNode.setValue(files.toString());

      Xpp3Dom classifiersNode = new Xpp3Dom("classifiers");
      classifiersNode.setValue(classifiers.toString());

      Xpp3Dom typesNode = new Xpp3Dom("types");
      typesNode.setValue(types.toString());

      Xpp3Dom deployConfig = (Xpp3Dom) deployMojo.getExecutions().get(0).getConfiguration();
      deployConfig.addChild(filesNode);
      deployConfig.addChild(classifiersNode);
      deployConfig.addChild(typesNode);
   }

   private void configureInstallMojo(final Plugin installMojo, final Model moduleModel,
      final Collection<ModuleArtifact> artifacts)
   {
      for (ModuleArtifact artifact : artifacts)
      {
         // create additional executions for install mojo
         final PluginExecution execution = installMojo.getExecutions().get(0).clone();
         final StringBuilder idBuilder = new StringBuilder();
         idBuilder.append("b2-install");
         if (artifact.classifier != null && artifact.classifier.length() > 0)
         {
            idBuilder.append('-');
            idBuilder.append(artifact.classifier);
         }
         if (artifact.type != null && artifact.type.length() > 0)
         {
            idBuilder.append('.');
            idBuilder.append(artifact.type);
         }
         execution.setId(idBuilder.toString());

         Xpp3Dom conf = new Xpp3Dom("configuration");
         execution.setConfiguration(conf);

         Xpp3Dom groupId = new Xpp3Dom("groupId");
         groupId.setValue(moduleModel.getGroupId());
         conf.addChild(groupId);

         Xpp3Dom artifactId = new Xpp3Dom("artifactId");
         artifactId.setValue(moduleModel.getArtifactId());
         conf.addChild(artifactId);

         Xpp3Dom version = new Xpp3Dom("version");
         version.setValue(moduleModel.getVersion());
         conf.addChild(version);

         if (artifact.classifier != null && artifact.classifier.length() > 0)
         {
            Xpp3Dom classifer = new Xpp3Dom("classifier");
            classifer.setValue(artifact.classifier);
            conf.addChild(classifer);
         }
         if (artifact.type != null && artifact.type.length() > 0)
         {
            Xpp3Dom type = new Xpp3Dom("packaging"); // parameter for "type" is named "packaging" in InstallFileMojo
            type.setValue(artifact.type);
            conf.addChild(type);
         }

         Xpp3Dom file = new Xpp3Dom("file");
         file.setValue(artifact.file.getAbsolutePath());
         conf.addChild(file);

         installMojo.getExecutions().add(execution);
      }
   }

   private Collection<Dependency> gatherDependencies(final AbstractModule module)
   {
      final Collection<Dependency> dependencies = new ArrayList<Dependency>();
      for (AbstractFacet abstractFacet : module.getFacets())
      {
         Dependency dependency = toDependency(abstractFacet);
         if (dependency != null)
         {
            dependencies.add(dependency);
         }

         for (EObject eObject : abstractFacet.eContents())
         {
            if (eObject instanceof Annotatable)
            {
               dependency = toDependency((Annotatable) eObject);
               if (dependency != null)
               {
                  dependencies.add(dependency);
               }
            }
         }
      }
      return dependencies;
   }

   private Dependency toDependency(Annotatable annotatable)
   {
      File pom = getPomFile(annotatable);
      if (pom != null)
      {
         Model model = readMavenModel(pom);
         Dependency dependency = new Dependency();
         dependency.setGroupId(model.getGroupId());
         dependency.setArtifactId(model.getArtifactId());
         dependency.setVersion(model.getVersion());
         dependency.setType("pom");
         return dependency;
      }
      return null;
   }

   private File copyPomTemplate(ITemplates templates, final File projectDir, Properties properties)
   {
      try
      {
         final File pomFile = new File(projectDir, "catapult-pom.xml");
         templates.copy(pomFile.getName(), pomFile.getParentFile(), properties);

         final File destFile = new File(pomFile.getParentFile(), "pom.xml");
         FileUtils.copyFile(pomFile, destFile);
         FileUtils.forceDelete(pomFile);

         return destFile;
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   private Collection<ModuleArtifact> gatherArtifacts(final AbstractModule module)
   {
      final List<ModuleArtifact> artifacts = new ArrayList<ModuleArtifact>();

      final IInterpolationLayout layout = getLayout(module.getLayoutId());

      final ModuleArtifact sessionModel = new ModuleArtifact();
      sessionModel.file = new File(layout.pathOfMetaDataFile(module, "b2.session"));
      // sessionModel.classifier = "b2";
      sessionModel.type = "session";
      artifacts.add(sessionModel);

      int artifactCount = artifacts.size();

      for (SitesFacet sitesFacet : module.getFacets(SitesFacet.class))
      {
         for (SiteProject siteProject : sitesFacet.getProjects())
         {
            if (siteProject.getClassifier() != null && siteProject.getClassifier().length() > 0)
            {
               final String cl = siteProject.getClassifier();

               final String clString = cl == null || cl.length() == 0 ? "" : ("-" + cl);

               final File modelFile = new File(layout.pathOfMetaDataFile(module, "b2" + clString + ".module"));

               final ModuleArtifact classifiedModel = new ModuleArtifact();
               classifiedModel.file = modelFile;
               classifiedModel.classifier = cl;
               classifiedModel.type = "module";
               artifacts.add(classifiedModel);
            }
         }
      }

      // add default model if no site classifiers are specified
      if (artifactCount == artifacts.size())
      {
         final ModuleArtifact moduleModel = new ModuleArtifact();
         moduleModel.file = new File(layout.pathOfMetaDataFile(module, "b2.module"));
         // moduleModel.classifier = "b2";
         moduleModel.type = "module";
         artifacts.add(moduleModel);
      }

      gatherSiteArtifacts(module, artifacts);

      return artifacts;
   }

   private void gatherSiteArtifacts(final AbstractModule module, final List<ModuleArtifact> artifacts)
   {
      for (SitesFacet sitesFacet : module.getFacets(SitesFacet.class))
      {
         for (SiteProject siteProject : sitesFacet.getProjects())
         {
            final ModuleArtifact siteArtifact = new ModuleArtifact();
            siteArtifact.file = new File(siteProject.getDirectory(), "target/" + siteProject.getId() + ".zip");
            String cl = siteProject.getClassifier();
            siteArtifact.classifier = cl == null || cl.length() == 0 ? "site" : "site-" + cl;
            siteArtifact.type = "zip";
            artifacts.add(siteArtifact);
         }
      }
   }

   private List<ModuleArtifact> gatherProductArtifacts(final AbstractModule module)
   {
      final List<ModuleArtifact> artifacts = new ArrayList<ModuleArtifact>();

      ModuleProject project = sessionService.getCurrentSession().getCurrentProject();

      final List<String> envAppendixes = new ArrayList<String>();

      for (Environment environment : project.getEnvironements())
      {
         StringBuilder sb = new StringBuilder();
         if (environment.getOs() != null)
         {
            sb.append(environment.getOs());
            sb.append('.');
         }
         if (environment.getWs() != null)
         {
            sb.append(environment.getWs());
            sb.append('.');
         }
         if (environment.getArch() != null)
         {
            sb.append(environment.getArch());
            sb.append('.');
         }
         if (sb.length() > 0)
         {
            sb.deleteCharAt(sb.length() - 1);
            sb.insert(0, '-');
         }

         envAppendixes.add(sb.toString());
      }

      for (ProductsFacet productsFacet : module.getFacets(ProductsFacet.class))
      {
         for (ProductDefinition product : productsFacet.getProductDefinitions())
         {
            String classifierPrefix = ProductProjectGenerator.getClassifier(product.getFile().getName());
            if (classifierPrefix == null)
            {
               classifierPrefix = "";
            }
            else
            {
               classifierPrefix = classifierPrefix + ".";
            }

            final IInterpolationLayout layout = layoutMap.get(module.getLayoutId());
            final String uid = product.getAnnotationEntry("product", "uid");
            final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
            for (String envAppendix : envAppendixes)
            {
               // target/products/de.visualrules.modeler-linux.gtk.x86.zip
               final File file = new File(projectDir, "target/products/" + uid + envAppendix + ".zip");

               String classifierApendix = envAppendix.length() > 0 ? envAppendix.substring(1) : envAppendix;

               final ModuleArtifact productArtifact = new ModuleArtifact();
               productArtifact.file = file;
               productArtifact.classifier = classifierPrefix + classifierApendix;
               productArtifact.type = "zip";
               artifacts.add(productArtifact);
            }
         }
      }

      return artifacts;
   }

   private IInterpolationLayout getLayout(final String layoutId)
   {
      final IInterpolationLayout layout = layoutMap.get(layoutId);
      if (layout == null)
      {
         throw new UnsupportedOperationException("Layout " + layoutId + " is not supported.");
      }
      return layout;
   }

   private class ModuleArtifact
   {
      public File file;
      public String classifier, type;

      public ModuleArtifact()
      {
         super();
      }
   }

}
