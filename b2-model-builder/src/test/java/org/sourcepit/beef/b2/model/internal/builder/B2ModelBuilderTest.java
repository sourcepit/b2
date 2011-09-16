/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.beef.b2.model.internal.builder;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.beef.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.beef.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.EcoreUtils;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.CompositeModule;
import org.sourcepit.beef.b2.model.module.PluginProject;
import org.sourcepit.beef.b2.model.module.PluginsFacet;
import org.sourcepit.beef.b2.model.module.test.internal.harness.B2ModelHarness;
import org.sourcepit.beef.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;

/**
 * @author Bernd
 */
public class B2ModelBuilderTest extends AbstractInjectedWorkspaceTest
{
   /**
    * Test if we are able to lookup and obtain our module instance from the container.
    * 
    * @throws Exception
    */
   public void testBasic() throws Exception
   {
      B2ModelBuilder module = lookup();
      assertNotNull(module);
   }

   public void testNull() throws Exception
   {
      B2ModelBuilder module = lookup();
      try
      {
         module.build(null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);

      try
      {
         module.build(request);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      request.setModuleDirectory(new File(""));
      request.setConverter(null);

      try
      {
         module.build(request);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
   }

   public void testSimpleComponent() throws Exception
   {
      File coreResources = workspace.importResources("composed-component/simple-layout");
      assertTrue(coreResources.canRead());

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(coreResources);

      B2ModelBuilder modelBuilder = lookup();
      BasicModule module = (BasicModule) modelBuilder.build(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertEquals(module.getId(), request.getConverter().getModuleId(coreResources));

      assertEquals(4, module.getFacets().size());

      PluginsFacet pluginsFacet = module.getFacetByName("plugins");
      assertNotNull(pluginsFacet);

      EList<PluginProject> plugins = pluginsFacet.getProjects();
      assertEquals(2, plugins.size());

      PluginsFacet testsFacet = module.getFacetByName("tests");
      assertNotNull(testsFacet);

      EList<PluginProject> tests = pluginsFacet.getProjects();
      assertEquals(2, tests.size());
   }

   public void testComposedComposite() throws Exception
   {
      final File moduleDir = workspace.importResources("composed-component");

      // get dummy module files
      final File parentFile = moduleDir;
      final File simpleFile = new File(moduleDir, "simple-layout");
      final File structuredFile = new File(moduleDir, "structured-layout");

      final B2ModelBuilder builder = lookup();

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(structuredFile);
      BasicModule structuredModule = (BasicModule) builder.build(request);

      request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(simpleFile);

      BasicModule simpleModule = (BasicModule) builder.build(request);

      request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(parentFile);

      CompositeModule compositeModule = (CompositeModule) builder.build(request);
      assertEquals("composite", compositeModule.getLayoutId());

      final EList<AbstractModule> modules = compositeModule.getModules();
      assertEquals(2, modules.size());

      // folder ordering differs between win and linux, we can't rely on the index
      final boolean firstIsSimple = modules.get(0).getDirectory().equals(simpleModule.getDirectory());
      EcoreUtils.assertEEquals(simpleModule, (BasicModule) modules.get(firstIsSimple ? 0 : 1));
      EcoreUtils.assertEEquals(structuredModule, (BasicModule) modules.get(firstIsSimple ? 1 : 0));
   }

   public void testSkipInterpolator() throws Exception
   {
      File coreResources = workspace.importResources("composed-component/simple-layout");
      assertTrue(coreResources.canRead());

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(coreResources);
      request.setInterpolate(true);

      B2ModelBuilder modelBuilder = lookup();

      BasicModule module = (BasicModule) modelBuilder.build(request);
      assertNotNull(module);
      B2ModelHarness.assertHasDerivedElements(module);

      module = (BasicModule) modelBuilder.build(request);
      request.setInterpolate(false);
      assertNotNull(module);
      B2ModelHarness.assertHasDerivedElements(module);
   }

   private B2ModelBuilder lookup() throws Exception
   {
      return (B2ModelBuilder) lookup(IB2ModelBuilder.class);
   }
}
