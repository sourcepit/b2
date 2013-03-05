/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.p2;

import static com.google.common.collect.Collections2.filter;
import static org.sourcepit.b2.internal.maven.util.MavenDepenenciesUtils.removeDependencies;
import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Repository;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.internal.generator.AbstractPomGenerator;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.interpolation.module.ModuleInterpolatorLifecycleParticipant;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.common.utils.props.AbstractPropertiesSource;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.osgify.core.model.context.BundleCandidate;
import org.sourcepit.osgify.core.model.context.OsgifyContext;
import org.sourcepit.osgify.maven.p2.P2UpdateSiteGenerator;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

@Named
public class MavenDependenciesSiteGenerator extends AbstractPomGenerator
   implements
      ModuleInterpolatorLifecycleParticipant,
      IB2GenerationParticipant
{

   private Predicate<Dependency> JARS = new Predicate<Dependency>()
   {
      public boolean apply(Dependency dependency)
      {
         return "jar".equals(dependency.getType());
      }
   };

   @Inject
   private LegacySupport legacySupport;

   @Inject
   private P2UpdateSiteGenerator updateSiteGenerator;

   @Inject
   private LayoutManager layoutManager;

   public void preInterpolation(AbstractModule module, final PropertiesSource moduleProperties)
   {
      final MavenSession session = legacySupport.getSession();
      final MavenProject project = session.getCurrentProject();

      final List<Dependency> dependencies = new ArrayList<Dependency>(filter(project.getDependencies(), JARS));
      if (!dependencies.isEmpty())
      {
         final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
         final File siteDir = new File(layout.pathOfMetaDataFile(module, "maven-dependencies"));

         final String repositoryName = "maven-dependencies@" + project.getArtifactId();
         final List<ArtifactRepository> remoteRepositories = project.getRemoteArtifactRepositories();
         final ArtifactRepository localRepository = session.getLocalRepository();

         final PropertiesSource options = new AbstractPropertiesSource()
         {
            public String get(String key)
            {
               return moduleProperties.get("b2.osgify." + key);
            }
         };

         final OsgifyContext osgifyContext = updateSiteGenerator.generateUpdateSite(siteDir, dependencies, true,
            remoteRepositories, localRepository, repositoryName, options);

         try
         {
            module
               .putAnnotationEntry("b2.mavenDependencies", "repositoryURL", siteDir.toURI().toURL().toExternalForm());
         }
         catch (MalformedURLException e)
         {
            throw pipe(e);
         }
         module.putAnnotationEntry("b2.mavenDependencies", "repositoryName", repositoryName);

         interpolatePlugins(module, moduleProperties, osgifyContext);
      }
   }

   private static void interpolatePlugins(final AbstractModule module, PropertiesSource moduleProperties,
      final OsgifyContext osgifyContext)
   {
      ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;

      PluginsFacet pluginsFacet = eFactory.createPluginsFacet();
      pluginsFacet.setName(getFacetName(moduleProperties));
      pluginsFacet.setDerived(true);

      PluginsFacet sourcesFacet = eFactory.createPluginsFacet();
      sourcesFacet.setName(getSourcesFacetName(moduleProperties));
      sourcesFacet.setDerived(true);

      if (pluginsFacet.getName().equals(sourcesFacet.getName()))
      {
         sourcesFacet = pluginsFacet;
      }

      final EList<BundleCandidate> bundles = osgifyContext.getBundles();
      for (BundleCandidate bundle : bundles)
      {
         PluginProject pluginProject = eFactory.createPluginProject();
         pluginProject.setDerived(true);
         pluginProject.setId(bundle.getSymbolicName());
         pluginProject.setVersion(bundle.getVersion().toFullString());
         pluginProject.setBundleManifest(EcoreUtil.copy(bundle.getManifest()));

         if (pluginProject.getBundleManifest().getHeader("Eclipse-SourceBundle") == null)
         {
            pluginsFacet.getProjects().add(pluginProject);
         }
         else
         {
            sourcesFacet.getProjects().add(pluginProject);
         }
      }

      if (!pluginsFacet.getProjects().isEmpty())
      {
         module.getFacets().add(pluginsFacet);
      }

      if (sourcesFacet != pluginsFacet && !sourcesFacet.getProjects().isEmpty())
      {
         module.getFacets().add(sourcesFacet);
      }
   }

   private static String getSourcesFacetName(PropertiesSource moduleProperties)
   {
      final String sourcesClassifier = moduleProperties.get("b2.featuresSourceClassifier", "sources");
      final StringBuilder sb = new StringBuilder();
      sb.append(getFacetName(moduleProperties));
      if (!Strings.isNullOrEmpty(sourcesClassifier))
      {
         sb.append('.');
         sb.append(sourcesClassifier);
      }
      return sb.toString();
   }

   private static String getFacetName(PropertiesSource moduleProperties)
   {
      return moduleProperties.get("b2.mavenDependenciesFacetName", "dependencies");
   }

   public void postInterpolation(AbstractModule module, PropertiesSource moduleProperties, ThrowablePipe errors)
   {
      final String repositoryURL = module.getAnnotationEntry("b2.mavenDependencies", "repositoryURL");
      if (repositoryURL != null)
      {
         AbstractFacet facet = module.getFacetByName(getFacetName(moduleProperties));
         if (facet != null)
         {
            module.getFacets().remove(facet);
         }
         facet = module.getFacetByName(getSourcesFacetName(moduleProperties));
         if (facet != null)
         {
            module.getFacets().remove(facet);
         }
      }
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource propertie,
      ITemplates templates)
   {
      final String repositoryURL = inputElement.getAnnotationEntry("b2.mavenDependencies", "repositoryURL");
      if (repositoryURL != null)
      {
         final File pomFile = resolvePomFile(inputElement);

         final Model pom = readMavenModel(pomFile);

         removeDependencies(pom, JARS);

         final String repositoryName = inputElement.getAnnotationEntry("b2.mavenDependencies", "repositoryName");

         final Repository repository = new Repository();
         repository.setId(repositoryName);
         repository.setLayout("p2");
         repository.setUrl(repositoryURL);
         pom.addRepository(repository);

         writeMavenModel(pomFile, pom);
      }
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(AbstractModule.class);
   }

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.MODULE_RESOURCE_FILTER;
   }
}
