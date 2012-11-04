/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.mockito.Mockito.mock;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addPluginProject;
import static org.sourcepit.b2.model.harness.ModelTestHarness.createBasicModule;
import static org.sourcepit.b2.model.harness.ModelTestHarness.createCompositeModule;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.PackageExport;
import org.sourcepit.common.manifest.osgi.PackageImport;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.guplex.test.GuplexTest;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.facets.plugins.classifier", "");

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.facets.plugins.classifier", "");

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
      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                  // default?
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                  // default?
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.assemblies.main.classifier", "");

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.assemblies.main.classifier", "");

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

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

      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

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

      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

      moduleProperties.put("b2.aggregator.mode", "unwrap");
      // moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**");
      // moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      // moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

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
      moduleProperties.put("b2.assemblies", "public, sdk, test");

      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

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

      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

      moduleProperties.put("b2.aggregator.mode", "unwrap");
      // moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**");
      // moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      // moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

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
      moduleProperties.put("b2.assemblies", "public, sdk, test");

      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

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
      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.aggregator.mode", "aggregate");
      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

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
      moduleProperties.put("b2.assemblies", "public, sdk, test");

      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

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

      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

      moduleProperties.put("b2.aggregator.mode", "aggregate");

      // interpolate
      interpolate(module, moduleProperties);

      assertUC_7_AggregateContentOfOtherModule_ModeAggregate_WithSource(module);
   }

   protected abstract void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_WithSource(BasicModule module);

   @Test
   public void testUC_8_AggregateContentOfCompositeModule_NoSource()
   {
      BasicModule coreModule = createBasicModule("core");
      addPluginProject(coreModule, "plugins", "foo.core", coreModule.getVersion());
      addPluginProject(coreModule, "tests", "foo.core.tests", coreModule.getVersion());

      BasicModule uiModule = createBasicModule("ui");
      addPluginProject(uiModule, "plugins", "foo.ui", coreModule.getVersion());
      addPluginProject(uiModule, "tests", "foo.ui.tests", coreModule.getVersion());

      CompositeModule module = createCompositeModule("foo");
      module.getModules().add(coreModule);
      module.getModules().add(uiModule);

      module.setDirectory(new File(""));
      coreModule.setDirectory(new File(module.getDirectory(), "core"));
      uiModule.setDirectory(new File(module.getDirectory(), "ui"));

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test");

      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.main.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

      // moduleProperties.put("b2.aggregator.mode", "unwrap");

      // interpolate
      interpolate(coreModule, moduleProperties);
      interpolate(uiModule, moduleProperties);

      interpolate(module, moduleProperties);

      assertUC_8_AggregateContentOfCompositeModule_NoSource(module);
   }

   protected abstract void assertUC_8_AggregateContentOfCompositeModule_NoSource(CompositeModule module);

   @Test
   public void testUC_8_AggregateContentOfCompositeModule_ModeAggregate_NoSource()
   {
      BasicModule coreModule = createBasicModule("core");
      addPluginProject(coreModule, "plugins", "foo.core", coreModule.getVersion());
      addPluginProject(coreModule, "tests", "foo.core.tests", coreModule.getVersion());

      BasicModule uiModule = createBasicModule("ui");
      addPluginProject(uiModule, "plugins", "foo.ui", coreModule.getVersion());
      addPluginProject(uiModule, "tests", "foo.ui.tests", coreModule.getVersion());

      CompositeModule module = createCompositeModule("foo");
      module.getModules().add(coreModule);
      module.getModules().add(uiModule);

      module.setDirectory(new File(""));
      coreModule.setDirectory(new File(module.getDirectory(), "core"));
      uiModule.setDirectory(new File(module.getDirectory(), "ui"));

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test");

      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.main.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

      moduleProperties.put("b2.aggregator.mode", "aggregate");

      // interpolate
      interpolate(coreModule, moduleProperties);
      interpolate(uiModule, moduleProperties);

      interpolate(module, moduleProperties);

      assertUC_8_AggregateContentOfCompositeModule_NoSource(module);
   }

   protected abstract void assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_NoSource(CompositeModule module);

   @Test
   public void testUC_8_AggregateContentOfCompositeModule_WithSource()
   {
      BasicModule coreModule = createBasicModule("core");
      addPluginProject(coreModule, "plugins", "foo.core", coreModule.getVersion());
      addPluginProject(coreModule, "tests", "foo.core.tests", coreModule.getVersion());

      BasicModule uiModule = createBasicModule("ui");
      addPluginProject(uiModule, "plugins", "foo.ui", coreModule.getVersion());
      addPluginProject(uiModule, "tests", "foo.ui.tests", coreModule.getVersion());

      CompositeModule module = createCompositeModule("foo");
      module.getModules().add(coreModule);
      module.getModules().add(uiModule);

      module.setDirectory(new File(""));
      coreModule.setDirectory(new File(module.getDirectory(), "core"));
      uiModule.setDirectory(new File(module.getDirectory(), "ui"));

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test");

      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.main.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

      // moduleProperties.put("b2.aggregator.mode", "unwrap");

      // interpolate
      interpolate(coreModule, moduleProperties);
      interpolate(uiModule, moduleProperties);

      interpolate(module, moduleProperties);

      assertUC_8_AggregateContentOfCompositeModule_WithSource(module);
   }

   protected abstract void assertUC_8_AggregateContentOfCompositeModule_WithSource(CompositeModule module);

   @Test
   public void testUC_8_AggregateContentOfCompositeModule_ModeAggregate_WithSource()
   {
      BasicModule coreModule = createBasicModule("core");
      addPluginProject(coreModule, "plugins", "foo.core", coreModule.getVersion());
      addPluginProject(coreModule, "tests", "foo.core.tests", coreModule.getVersion());

      BasicModule uiModule = createBasicModule("ui");
      addPluginProject(uiModule, "plugins", "foo.ui", coreModule.getVersion());
      addPluginProject(uiModule, "tests", "foo.ui.tests", coreModule.getVersion());

      CompositeModule module = createCompositeModule("foo");
      module.getModules().add(coreModule);
      module.getModules().add(uiModule);

      module.setDirectory(new File(""));
      coreModule.setDirectory(new File(module.getDirectory(), "core"));
      uiModule.setDirectory(new File(module.getDirectory(), "ui"));

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test");

      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.public.featuresFilter", "!**.sources.**,!**.tests.**");
      moduleProperties.put("b2.assemblies.sdk.featuresFilter", "!**.tests.**");
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**");

      moduleProperties.put("b2.assemblies.main.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.public.aggregator.featuresFilter", "!**.sdk.**,!**.test.**");
      moduleProperties.put("b2.assemblies.sdk.aggregator.featuresFilter", "**.sdk.**");
      moduleProperties.put("b2.assemblies.test.aggregator.featuresFilter", "**.test.**");

      moduleProperties.put("b2.aggregator.mode", "aggregate");

      // interpolate
      interpolate(coreModule, moduleProperties);
      interpolate(uiModule, moduleProperties);

      interpolate(module, moduleProperties);

      assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_WithSource(module);
   }

   protected abstract void assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_WithSource(CompositeModule module);

   protected void interpolate(AbstractModule module, PropertiesMap moduleProperties)
   {
      ISourceService sourceService = gLookup(ISourceService.class);
      LayoutManager layoutManager = gLookup(LayoutManager.class);
      UnpackStrategy unpackStrategy = mock(UnpackStrategy.class);
      FeaturesConverter converter = gLookup(FeaturesConverter.class);

      ResolutionContextResolver contextResolver = new ResolutionContextResolver()
      {
         public SetMultimap<AbstractModule, FeatureProject> resolveResolutionContext(AbstractModule module,
            boolean scopeTest)
         {
            final SetMultimap<AbstractModule, FeatureProject> moduleToAssemblies = LinkedHashMultimap.create();
            final Collection<AbstractModule> modules = resolutionContext.get();
            for (AbstractModule abstractModule : modules)
            {
               for (FeaturesFacet featuresFacet : abstractModule.getFacets(FeaturesFacet.class))
               {
                  for (FeatureProject featureProject : featuresFacet.getProjects())
                  {
                     if (!B2MetadataUtils.getAssemblyNames(featureProject).isEmpty())
                     {
                        moduleToAssemblies.get(abstractModule).add(featureProject);
                     }
                  }
               }
            }
            return moduleToAssemblies;
         }
      };

      DefaultIncludesAndRequirementsResolver includesAndRequirements = new DefaultIncludesAndRequirementsResolver(
         converter, unpackStrategy, sourceService, contextResolver);

      FeaturesInterpolator interpolator;
      interpolator = new FeaturesInterpolator(sourceService, layoutManager, converter, includesAndRequirements);

      interpolator.interpolate(module, moduleProperties);
   }

}
