/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class FeaturesInterpolatorTest extends AbstractInterpolatorUseCasesTest
{
   @Override
   protected boolean isUseIndex()
   {
      return true;
   }

   @Override
   protected void assertUC_1_SinglePlugin_NoSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(1, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());
   }

   @Override
   protected void assertUC_1_SinglePlugin_WithSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(3, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_2_PluginsAndTests_NoSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());
   }

   @Override
   protected void assertUC_2_PluginsAndTests_WithSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(6, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());
   }

   @Override
   protected void assertUC_3_EraseFacetClassifier_NoSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());
   }

   @Override
   protected void assertUC_3_EraseFacetClassifier_WithSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(6, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_4_CustomAssemblies_PublicSdkTest_NoSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertEquals("public, sdk", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());
   }

   @Override
   protected void assertUC_4_CustomAssemblies_PublicSdkTest_WithSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(6, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertEquals("public", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.sdk.feature", "1.0.0.qualifier", featureProject);
      assertEquals("sdk", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_NoSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(4, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.doc.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_WithSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(8, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.doc.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.doc.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(6);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(4, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.doc.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(2);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(3);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(7);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_6_InterFacetRequirements_NoSource(BasicModule module)
   {
      FeatureProject docFeature = getFeatureProject(module, "foo.doc.feature");
      assertNotNull(docFeature);
      assertEquals(0, docFeature.getRequiredFeatures().size());
      assertEquals(0, docFeature.getRequiredPlugins().size());

      FeatureProject pluginsFeature = getFeatureProject(module, "foo.plugins.feature");
      assertNotNull(pluginsFeature);
      assertEquals(0, pluginsFeature.getRequiredFeatures().size());
      assertEquals(0, pluginsFeature.getRequiredPlugins().size());

      FeatureProject testsFeature = getFeatureProject(module, "foo.tests.feature");
      assertNotNull(testsFeature);
      assertEquals(1, testsFeature.getRequiredFeatures().size());
      assertEquals(0, testsFeature.getRequiredPlugins().size());

      RuledReference requiredFeature;
      requiredFeature = testsFeature.getRequiredFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", requiredFeature);
   }

   @Override
   protected void assertUC_6_InterFacetRequirements_WithSource(BasicModule module)
   {
      FeatureProject docFeature = getFeatureProject(module, "foo.doc.feature");
      assertNotNull(docFeature);
      assertEquals(0, docFeature.getRequiredFeatures().size());
      assertEquals(0, docFeature.getRequiredPlugins().size());

      FeatureProject docSrcFeature = getFeatureProject(module, "foo.doc.sources.feature");
      assertNotNull(docSrcFeature);
      assertEquals(0, docSrcFeature.getRequiredFeatures().size());
      assertEquals(0, docSrcFeature.getRequiredPlugins().size());

      FeatureProject pluginsFeature = getFeatureProject(module, "foo.plugins.feature");
      assertNotNull(pluginsFeature);
      assertEquals(0, pluginsFeature.getRequiredFeatures().size());
      assertEquals(0, pluginsFeature.getRequiredPlugins().size());

      FeatureProject pluginsSrcFeature = getFeatureProject(module, "foo.plugins.sources.feature");
      assertNotNull(pluginsSrcFeature);
      assertEquals(0, pluginsSrcFeature.getRequiredFeatures().size());
      assertEquals(0, pluginsSrcFeature.getRequiredPlugins().size());

      FeatureProject testsFeature = getFeatureProject(module, "foo.tests.feature");
      assertNotNull(testsFeature);
      assertEquals(1, testsFeature.getRequiredFeatures().size());
      assertEquals(0, testsFeature.getRequiredPlugins().size());

      RuledReference requiredFeature;
      requiredFeature = testsFeature.getRequiredFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", requiredFeature);

      FeatureProject testsSrcFeature = getFeatureProject(module, "foo.tests.sources.feature");
      assertNotNull(testsSrcFeature);
      assertEquals(1, testsSrcFeature.getRequiredFeatures().size());
      assertEquals(0, testsSrcFeature.getRequiredPlugins().size());

      requiredFeature = testsSrcFeature.getRequiredFeatures().get(0);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", requiredFeature);
   }

   @Override
   protected void assertUC_6_InterModuleFacetRequirements_NoSource(BasicModule module)
   {
      FeatureProject pluginsFeature = getFeatureProject(module, "bar.plugins.feature");
      assertNotNull(pluginsFeature);
      assertEquals(1, pluginsFeature.getRequiredFeatures().size());
      assertEquals(0, pluginsFeature.getRequiredPlugins().size());

      FeatureProject testsFeature = getFeatureProject(module, "bar.tests.feature");
      assertNotNull(testsFeature);
      assertEquals(2, testsFeature.getRequiredFeatures().size());
      assertEquals(0, testsFeature.getRequiredPlugins().size());

      RuledReference requiredFeature;
      requiredFeature = testsFeature.getRequiredFeatures().get(0);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", requiredFeature);

      requiredFeature = testsFeature.getRequiredFeatures().get(1);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", requiredFeature);
   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_NoSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(5, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.public.feature", "1.0.0.qualifier", featureProject);
      assertEquals("public", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public, sdk", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.sdk.feature", "1.0.0.qualifier", featureProject);
      assertEquals("sdk", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public, sdk", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("bar.tests.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("test", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_WithSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(7, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.public.feature", "1.0.0.qualifier", featureProject);
      assertEquals("public", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.sdk.feature", "1.0.0.qualifier", featureProject);
      assertEquals("sdk", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(4, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(2);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(3);
      assertReference("bar.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(6);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(4, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(2);
      assertReference("bar.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(3);
      assertReference("bar.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_NoSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(5, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.public.feature", "1.0.0.qualifier", featureProject);
      assertEquals("public", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public, sdk", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.sdk.feature", "1.0.0.qualifier", featureProject);
      assertEquals("sdk", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public, sdk", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("bar.tests.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("test", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_WithSource(BasicModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(7, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.public.feature", "1.0.0.qualifier", featureProject);
      assertEquals("public", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.sdk.feature", "1.0.0.qualifier", featureProject);
      assertEquals("sdk", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(3, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(2);
      assertReference("bar.sdk.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("sdk", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(6);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(3, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(2);
      assertReference("bar.test.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("test", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_NoSource(CompositeModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("core.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("core", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(featureInclude));
      assertFalse(B2MetadataUtils.isSourceFeature(featureInclude));
      assertFalse(B2MetadataUtils.isTestFeature(featureInclude));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("ui.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("ui", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(featureInclude));
      assertFalse(B2MetadataUtils.isSourceFeature(featureInclude));
      assertFalse(B2MetadataUtils.isTestFeature(featureInclude));

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("core.tests.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("core", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(featureInclude));
      assertFalse(B2MetadataUtils.isSourceFeature(featureInclude));
      assertTrue(B2MetadataUtils.isTestFeature(featureInclude));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("ui.tests.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("ui", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(featureInclude));
      assertFalse(B2MetadataUtils.isSourceFeature(featureInclude));
      assertTrue(B2MetadataUtils.isTestFeature(featureInclude));
   }
   
   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_NoSource(CompositeModule module)
   {
      assertUC_8_AggregateContentOfCompositeModule_NoSource(module);
   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_WithSource(CompositeModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(4, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("core.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("core", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(featureInclude));
      assertFalse(B2MetadataUtils.isSourceFeature(featureInclude));
      assertFalse(B2MetadataUtils.isTestFeature(featureInclude));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("core.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("core", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(featureInclude));
      assertTrue(B2MetadataUtils.isSourceFeature(featureInclude));
      assertFalse(B2MetadataUtils.isTestFeature(featureInclude));
      
      featureInclude = featureProject.getIncludedFeatures().get(2);
      assertReference("ui.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("ui", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(featureInclude));
      assertFalse(B2MetadataUtils.isSourceFeature(featureInclude));
      assertFalse(B2MetadataUtils.isTestFeature(featureInclude));

      featureInclude = featureProject.getIncludedFeatures().get(3);
      assertReference("ui.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("ui", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(featureInclude));
      assertTrue(B2MetadataUtils.isSourceFeature(featureInclude));
      assertFalse(B2MetadataUtils.isTestFeature(featureInclude));

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(4, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("core.tests.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("core", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(featureInclude));
      assertFalse(B2MetadataUtils.isSourceFeature(featureInclude));
      assertTrue(B2MetadataUtils.isTestFeature(featureInclude));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("core.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("core", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(featureInclude));
      assertTrue(B2MetadataUtils.isSourceFeature(featureInclude));
      assertTrue(B2MetadataUtils.isTestFeature(featureInclude));
      
      featureInclude = featureProject.getIncludedFeatures().get(2);
      assertReference("ui.tests.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("ui", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(featureInclude));
      assertFalse(B2MetadataUtils.isSourceFeature(featureInclude));
      assertTrue(B2MetadataUtils.isTestFeature(featureInclude));

      featureInclude = featureProject.getIncludedFeatures().get(3);
      assertReference("ui.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("ui", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(featureInclude));
      assertTrue(B2MetadataUtils.isSourceFeature(featureInclude));
      assertTrue(B2MetadataUtils.isTestFeature(featureInclude));
   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_WithSource(CompositeModule module)
   {
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("core.main.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("core", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertNull(B2MetadataUtils.getFacetName(featureInclude));
      assertTrue(B2MetadataUtils.isSourceFeature(featureInclude));
      assertFalse(B2MetadataUtils.isTestFeature(featureInclude));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("ui.main.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("ui", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertNull(B2MetadataUtils.getFacetName(featureInclude));
      assertTrue(B2MetadataUtils.isSourceFeature(featureInclude));
      assertFalse(B2MetadataUtils.isTestFeature(featureInclude));

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("core.test.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("core", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertNull(B2MetadataUtils.getFacetName(featureInclude));
      assertTrue(B2MetadataUtils.isSourceFeature(featureInclude));
      assertTrue(B2MetadataUtils.isTestFeature(featureInclude));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("ui.test.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("ui", B2MetadataUtils.getModuleId(featureInclude));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(featureInclude));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(featureInclude).toString());
      assertNull(B2MetadataUtils.getFacetName(featureInclude));
      assertTrue(B2MetadataUtils.isSourceFeature(featureInclude));
      assertTrue(B2MetadataUtils.isTestFeature(featureInclude));
   }

   @Test
   public void testSourceBuild() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "true"); // true is default
      moduleProperties.put("b2.assemblies", "main");

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertEquals(3, featuresFacet.getProjects().size());

      FeatureProject featureProject;

      featureProject = featuresFacet.getProjects().get(0);
      assertEquals("foo.plugins.feature", featureProject.getId());
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));

      featureProject = featuresFacet.getProjects().get(1);
      assertEquals("foo.plugins.sources.feature", featureProject.getId());
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));

      featureProject = featuresFacet.getProjects().get(2);
      assertEquals("foo.main.feature", featureProject.getId());
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
   }

   @Test
   public void testReplaceAssemblyFeatureWithSingleFacetFeature() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false");
      moduleProperties.put("b2.assemblies", "main, bla");

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertEquals(1, featuresFacet.getProjects().size());

      FeatureProject featureProject = featuresFacet.getProjects().get(0);
      assertEquals("foo.plugins.feature", featureProject.getId());

      String assemblyNames = featureProject.getAnnotationEntry("b2", "assemblyNames");
      assertEquals("main, bla", assemblyNames);
   }

   @Test
   public void testOverall()
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "main", "foo.plugin", "1.0.0.qualifier");

      PluginsFacet pluginsFacet = createPluginsFacet("tests");
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.facets[\"tests\"].requiredPlugins", "org.junit:4.10");


      // interpolate
      interpolate(module, moduleProperties);


      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertEquals("features", featuresFacet.getName());
      assertEquals(4, featuresFacet.getProjects().size());

      FeatureProject mainFeature = featuresFacet.getProjects().get(0);
      assertEquals("foo.main.feature", mainFeature.getId());
      assertEquals(new File(".b2/features/foo.main.feature"), mainFeature.getDirectory());
      assertEquals(0, mainFeature.getIncludedFeatures().size());
      assertEquals(1, mainFeature.getIncludedPlugins().size());
      assertEquals(0, mainFeature.getRequiredFeatures().size());
      assertEquals(0, mainFeature.getRequiredPlugins().size());

      PluginInclude mainPluginInclude = mainFeature.getIncludedPlugins().get(0);
      assertEquals("foo.plugin", mainPluginInclude.getId());
      assertEquals("1.0.0.qualifier", mainPluginInclude.getVersion());

      FeatureProject srcMainFeature = featuresFacet.getProjects().get(1);
      assertEquals("foo.main.sources.feature", srcMainFeature.getId());
      assertEquals(new File(".b2/features/foo.main.sources.feature"), srcMainFeature.getDirectory());
      assertEquals(0, srcMainFeature.getIncludedFeatures().size());
      assertEquals(1, srcMainFeature.getIncludedPlugins().size());

      PluginInclude srcMainPluginInclude = srcMainFeature.getIncludedPlugins().get(0);
      assertEquals("foo.plugin.source", srcMainPluginInclude.getId());
      assertEquals("1.0.0.qualifier", srcMainPluginInclude.getVersion());

      FeatureProject testFeature = featuresFacet.getProjects().get(2);
      assertEquals("foo.tests.feature", testFeature.getId());
      assertEquals(new File(".b2/features/foo.tests.feature"), testFeature.getDirectory());
      assertEquals(0, testFeature.getIncludedFeatures().size());
      assertEquals(0, testFeature.getIncludedPlugins().size());
      assertEquals(0, testFeature.getRequiredFeatures().size());
      assertEquals(1, testFeature.getRequiredPlugins().size());
   }

   @Test
   public void testIncludedFeatures() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginsFacet pluginsFacet = createPluginsFacet("main");
      module.getFacets().add(pluginsFacet);

      pluginsFacet = createPluginsFacet("tests");
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.facets[\"main\"].includedSourceFeatures", "org.eclipse.platform:3.8.0");
      moduleProperties.put("b2.facets[\"tests\"].includedFeatures", "org.junit:4.10:optional");


      // interpolate
      interpolate(module, moduleProperties);

      FeatureProject mainFeature = getFeatureProject(module, "foo.main.feature");
      assertNotNull(mainFeature);
      assertEquals(0, mainFeature.getIncludedFeatures().size());
      assertEquals(0, mainFeature.getIncludedPlugins().size());
      assertEquals(0, mainFeature.getRequiredFeatures().size());
      assertEquals(0, mainFeature.getRequiredPlugins().size());

      FeatureProject srcMainFeature = getFeatureProject(module, "foo.main.sources.feature");
      assertNotNull(srcMainFeature);
      assertEquals(1, srcMainFeature.getIncludedFeatures().size());
      assertEquals(0, srcMainFeature.getIncludedPlugins().size());
      assertEquals(0, srcMainFeature.getRequiredFeatures().size());
      assertEquals(0, srcMainFeature.getRequiredPlugins().size());

      FeatureInclude featureInclude = srcMainFeature.getIncludedFeatures().get(0);
      assertEquals("org.eclipse.platform", featureInclude.getId());
      assertEquals("3.8.0", featureInclude.getVersion());
      assertFalse(featureInclude.isOptional());

      FeatureProject testFeature = getFeatureProject(module, "foo.tests.feature");
      assertNotNull(testFeature);
      assertEquals(1, testFeature.getIncludedFeatures().size());
      assertEquals(0, testFeature.getIncludedPlugins().size());
      assertEquals(0, testFeature.getRequiredFeatures().size());
      assertEquals(0, testFeature.getRequiredPlugins().size());

      featureInclude = testFeature.getIncludedFeatures().get(0);
      assertEquals("org.junit", featureInclude.getId());
      assertEquals("4.10", featureInclude.getVersion());
      assertTrue(featureInclude.isOptional());
   }

   @Test
   public void testIncludedPlugins() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginsFacet pluginsFacet = createPluginsFacet("main");
      module.getFacets().add(pluginsFacet);

      pluginsFacet = createPluginsFacet("tests");
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.facets[\"main\"].includedSourcePlugins", "org.eclipse.platform:3.8.0");
      moduleProperties.put("b2.facets[\"tests\"].includedPlugins", "org.junit:4.10:unpack");


      // interpolate
      interpolate(module, moduleProperties);

      FeatureProject mainFeature = getFeatureProject(module, "foo.main.feature");
      assertNotNull(mainFeature);
      assertEquals(0, mainFeature.getIncludedFeatures().size());
      assertEquals(0, mainFeature.getIncludedPlugins().size());
      assertEquals(0, mainFeature.getRequiredFeatures().size());
      assertEquals(0, mainFeature.getRequiredPlugins().size());

      FeatureProject srcMainFeature = getFeatureProject(module, "foo.main.sources.feature");
      assertNotNull(srcMainFeature);
      assertEquals(0, srcMainFeature.getIncludedFeatures().size());
      assertEquals(1, srcMainFeature.getIncludedPlugins().size());
      assertEquals(0, srcMainFeature.getRequiredFeatures().size());
      assertEquals(0, srcMainFeature.getRequiredPlugins().size());

      PluginInclude featureInclude = srcMainFeature.getIncludedPlugins().get(0);
      assertEquals("org.eclipse.platform", featureInclude.getId());
      assertEquals("3.8.0", featureInclude.getVersion());
      assertFalse(featureInclude.isUnpack());

      FeatureProject testFeature = getFeatureProject(module, "foo.tests.feature");
      assertNotNull(testFeature);
      assertEquals(0, testFeature.getIncludedFeatures().size());
      assertEquals(1, testFeature.getIncludedPlugins().size());
      assertEquals(0, testFeature.getRequiredFeatures().size());
      assertEquals(0, testFeature.getRequiredPlugins().size());

      featureInclude = testFeature.getIncludedPlugins().get(0);
      assertEquals("org.junit", featureInclude.getId());
      assertEquals("4.10", featureInclude.getVersion());
      assertTrue(featureInclude.isUnpack());
   }
}
