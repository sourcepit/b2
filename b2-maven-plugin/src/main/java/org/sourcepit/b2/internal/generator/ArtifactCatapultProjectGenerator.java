/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

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
import org.apache.maven.plugin.LegacySupport;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.builder.util.IB2SessionService;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.session.Environment;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.base.Strings;

/**
 * @author Bernd
 */
@Named
public class ArtifactCatapultProjectGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private IB2SessionService sessionService;

   @Inject
   private LegacySupport legacySupport;

   @Inject
   private BasicConverter basicConverter;

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
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource propertie,
      ITemplates templates)
   {
      // TODO ability to skip reactor projects
      if (basicConverter.isSkipInterpolator(propertie))
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

      final ArtifactRepository repo = legacySupport.getSession().getCurrentProject()
         .getDistributionManagementArtifactRepository();
      if (repo != null)
      {
         properties.setProperty("repositoryId", repo.getId());
         properties.setProperty("url", repo.getUrl());
      }

      final File pomFile = copyPomTemplate(templates, projectDir, properties);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(basicConverter.getNameSpace(propertie));
      defaultModel.setArtifactId(projectDir.getName());

      final Model model = readMavenModel(pomFile);
      new FixedModelMerger().merge(model, defaultModel, false, null);

      Collection<ModuleArtifact> artifacts = gatherProductArtifacts(module);
      if (!artifacts.isEmpty())
      {
         artifacts.addAll(gatherArtifacts(module, propertie));

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

      artifacts = gatherArtifacts(module, propertie);
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
         files.append(artifact.getFile().getAbsolutePath());
         files.append(',');
         classifiers.append(artifact.getClassifier() == null ? "" : artifact.getClassifier());
         classifiers.append(',');
         types.append(artifact.getType());
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
         if (artifact.getClassifier() != null && artifact.getClassifier().length() > 0)
         {
            idBuilder.append('-');
            idBuilder.append(artifact.getClassifier());
         }
         if (artifact.getType() != null && artifact.getType().length() > 0)
         {
            idBuilder.append('.');
            idBuilder.append(artifact.getType());
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

         if (artifact.getClassifier() != null && artifact.getClassifier().length() > 0)
         {
            Xpp3Dom classifer = new Xpp3Dom("classifier");
            classifer.setValue(artifact.getClassifier());
            conf.addChild(classifer);
         }
         if (artifact.getType() != null && artifact.getType().length() > 0)
         {
            Xpp3Dom type = new Xpp3Dom("packaging"); // parameter for "type" is named "packaging" in InstallFileMojo
            type.setValue(artifact.getType());
            conf.addChild(type);
         }

         Xpp3Dom file = new Xpp3Dom("file");
         file.setValue(artifact.getFile().getAbsolutePath());
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

   private Collection<ModuleArtifact> gatherArtifacts(final AbstractModule module, PropertiesSource properties)
   {
      final List<ModuleArtifact> artifacts = new ArrayList<ModuleArtifact>();

      final IInterpolationLayout layout = getLayout(module.getLayoutId());

      final ModuleArtifact sessionModel = new ModuleArtifact();
      sessionModel.setFile(new File(layout.pathOfMetaDataFile(module, "b2.session")));
      sessionModel.setType("session");
      artifacts.add(sessionModel);

      final ModuleArtifact moduleModel = new ModuleArtifact();
      moduleModel.setFile(new File(layout.pathOfMetaDataFile(module, "b2.module")));
      moduleModel.setType("module");
      artifacts.add(moduleModel);

      gatherSiteArtifacts(module, artifacts, properties);

      return artifacts;
   }

   private void gatherSiteArtifacts(final AbstractModule module, final List<ModuleArtifact> artifacts,
      PropertiesSource properties)
   {
      for (SitesFacet sitesFacet : module.getFacets(SitesFacet.class))
      {
         for (SiteProject siteProject : sitesFacet.getProjects())
         {
            String version = siteProject.getVersion();

            String mavenVersion = VersionUtils.toMavenVersion(version);

            final ModuleArtifact siteArtifact = new ModuleArtifact();
            siteArtifact.setFile(new File(siteProject.getDirectory(), "target/" + siteProject.getId() + "-"
               + mavenVersion + ".zip"));
            String cl = SiteProjectGenerator.getAssemblyClassifier(siteProject);
            siteArtifact.setClassifier(cl == null || cl.length() == 0 ? "site" : "site-" + cl);
            siteArtifact.setType("zip");
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
         envAppendixes.add(toEnvAppendix(environment));
      }

      for (ProductsFacet productsFacet : module.getFacets(ProductsFacet.class))
      {
         for (ProductDefinition product : productsFacet.getProductDefinitions())
         {
            String classifierPrefix = ProductProjectGenerator.getAssemblyClassifier(product.getFile().getName());
            if (Strings.isNullOrEmpty(classifierPrefix))
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
               productArtifact.setFile(file);
               productArtifact.setClassifier(classifierPrefix + classifierApendix);
               productArtifact.setType("zip");
               artifacts.add(productArtifact);
            }
         }
      }

      return artifacts;
   }

   /**
    * @param environment
    * @return
    */
   private String toEnvAppendix(Environment environment)
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

      String evnAppendix = sb.toString();
      return evnAppendix;
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

}
