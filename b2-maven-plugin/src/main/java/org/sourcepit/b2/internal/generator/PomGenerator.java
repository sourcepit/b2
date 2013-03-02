/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.common.utils.io.IOResources.cpIn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Repository;
import org.apache.maven.plugin.LegacySupport;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.builder.util.IB2SessionService;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.util.ModuleModelSwitch;
import org.sourcepit.common.utils.io.IOOperation;
import org.sourcepit.common.utils.nls.NlsUtils;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class PomGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private ISourceService sourceManager;

   @Inject
   private UnpackStrategy unpackStrategy;

   @Inject
   private IB2SessionService b2SessionService;

   @Inject
   private BasicConverter basicConverter;

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.MODULE_RESOURCE_GENERATOR;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(ProductDefinition.class);
      inputTypes.add(Project.class);
      inputTypes.add(AbstractFacet.class);
      inputTypes.add(AbstractModule.class);
   }

   @Override
   protected void generate(final Annotatable inputElement, boolean skipFacets, final PropertiesSource properties,
      final ITemplates templates)
   {
      if (skipFacets && inputElement instanceof AbstractFacet)
      {
         return;
      }

      File pomFile = doGenerate(inputElement, properties, templates);

      if (inputElement instanceof Project)
      {
         final Annotation additionalProps = inputElement.getAnnotation("project.properties");
         if (additionalProps != null && !additionalProps.getEntries().isEmpty())
         {
            final Model model = readMavenModel(pomFile);
            for (Entry<String, String> property : additionalProps.getEntries())
            {
               model.getProperties().setProperty(property.getKey(), property.getValue());
            }
            writeMavenModel(pomFile, model);
         }
      }

      final File projectPomFile = new File(pomFile.getParentFile(), "b2-extension.xml");
      if (projectPomFile.exists())
      {
         final Model projectModel = readMavenModel(projectPomFile);
         projectModel.setPackaging(null);
         mergeIntoPomFile(pomFile, projectModel, true);
      }

      if (!pomFile.getName().equals("pom.xml"))
      {
         try
         {
            File destFile = new File(pomFile.getParentFile(), "pom.xml");
            FileUtils.copyFile(pomFile, destFile);
            FileUtils.forceDelete(pomFile);
            pomFile = destFile;
         }
         catch (IOException e)
         {
            throw new IllegalStateException(e);
         }
      }

      inputElement.putAnnotationEntry(SOURCE_MAVEN, KEY_POM_FILE, pomFile.toString());
   }

   protected File doGenerate(final EObject inputElement, final PropertiesSource properties, final ITemplates templates)
   {
      final ModuleModelSwitch<File> modelSwitch = new ModuleModelSwitch<File>()
      {
         @Override
         public File casePluginProject(PluginProject project)
         {
            return generatePluginProject(project, properties, templates);
         }

         @Override
         public File caseFeatureProject(FeatureProject project)
         {
            return generateFeatureProject(project, properties, templates);
         }

         @Override
         public File caseSiteProject(SiteProject project)
         {
            return generateSiteProject(project, properties, templates);
         }

         public File caseProductDefinition(ProductDefinition project)
         {
            return generateProductProject(project, properties, templates);
         };

         public File caseAbstractFacet(AbstractFacet facet)
         {
            return generateFacet(facet, properties, templates);
         };

         public File caseAbstractModule(AbstractModule module)
         {
            return generateModule(module, properties, templates);
         };

         @Override
         public File defaultCase(EObject object)
         {
            throw new UnsupportedOperationException("Input type '" + object.getClass()
               + "' is currently not supported.");
         }
      };
      return modelSwitch.doSwitch(inputElement);
   }

   protected File generateProductProject(ProductDefinition product, PropertiesSource source, ITemplates templates)
   {
      final AbstractModule module = product.getParent().getParent();
      final IInterpolationLayout layout = layoutMap.get(module.getLayoutId());

      final String uid = product.getAnnotationEntry("product", "uid");
      final String version = product.getAnnotationEntry("product", "version");

      final File targetDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      targetDir.mkdirs();

      final File pomFile = new File(targetDir, "product-pom.xml");

      Properties properties = new Properties();
      properties.setProperty("product.uid", uid);
      properties.setProperty("product.id", product.getAnnotationEntry("product", "id"));
      properties.setProperty("product.applications", product.getAnnotationEntry("product", "application"));
      copyPomTemplate(templates, pomFile);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.getProperties().putAll(properties);
      defaultModel.setGroupId(basicConverter.getNameSpace(source));
      defaultModel.setArtifactId(uid);
      defaultModel.setVersion(VersionUtils.toMavenVersion(version == null ? module.getVersion() : version));
      defaultModel.setPackaging("eclipse-repository");

      mergeIntoPomFile(pomFile, defaultModel);
      return pomFile;
   }

   protected File generateModule(AbstractModule module, PropertiesSource properties, ITemplates templates)
   {
      final File targetDir = module.getDirectory();

      final File tmpPomFile = new File(targetDir, "module-pom.xml");
      copyPomTemplate(templates, tmpPomFile);

      // rename module pom do default poms name that we can start maven just with 'mvn <goals>'
      final File pomFile = new File(targetDir, "pom.xml");
      moveFile(tmpPomFile, pomFile);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(basicConverter.getNameSpace(properties));

      defaultModel.setArtifactId(getArtifactIdForModule(module, properties));
      defaultModel.setVersion(VersionUtils.toMavenVersion(module.getVersion()));
      defaultModel.setPackaging("pom");

      @SuppressWarnings("unchecked")
      final Map<String, String> sites = (Map<String, String>) buildContext.getSession().getCurrentProject()
         .getContextValue("b2.resolvedSites");

      if (sites != null)
      {
         for (Entry<String, String> idToUrlEntry : sites.entrySet())
         {
            final Repository repository = new Repository();
            repository.setId(idToUrlEntry.getKey());
            repository.setUrl(idToUrlEntry.getValue());
            repository.setLayout("p2");
            defaultModel.getRepositories().add(repository);
         }
      }

      NlsUtils.injectNlsProperties(defaultModel.getProperties(), targetDir, "module", "properties");

      addAdditionalProjectProperties(module, properties, defaultModel);

      final Model moduleModel;

      final String path = module.getAnnotationEntry("maven", "modulePomTemplate");
      if (path != null)
      {
         moduleModel = readMavenModel(new File(path));
      }
      else
      {
         moduleModel = readMavenModel(new File(targetDir, "module.xml"));
      }

      final List<Dependency> moduleDependencies = new ArrayList<Dependency>();
      for (Dependency dependency : moduleModel.getDependencies())
      {
         if ("module".equals(dependency.getType()))
         {
            moduleDependencies.add(dependency);
         }
      }
      moduleModel.getDependencies().removeAll(moduleDependencies);

      mergeIntoPomFile(pomFile, defaultModel);
      mergeIntoPomFile(pomFile, moduleModel, true);

      return pomFile;
   }

   private void addAdditionalProjectProperties(AbstractModule module, PropertiesSource properties, Model defaultModel)
   {
      final Properties b2Props = new Properties();
      new IOOperation<InputStream>(cpIn(getClass().getClassLoader(), "META-INF/b2/b2.properties"))
      {
         @Override
         protected void run(InputStream openResource) throws IOException
         {
            b2Props.load(openResource);
         }
      }.run();
      final String b2Version = b2Props.getProperty("b2.version");
      if (b2Version == null)
      {
         throw new IllegalStateException("Unable to determine b2.version");
      }
      defaultModel.getProperties().putAll(b2Props);

      final List<String> sonarExcludes = new ArrayList<String>();
      collectSonarExcludes(module, properties, sonarExcludes);
      if (sonarExcludes.size() > 0)
      {
         StringBuilder sb = new StringBuilder();
         for (String included : sonarExcludes)
         {
            sb.append(',');
            sb.append(included);
         }
         sb.deleteCharAt(0);
         defaultModel.getProperties().setProperty("sonar.skippedModules", sb.toString());
      }
   }

   private void collectSonarExcludes(AbstractModule module, PropertiesSource properties,
      final List<String> sonarExcluded)
   {
      for (PluginsFacet pluginsFacet : module.getFacets(PluginsFacet.class))
      {
         for (PluginProject pluginProject : pluginsFacet.getProjects())
         {
            if (pluginProject.isTestPlugin())
            {
               sonarExcluded.add(pluginProject.getId());
            }
         }
      }

      // if (module instanceof CompositeModule)
      // {
      // final CompositeModule composite = (CompositeModule) module;
      // for (AbstractModule abstractModule : composite.getModules())
      // {
      // collectSonarExcludes(abstractModule, properties, sonarExcluded);
      // }
      // }
   }

   @Inject
   private LegacySupport buildContext;

   private String getArtifactIdForModule(AbstractModule module, PropertiesSource propertie)
   {
      return buildContext.getSession().getCurrentProject().getArtifactId();
   }

   private void moveFile(final File srcFile, final File destFile)
   {
      try
      {
         if (destFile.exists())
         {
            FileUtils.forceDelete(destFile);
         }
         FileUtils.moveFile(srcFile, destFile);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   protected File generateFacet(AbstractFacet facet, PropertiesSource properties, ITemplates templates)
   {
      final AbstractModule module = facet.getParent();

      final IInterpolationLayout layout = layoutMap.get(module.getLayoutId());

      File tmpPomFile = new File(layout.pathOfFacetMetaData(module, facet.getName(), facet.getName() + "-pom.xml"));

      try
      {
         copyPomTemplate(templates, tmpPomFile);
      }
      catch (IllegalArgumentException e)
      {
         tmpPomFile = new File(layout.pathOfFacetMetaData(module, facet.getName(), "facet-pom.xml"));
         copyPomTemplate(templates, tmpPomFile);

         final File pomFile = new File(tmpPomFile.getParentFile(), facet.getName() + "-pom.xml");
         moveFile(tmpPomFile, pomFile);
         tmpPomFile = pomFile;
      }

      final File pomFile = tmpPomFile;

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(basicConverter.getNameSpace(properties));
      defaultModel.setArtifactId(getArtifactIdForFacet(facet, properties));
      defaultModel.setVersion(VersionUtils.toMavenVersion(facet.getParent().getVersion()));
      defaultModel.setPackaging("pom");

      mergeIntoPomFile(pomFile, defaultModel);

      return pomFile;
   }

   private String getArtifactIdForFacet(AbstractFacet facet, PropertiesSource properties)
   {
      final String moduleArtifactId = getArtifactIdForModule(facet.getParent(), properties);
      final String separator = moduleArtifactId.indexOf('.') > -1 ? "." : "-";
      return moduleArtifactId + separator + facet.getName();
   }

   protected File generateSiteProject(SiteProject project, PropertiesSource properties, ITemplates templates)
   {
      final File targetDir = project.getDirectory();

      final File pomFile = new File(targetDir, "site-pom.xml");
      copyPomTemplate(templates, pomFile);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(basicConverter.getNameSpace(properties));
      defaultModel.setArtifactId(project.getId());
      defaultModel.setVersion(VersionUtils.toMavenVersion(project.getVersion()));
      defaultModel.setPackaging("eclipse-repository");
      final String classifier = SiteProjectGenerator.getAssemblyClassifier(project);
      defaultModel.getProperties().setProperty("classifier", classifier == null ? "" : classifier);

      mergeIntoPomFile(pomFile, defaultModel);

      return pomFile;
   }

   protected File generateFeatureProject(FeatureProject project, PropertiesSource properties, ITemplates templates)
   {
      final File targetDir = project.getDirectory();

      final File pomFile = new File(targetDir, "feature-pom.xml");
      copyPomTemplate(templates, pomFile);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(basicConverter.getNameSpace(properties));
      defaultModel.setArtifactId(project.getId());
      defaultModel.setVersion(VersionUtils.toMavenVersion(project.getVersion()));
      defaultModel.setPackaging("eclipse-feature");

      mergeIntoPomFile(pomFile, defaultModel);

      return pomFile;
   }

   protected File generatePluginProject(PluginProject project, PropertiesSource properties, ITemplates templates)
   {
      final File targetDir = project.getDirectory();

      final String groupId = basicConverter.getNameSpace(properties);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(groupId);
      defaultModel.setArtifactId(project.getId());
      defaultModel.setVersion(VersionUtils.toMavenVersion(project.getVersion()));
      if (project.isTestPlugin())
      {
         defaultModel.setPackaging("eclipse-test-plugin");
      }
      else
      {
         defaultModel.setPackaging("eclipse-plugin");
      }

      defaultModel.getProperties().setProperty("bundle.symbolicName", project.getId());
      defaultModel.getProperties().setProperty("bundle.version", project.getVersion());

      final String requiresUI = project.getAnnotationEntry("UI", "required");
      defaultModel.getProperties().setProperty("bundle.requiresUI", requiresUI == null ? "false" : requiresUI);

      if (unpackStrategy.isUnpack(project))
      {
         List<String> buildJars = unpackStrategy.getBuildJars(project);
         if (buildJars.size() > 0)
         {
            // TODO handle multi jars
            String jarRelativePath = buildJars.get(0);

            if (defaultModel.getBuild() == null)
            {
               defaultModel.setBuild(new Build());
            }
            defaultModel.getBuild().setOutputDirectory("${project.build.directory}/" + jarRelativePath + "-classes");
         }
      }

      Properties p = new Properties();
      p.put("generate.sources", String.valueOf(sourceManager.isSourceBuildEnabled(project, properties)));

      final File pomFile = new File(targetDir, project.isTestPlugin() ? "test-plugin-pom.xml" : "plugin-pom.xml");
      copyPomTemplate(templates, pomFile, p);

      mergeIntoPomFile(pomFile, defaultModel);

      return pomFile;
   }

   private void copyPomTemplate(ITemplates templates, File pomFile)
   {
      copyPomTemplate(templates, pomFile, null);
   }

   private void copyPomTemplate(ITemplates templates, File pomFile, Properties properties)
   {
      templates.copy(pomFile.getName(), pomFile.getParentFile(), properties);
   }

   private void mergeIntoPomFile(final File pomFile, final Model model, boolean force)
   {
      final Model mavenModel = readMavenModel(pomFile);
      new FixedModelMerger().merge(mavenModel, model, force, null);
      writeMavenModel(pomFile, mavenModel);
   }

   private void mergeIntoPomFile(final File pomFile, final Model model)
   {
      mergeIntoPomFile(pomFile, model, false);
   }
}
