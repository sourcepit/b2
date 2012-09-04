/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.junit.Before;
import org.junit.Test;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.Identifiable;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.PackageExport;
import org.sourcepit.common.manifest.osgi.PackageImport;
import org.sourcepit.common.utils.collections.MultiValueMap;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.guplex.test.GuplexTest;

// TODO UC #1: Module with pure plugin
// TODO UC #2: Module with plugins + tests
// TODO UC #3: Erase facet classifier in feature id
// TODO UC #4: Module with different assemblies, e.g. main, sdk, test
// TODO UC #5: Erase assembly classifier in feature id
// TODO UC #6: Module that requires other Module
// TODO UC #7. Module that includes other Module (aggregator)
public abstract class AbstractInterpolatorUseCasesTest extends GuplexTest
{
   @Override
   protected boolean isUseIndex()
   {
      return true;
   }

   @Test
   public void testUC_1_SinglePlugin_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_1_SinglePlugin_NoSource(module);
   }

   protected abstract void assertUC_1_SinglePlugin_NoSource(BasicModule module);

   @Test
   public void testUC_1_SinglePlugin_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_1_SinglePlugin_WithSource(module);
   }

   protected abstract void assertUC_1_SinglePlugin_WithSource(BasicModule module);

   @Test
   public void testUC_2_PluginsAndTests_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_2_PluginsAndTests_NoSource(module);
   }

   protected abstract void assertUC_2_PluginsAndTests_NoSource(BasicModule module);

   @Test
   public void testUC_2_PluginsAndTests_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_2_PluginsAndTests_WithSource(module);
   }

   protected abstract void assertUC_2_PluginsAndTests_WithSource(BasicModule module);

   @Test
   public void testUC_3_EraseFacetClassifier_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.facets[\"plugins\"].classifier", "");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_3_EraseFacetClassifier_NoSource(module);
   }

   protected abstract void assertUC_3_EraseFacetClassifier_NoSource(BasicModule module);

   @Test
   public void testUC_3_EraseFacetClassifier_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.facets[\"plugins\"].classifier", "");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_3_EraseFacetClassifier_WithSource(module);
   }

   protected abstract void assertUC_3_EraseFacetClassifier_WithSource(BasicModule module);

   @Test
   public void testUC_4_CustomAssemblies_PublicSdkTest_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                       // default?
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_4_CustomAssemblies_PublicSdkTest_NoSource(module);
   }

   protected abstract void assertUC_4_CustomAssemblies_PublicSdkTest_NoSource(BasicModule module);

   @Test
   public void testUC_4_CustomAssemblies_PublicSdkTest_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                       // default?
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_4_CustomAssemblies_PublicSdkTest_WithSource(module);
   }

   protected abstract void assertUC_4_CustomAssemblies_PublicSdkTest_WithSource(BasicModule module);

   @Test
   public void testUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "doc", "foo.plugin.doc", "1.0.0.qualifier");
      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.assemblies[\"main\"].classifier", "");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_NoSource(module);
   }

   protected abstract void assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_NoSource(BasicModule module);

   @Test
   public void testUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "doc", "foo.plugin.doc", "1.0.0.qualifier");
      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.assemblies[\"main\"].classifier", "");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_WithSource(module);
   }

   protected abstract void assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_WithSource(BasicModule module);

   private ThreadLocal<Collection<AbstractModule>> resolutionContext = new ThreadLocal<Collection<AbstractModule>>()
   {
      protected java.util.Collection<AbstractModule> initialValue()
      {
         return new ArrayList<AbstractModule>();
      };
   };

   @Before
   public void resetResolutionContext()
   {
      resolutionContext.get().clear();
   }

   @Test
   public void testUC_6_InterFacetRequirements_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));
      resolutionContext.get().add(module);

      addPluginProject(module, "doc", "foo.plugin.doc", "1.0.0.qualifier");

      PluginProject mainPlugin = addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");

      PackageExport packageExport = BundleManifestFactory.eINSTANCE.createPackageExport();
      packageExport.getPackageNames().add("foo");
      mainPlugin.getBundleManifest().getExportPackage(true).add(packageExport);

      PluginProject testPlugin = addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PackageImport packageImport = BundleManifestFactory.eINSTANCE.createPackageImport();
      packageImport.getPackageNames().add("foo");
      testPlugin.getBundleManifest().getImportPackage(true).add(packageImport);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_6_InterFacetRequirements_NoSource(module);
   }

   protected abstract void assertUC_6_InterFacetRequirements_NoSource(BasicModule module);

   @Test
   public void testUC_6_InterFacetRequirements_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));
      resolutionContext.get().add(module);

      addPluginProject(module, "doc", "foo.plugin.doc", "1.0.0.qualifier");

      PluginProject mainPlugin = addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");

      PackageExport packageExport = BundleManifestFactory.eINSTANCE.createPackageExport();
      packageExport.getPackageNames().add("foo");
      mainPlugin.getBundleManifest().getExportPackage(true).add(packageExport);

      PluginProject testPlugin = addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      PackageImport packageImport = BundleManifestFactory.eINSTANCE.createPackageImport();
      packageImport.getPackageNames().add("foo");
      testPlugin.getBundleManifest().getImportPackage(true).add(packageImport);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_6_InterFacetRequirements_WithSource(module);
   }

   protected abstract void assertUC_6_InterFacetRequirements_WithSource(BasicModule module);

   @Test
   public void testUC_6_InterModuleFacetRequirements_NoSource() throws Exception
   {
      // setup
      BasicModule fooModule = createBasicModule("foo");
      fooModule.setDirectory(new File(""));

      PluginProject mainPlugin = addPluginProject(fooModule, "plugins", "foo.plugin", "1.0.0.qualifier");
      PackageExport packageExport = BundleManifestFactory.eINSTANCE.createPackageExport();
      packageExport.getPackageNames().add("foo");
      mainPlugin.getBundleManifest().getExportPackage(true).add(packageExport);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(fooModule, moduleProperties);

      BasicModule barModule = createBasicModule("bar");
      barModule.setDirectory(new File(""));

      mainPlugin = addPluginProject(barModule, "plugins", "bar.plugin", "1.0.0.qualifier");

      packageExport = BundleManifestFactory.eINSTANCE.createPackageExport();
      packageExport.getPackageNames().add("bar");
      mainPlugin.getBundleManifest().getExportPackage(true).add(packageExport);

      PackageImport packageImport = BundleManifestFactory.eINSTANCE.createPackageImport();
      packageImport.getPackageNames().add("foo");
      mainPlugin.getBundleManifest().getImportPackage(true).add(packageImport);

      PluginProject testPlugin = addPluginProject(barModule, "tests", "bar.plugin.tests", "1.0.0.qualifier");

      packageImport = BundleManifestFactory.eINSTANCE.createPackageImport();
      packageImport.getPackageNames().add("bar");
      testPlugin.getBundleManifest().getImportPackage(true).add(packageImport);

      packageImport = BundleManifestFactory.eINSTANCE.createPackageImport();
      packageImport.getPackageNames().add("foo");
      testPlugin.getBundleManifest().getImportPackage(true).add(packageImport);

      moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      resolutionContext.get().add(fooModule);

      // interpolate
      interpolate(barModule, moduleProperties);

      assertUC_6_InterModuleFacetRequirements_NoSource(barModule);
   }

   protected abstract void assertUC_6_InterModuleFacetRequirements_NoSource(BasicModule barModule);

   @Test
   public void testUC_7_AggregateContentOfOtherModule_ModeUnwrap_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("bar");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "bar.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "bar.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                       // default?
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      resolutionContext.get().add(module);

      module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test");
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**");

      moduleProperties.put("b2.aggregator.mode", "unwrap");
      // moduleProperties.put("b2.assemblies[\"public\"].aggregator.featuresFilter", "!**.sdk.**");
      // moduleProperties.put("b2.assemblies[\"sdk\"].aggregator.featuresFilter", "**.sdk.**");
      // moduleProperties.put("b2.assemblies[\"test\"].aggregator.featuresFilter", "**.test.**");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_NoSource(module);
   }

   protected abstract void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_NoSource(BasicModule module);

   @Test
   public void testUC_7_AggregateContentOfOtherModule_ModeUnwrap_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("bar");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "bar.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "bar.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                       // default?
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      resolutionContext.get().add(module);

      module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test");
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**");

      moduleProperties.put("b2.aggregator.mode", "unwrap");
      // moduleProperties.put("b2.assemblies[\"public\"].aggregator.featuresFilter", "!**.sdk.**");
      // moduleProperties.put("b2.assemblies[\"sdk\"].aggregator.featuresFilter", "**.sdk.**");
      // moduleProperties.put("b2.assemblies[\"test\"].aggregator.featuresFilter", "**.test.**");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_WithSource(module);
   }

   protected abstract void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_WithSource(BasicModule module);

   @Test
   public void testUC_7_AggregateContentOfOtherModule_ModeAggregate_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("bar");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "bar.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "bar.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                       // default?
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      resolutionContext.get().add(module);

      module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test");
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**");

      moduleProperties.put("b2.aggregator.mode", "aggregate");
      // moduleProperties.put("b2.assemblies[\"public\"].aggregator.featuresFilter", "!**.sdk.**");
      // moduleProperties.put("b2.assemblies[\"sdk\"].aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies[\"test\"].aggregator.featuresFilter", "**.test.**,**.tests.**");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_7_AggregateContentOfOtherModule_ModeAggregate_NoSource(module);
   }

   protected abstract void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_NoSource(BasicModule module);

   @Test
   public void testUC_7_AggregateContentOfOtherModule_ModeAggregate_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("bar");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "bar.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "bar.plugin.tests", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                       // default?
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      resolutionContext.get().add(module);

      module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");
      addPluginProject(module, "tests", "foo.plugin.tests", "1.0.0.qualifier");

      moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test");
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**");

      moduleProperties.put("b2.aggregator.mode", "aggregate");
      moduleProperties.put("b2.assemblies[\"public\"].aggregator.featuresFilter", "!**.sdk.**");
      moduleProperties.put("b2.assemblies[\"sdk\"].aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies[\"test\"].aggregator.featuresFilter", "**.test.**,**.tests.**");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_7_AggregateContentOfOtherModule_ModeAggregate_WithSource(module);
   }

   protected abstract void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_WithSource(BasicModule module);

   protected void interpolate(BasicModule module, PropertiesMap moduleProperties)
   {
      ISourceService sourceService = gLookup(ISourceService.class);
      LayoutManager layoutManager = gLookup(LayoutManager.class);
      UnpackStrategy unpackStrategy = mock(UnpackStrategy.class);
      FeaturesConverter converter = gLookup(FeaturesConverter.class);

      ResolutionContextResolver contextResolver = new ResolutionContextResolver()
      {
         public void determineForeignResolutionContext(MultiValueMap<AbstractModule, String> moduleToAssemblies,
            AbstractModule module, boolean isTest)
         {
            final Collection<AbstractModule> modules = resolutionContext.get();
            for (AbstractModule abstractModule : modules)
            {
               LinkedHashSet<String> assemblyNames = new LinkedHashSet<String>();
               for (FeaturesFacet featuresFacet : abstractModule.getFacets(FeaturesFacet.class))
               {
                  for (FeatureProject featureProject : featuresFacet.getProjects())
                  {
                     if (isTest || (!isTest && !B2MetadataUtils.isTestFeature(featureProject)))
                     {
                        assemblyNames.addAll(B2MetadataUtils.getAssemblyNames(featureProject));
                     }
                  }
               }
               if (!assemblyNames.isEmpty())
               {
                  moduleToAssemblies.get(abstractModule, true).addAll(assemblyNames);
               }
            }
         }
      };

      DefaultIncludesAndRequirementsResolver includesAndRequirements = new DefaultIncludesAndRequirementsResolver(
         converter, unpackStrategy, contextResolver);

      FeaturesInterpolator interpolator;
      interpolator = new FeaturesInterpolator(sourceService, layoutManager, converter, includesAndRequirements);

      interpolator.interpolate(module, moduleProperties);
   }

   public static PluginProject addPluginProject(BasicModule module, String facetName, String pluginId,
      String pluginVersion)
   {
      PluginsFacet pluginsFacet = module.getFacetByName(facetName);
      if (pluginsFacet == null)
      {
         pluginsFacet = createPluginsFacet(facetName);
         module.getFacets().add(pluginsFacet);
      }

      PluginProject plugin = createPluginProject(pluginId, pluginVersion);
      pluginsFacet.getProjects().add(plugin);
      return plugin;
   }

   public static FeatureProject getFeatureProject(BasicModule module, String id, String version)
   {
      StrictReference ref = ModuleModelFactory.eINSTANCE.createStrictReference();
      ref.setId(id);
      ref.setVersion(version);
      return module.resolveReference(ref, FeaturesFacet.class);
   }

   public static FeatureProject getFeatureProject(BasicModule module, String id)
   {
      return getFeatureProject(module, id, null);
   }

   public static BasicModule createBasicModule(String id)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final BasicModule module = eFactory.createBasicModule();
      module.setId(id);
      module.setVersion("1.0.0.qualifier");
      module.setLayoutId("structured");
      return module;
   }

   public static PluginProject createPluginProject(String id, String version)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final PluginProject plugin = eFactory.createPluginProject();
      plugin.setId(id);
      plugin.setVersion(version);
      plugin.setTestPlugin(id.endsWith(".tests"));

      final BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();

      manifest.setBundleSymbolicName(id);
      manifest.setBundleVersion(version);

      plugin.setBundleManifest(manifest);

      return plugin;
   }

   public static PluginsFacet createPluginsFacet(String name)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final PluginsFacet pluginsFacet = eFactory.createPluginsFacet();
      pluginsFacet.setName(name);
      return pluginsFacet;
   }

   public static void assertReference(String expectedId, String expectedVersion, AbstractReference reference)
   {
      assertEquals(expectedId, reference.getId());
      assertEquals(expectedVersion, reference.getVersion());
   }

   public static void assertIdentifiable(String expectedId, String expectedVersion, Identifiable identifiable)
   {
      assertEquals(expectedId, identifiable.getId());
      assertEquals(expectedVersion, identifiable.getVersion());
   }

}
