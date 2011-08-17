/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
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
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.common.internal.utils.NlsUtils;
import org.sourcepit.beef.b2.generator.GeneratorType;
import org.sourcepit.beef.b2.generator.IB2GenerationParticipant;
import org.sourcepit.beef.b2.internal.model.AbstractFacet;
import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.internal.model.Annotateable;
import org.sourcepit.beef.b2.internal.model.FeatureProject;
import org.sourcepit.beef.b2.internal.model.PluginProject;
import org.sourcepit.beef.b2.internal.model.PluginsFacet;
import org.sourcepit.beef.b2.internal.model.ProductDefinition;
import org.sourcepit.beef.b2.internal.model.Project;
import org.sourcepit.beef.b2.internal.model.SiteProject;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.ISourceManager;
import org.sourcepit.beef.b2.model.builder.util.IUnpackStrategy;
import org.sourcepit.beef.b2.model.internal.util.B2ModelSwitch;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;

@Named
public class PomGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Inject
   @Named("wrapper")
   private MavenProject wrapperProject;

   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private ISourceManager sourceManager;

   @Inject
   private IUnpackStrategy unpackStrategy;

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
   protected void generate(final Annotateable inputElement, final IConverter converter, final ITemplates templates)
   {
      File pomFile = doGenerate(inputElement, converter, templates);

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

   protected File doGenerate(final EObject inputElement, final IConverter converter, final ITemplates templates)
   {
      return new B2ModelSwitch<File>()
      {
         @Override
         public File casePluginProject(PluginProject project)
         {
            return generatePluginProject(project, converter, templates);
         }

         @Override
         public File caseFeatureProject(FeatureProject project)
         {
            return generateFeatureProject(project, converter, templates);
         }

         @Override
         public File caseSiteProject(SiteProject project)
         {
            return generateSiteProject(project, converter, templates);
         }

         public File caseProductDefinition(ProductDefinition project)
         {
            return generateProductProject(project, converter, templates);
         };

         public File caseAbstractFacet(AbstractFacet facet)
         {
            return generateFacet(facet, converter, templates);
         };

         public File caseAbstractModule(AbstractModule module)
         {
            return generateModule(module, converter, templates);
         };

         @Override
         public File defaultCase(EObject object)
         {
            throw new UnsupportedOperationException("Input type '" + object.getClass()
               + "' is currently not supported.");
         }
      }.doSwitch(inputElement);
   }

   protected File generateProductProject(ProductDefinition product, IConverter converter, ITemplates templates)
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
      defaultModel.setGroupId(converter.getNameSpace());
      defaultModel.setArtifactId(uid);
      defaultModel.setVersion(VersionUtils.toMavenVersion(version == null ? module.getVersion() : version));
      defaultModel.setPackaging("eclipse-repository");

      mergeIntoPomFile(pomFile, defaultModel);
      return pomFile;
   }

   protected File generateModule(AbstractModule module, IConverter converter, ITemplates templates)
   {
      final File targetDir = module.getDirectory();


      final File _pomFile = new File(targetDir, "module-pom.xml");
      copyPomTemplate(templates, _pomFile);

      // rename module pom do default poms name that we can start maven just with 'mvn <goals>'
      final File pomFile = new File(targetDir, "pom.xml");
      moveFile(_pomFile, pomFile);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(converter.getNameSpace());

      defaultModel.setArtifactId(getArtifactIdForModule(module, converter));
      defaultModel.setVersion(VersionUtils.toMavenVersion(module.getVersion()));
      defaultModel.setPackaging("pom");

      NlsUtils.injectNlsProperties(defaultModel.getProperties(), targetDir, "module", "properties");

      addAdditionalProjectProperties(module, converter, defaultModel);

      final Model moduleModel = readMavenModel(wrapperProject.getFile());

      new FixedModelMerger().merge(defaultModel, moduleModel, false, null);

      mergeIntoPomFile(pomFile, defaultModel);

      return pomFile;
   }

   private void addAdditionalProjectProperties(AbstractModule module, IConverter converter, Model defaultModel)
   {
      final List<String> sonarExcludes = new ArrayList<String>();
      collectSonarExcludes(module, converter, sonarExcludes);
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

   private void collectSonarExcludes(AbstractModule module, IConverter converter, final List<String> sonarExcluded)
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
      // collectSonarExcludes(abstractModule, converter, sonarExcluded);
      // }
      // }
   }

   private String getArtifactIdForModule(AbstractModule module, IConverter converter)
   {
      final String artifactId;
      if (module.getId().startsWith(converter.getNameSpace() + "."))
      {
         artifactId = module.getId().substring(converter.getNameSpace().length() + 1);
      }
      else
      {
         artifactId = module.getId();
      }
      return artifactId;
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

   protected File generateFacet(AbstractFacet facet, IConverter converter, ITemplates templates)
   {
      final AbstractModule module = facet.getParent();

      final IInterpolationLayout layout = layoutMap.get(module.getLayoutId());

      File _pomFile = new File(layout.pathOfFacetMetaData(module, facet.getName(), facet.getName() + "-pom.xml"));

      try
      {
         copyPomTemplate(templates, _pomFile);
      }
      catch (IllegalArgumentException e)
      {
         _pomFile = new File(layout.pathOfFacetMetaData(module, facet.getName(), "facet-pom.xml"));
         copyPomTemplate(templates, _pomFile);

         final File pomFile = new File(_pomFile.getParentFile(), facet.getName() + "-pom.xml");
         moveFile(_pomFile, pomFile);
         _pomFile = pomFile;
      }

      final File pomFile = _pomFile;

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(converter.getNameSpace());
      defaultModel.setArtifactId(getArtifactIdForFacet(facet, converter));
      defaultModel.setVersion(VersionUtils.toMavenVersion(facet.getParent().getVersion()));
      defaultModel.setPackaging("pom");

      mergeIntoPomFile(pomFile, defaultModel);

      return pomFile;
   }

   private String getArtifactIdForFacet(AbstractFacet facet, IConverter converter)
   {
      return getArtifactIdForModule(facet.getParent(), converter) + "-" + facet.getName();
   }

   protected File generateSiteProject(SiteProject project, IConverter converter, ITemplates templates)
   {
      final File targetDir = project.getDirectory();

      final File pomFile = new File(targetDir, "site-pom.xml");
      copyPomTemplate(templates, pomFile);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(converter.getNameSpace());
      defaultModel.setArtifactId(project.getId());
      defaultModel.setVersion(VersionUtils.toMavenVersion(project.getVersion()));
      defaultModel.setPackaging("eclipse-repository");

      mergeIntoPomFile(pomFile, defaultModel);

      return pomFile;
   }

   protected File generateFeatureProject(FeatureProject project, IConverter converter, ITemplates templates)
   {
      final File targetDir = project.getDirectory();

      final File pomFile = new File(targetDir, "feature-pom.xml");
      copyPomTemplate(templates, pomFile);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(converter.getNameSpace());
      defaultModel.setArtifactId(project.getId());
      defaultModel.setVersion(VersionUtils.toMavenVersion(project.getVersion()));
      defaultModel.setPackaging("eclipse-feature");

      mergeIntoPomFile(pomFile, defaultModel);

      return pomFile;
   }

   protected File generatePluginProject(PluginProject project, IConverter converter, ITemplates templates)
   {
      final File targetDir = project.getDirectory();

      Properties p = new Properties();
      p.put("generate.sources", String.valueOf(sourceManager.isSourceBuildEnabled(project, converter)));
      p.put("bundle.symbolicName", project.getId());

      final File pomFile = new File(targetDir, project.isTestPlugin() ? "test-plugin-pom.xml" : "plugin-pom.xml");
      copyPomTemplate(templates, pomFile, p);

      final Model defaultModel = new Model();
      defaultModel.setModelVersion("4.0.0");
      defaultModel.setGroupId(converter.getNameSpace());
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

   private void mergeIntoPomFile(final File pomFile, final Model defaultModel)
   {
      final Model mavenModel = readMavenModel(pomFile);
      new FixedModelMerger().merge(mavenModel, defaultModel, false, null);
      writeMavenModel(pomFile, mavenModel);
   }
}
