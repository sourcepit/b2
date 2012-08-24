/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.p2;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

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
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.internal.generator.AbstractPomGenerator;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.sourcepit.osgify.core.model.context.BundleCandidate;
import org.sourcepit.osgify.core.model.context.OsgifyContext;
import org.sourcepit.osgify.maven.p2.P2UpdateSiteGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Named
public class PomDependenciesSiteGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private P2UpdateSiteGenerator updateSiteGenerator;

   @Inject
   private LayoutManager layoutManager;

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.MODULE_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(AbstractModule.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, IConverter converter, ITemplates templates)
   {
      final MavenSession session = legacySupport.getSession();
      final MavenProject project = session.getCurrentProject();

      final List<Dependency> dependencies = new ArrayList<Dependency>();
      for (Dependency dependency : project.getDependencies())
      {
         if ("jar".equals(dependency.getType()))
         {
            dependencies.add(dependency);
         }
      }

      if (!dependencies.isEmpty())
      {
         final AbstractModule module = (AbstractModule) inputElement;
         final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
         final File siteDir = new File(layout.pathOfMetaDataFile(module, "pom-dependencies"));

         final String repositoryName = "pom-dependencies@" + project.getArtifactId();
         final List<ArtifactRepository> remoteRepositories = project.getRemoteArtifactRepositories();
         final ArtifactRepository localRepository = session.getLocalRepository();

         final PropertiesMap properties = converter.getProperties();

         final PropertiesMap options = new LinkedPropertiesMap();
         for (Entry<String, String> entry : properties.entrySet())
         {
            String key = entry.getKey();
            if (key.startsWith("b2.osgify."))
            {
               options.put(key.substring("b2.osgify.".length()), entry.getValue());
            }
         }

         final OsgifyContext osgifyContext = updateSiteGenerator.generateUpdateSite(siteDir, dependencies,
            remoteRepositories, localRepository, repositoryName, options);

         final File pomFile = resolvePomFile(module);

         final Model pom = readMavenModel(pomFile);
         filterDependencies(pom, dependencies);

         final Repository siteRepo = new Repository();
         siteRepo.setId(repositoryName);
         siteRepo.setLayout("p2");
         try
         {
            siteRepo.setUrl(siteDir.toURL().toExternalForm());
         }
         catch (MalformedURLException e)
         {
            throw new IllegalStateException(e);
         }

         pom.getRepositories().add(siteRepo);

         writeMavenModel(pomFile, pom);

         updateFeatures(module, osgifyContext);
      }
   }

   private void updateFeatures(final AbstractModule module, final OsgifyContext osgifyContext)
   {
      EList<FeaturesFacet> facets = module.getFacets(FeaturesFacet.class);
      for (FeaturesFacet featuresFacet : facets)
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            if (featureProject.isDerived())
            {
               File directory = featureProject.getDirectory();

               File xmlFile = new File(directory, "feature.xml");
               Document featureDoc = XmlUtils.readXml(xmlFile);

               Element feature = featureDoc.getDocumentElement();

               final EList<BundleCandidate> bundles = osgifyContext.getBundles();
               for (BundleCandidate bundle : bundles)
               {
                  Element plugin = featureDoc.createElement("plugin");
                  plugin.setAttribute("download-size", "0");
                  plugin.setAttribute("id", bundle.getSymbolicName());
                  plugin.setAttribute("install-size", "0");
                  plugin.setAttribute("unpack", "false");
                  plugin.setAttribute("version", bundle.getVersion().toFullString());

                  feature.appendChild(plugin);
               }

               XmlUtils.writeXml(featureDoc, xmlFile);
            }
         }
      }
   }

   private void filterDependencies(final Model mavenModel, final List<Dependency> blackList)
   {
      final List<Dependency> filteredDependencies = new ArrayList<Dependency>();
      for (Dependency dependency : mavenModel.getDependencies())
      {
         if (!containsDependency(blackList, dependency))
         {
            filteredDependencies.add(dependency);
         }
      }
      mavenModel.setDependencies(filteredDependencies);
   }

   private boolean containsDependency(final List<Dependency> dependencies, Dependency dependency)
   {
      for (Dependency d : dependencies)
      {
         if (dependency.getManagementKey().equals(d.getManagementKey()))
         {
            return true;
         }
      }
      return false;
   }
}
