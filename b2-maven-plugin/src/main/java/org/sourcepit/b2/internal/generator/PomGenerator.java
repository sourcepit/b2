/*
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
import java.util.Map.Entry;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Repository;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.common.internal.utils.NlsUtils;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.model.builder.util.IB2SessionService;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.IUnpackStrategy;
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

@Named
public class PomGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private ISourceService sourceManager;

   @Inject
   private IUnpackStrategy unpackStrategy;

   @Inject
   private IB2SessionService b2SessionService;

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
   protected void generate(final Annotatable inputElement, boolean skipFacets, final IConverter converter,
      final ITemplates templates)
   {
      if (skipFacets && inputElement instanceof AbstractFacet)
      {
         return;
      }

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
      return new ModuleModelSwitch<File>()
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

      final Annotation annotation = b2SessionService.getCurrentSession().getCurrentProject()
         .getAnnotation("b2.resolvedSites");

      if (annotation != null)
      {
         for (Entry<String, String> idToUrlEntry : annotation.getEntries())
         {
            final Repository repository = new Repository();
            repository.setId(idToUrlEntry.getKey());
            repository.setUrl(idToUrlEntry.getValue());
            repository.setLayout("p2");
            defaultModel.getRepositories().add(repository);
         }
      }

      NlsUtils.injectNlsProperties(defaultModel.getProperties(), targetDir, "module", "properties");

      addAdditionalProjectProperties(module, converter, defaultModel);

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
      final String classifier = project.getClassifier();
      defaultModel.getProperties().setProperty("classifier", classifier == null ? "" : classifier);

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
