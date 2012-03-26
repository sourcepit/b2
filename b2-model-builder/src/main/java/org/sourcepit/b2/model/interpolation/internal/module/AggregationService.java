/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.common.internal.utils.PathMatcher;
import org.sourcepit.b2.model.builder.util.IB2SessionService;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.common.util.ArtifactIdentifier;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;

@Named
@Singleton
public class AggregationService implements IAggregationService
{
   @Inject
   private IB2SessionService sessionService;

   public List<FeatureProject> resolveProductIncludes(AbstractModule module, String productClassifier,
      IConverter converter)
   {
      return resolveIncludes("b2.aggregate.product", productClassifier, module, converter);
   }

   public List<FeatureProject> resolveCategoryIncludes(AbstractModule module, String categoryClassifer,
      IConverter converter)
   {
      return resolveIncludes("b2.aggregate.category", categoryClassifer, module, converter);
   }

   public List<FeatureProject> resolveFeatureIncludes(AbstractModule module, String featureClassifier,
      IConverter converter)
   {
      return resolveIncludes("b2.aggregate.feature", featureClassifier, module, converter);
   }

   private List<FeatureProject> resolveIncludes(String propertyPrefix, String classifier, AbstractModule module,
      IConverter converter)
   {
      final String patternProperty = createFilterPatternProperty(propertyPrefix, classifier);
      final PathMatcher matcher = createPatternMatcher(patternProperty, converter);
      final List<FeatureProject> includes = new ArrayList<FeatureProject>();
      if (matcher != null)
      {
         resolveFeatureIncludes(module, matcher, includes);
      }
      return includes;
   }

   private PathMatcher createPatternMatcher(final String patternProperty, IConverter converter)
   {
      final String patterns = converter.getProperties().get(patternProperty);
      final PathMatcher matcher = patterns == null ? null : PathMatcher.parsePackagePatterns(patterns);
      return matcher;
   }

   private String createFilterPatternProperty(String prefix, String classifier)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(prefix);
      if (classifier != null && classifier.length() > 0)
      {
         sb.append('.');
         sb.append(classifier);
      }
      sb.append(".filter");
      return sb.toString();
   }

   private void resolveFeatureIncludes(AbstractModule module, PathMatcher matcher, List<FeatureProject> includes)
   {
      ResourceSet resourceSet = sessionService.getCurrentResourceSet();
      ModuleProject project = sessionService.getCurrentSession().getCurrentProject();

      for (ModuleDependency dependency : project.getDependencies())
      {
         AbstractModule depModule = resolveModule(resourceSet, dependency);
         collectIncludedFeatures(includes, matcher, depModule);
      }
   }

   private void collectIncludedFeatures(List<FeatureProject> includes, PathMatcher matcher, AbstractModule module)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         EList<FeatureProject> projects = featuresFacet.getProjects();
         for (FeatureProject featureProject : projects)
         {
            if (matcher.isMatch(featureProject.getId()))
            {
               includes.add(featureProject);
            }
         }
      }
   }

   private AbstractModule resolveModule(ResourceSet resourceSet, ModuleDependency dependency)
   {
      final URI uri = new ArtifactIdentifier(dependency.getGroupId(), dependency.getArtifactId(),
         dependency.getVersionRange(), dependency.getClassifier(), "module").toUri();

      Resource resource = resourceSet.getResource(uri, true);
      AbstractModule module = (AbstractModule) resource.getContents().get(0);
      return module;
   }
}
