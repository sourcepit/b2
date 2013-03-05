/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.internal.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.EcoreUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.test.internal.harness.B2ModelHarness;

/**
 * @author Bernd
 */
public class B2ModelBuilderTest extends AbstractB2SessionWorkspaceTest
{
   @Override
   protected String setUpModulePath()
   {
      if ("testComposedComposite".equals(getName()))
      {
         return "composed-component";
      }
      return "composed-component/simple-layout";
   }

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
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());

      try
      {
         module.build(request);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      request.setModuleDirectory(new File(""));
      request.setModuleProperties(null);

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
      File coreResources = getModuleDirs().get(0);
      assertTrue(coreResources.canRead());

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setModuleProperties(ModelBuilderTestHarness.newProperties(coreResources));
      request.setModuleDirectory(coreResources);

      B2ModelBuilder modelBuilder = lookup();
      BasicModule module = (BasicModule) modelBuilder.build(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertEquals("org.sourcepit.b2.test.resources.simple.layout", module.getId());

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
      final File moduleDir = getModuleDirByName("composite-layout");
      assertNotNull(moduleDir);

      // get dummy module files
      final File parentFile = moduleDir;
      final File simpleFile = new File(moduleDir, "simple-layout");
      final File structuredFile = new File(moduleDir, "structured-layout");

      final B2ModelBuilder builder = lookup();

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(simpleFile);

      BasicModule simpleModule = (BasicModule) builder.build(request);
      assertNotNull(simpleModule);

      List<AbstractModule> currentModules = new ArrayList<AbstractModule>();
      currentModules.add(simpleModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(structuredFile);
      BasicModule structuredModule = (BasicModule) builder.build(request);

      currentModules.add(structuredModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(parentFile);

      for (AbstractModule module : currentModules)
      {
         request.getModulesCache().put(module.getDirectory(), module);
      }

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
      File coreResources = getModuleDirs().get(0);
      assertTrue(coreResources.canRead());

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(coreResources);
      request.setInterpolate(true);

      B2ModelBuilder modelBuilder = lookup();

      BasicModule module = (BasicModule) modelBuilder.build(request);
      assertNotNull(module);
      B2ModelHarness.assertHasDerivedElements(module);

      request.setInterpolate(false);
      module = (BasicModule) modelBuilder.build(request);
      assertNotNull(module);
      B2ModelHarness.assertHasNoDerivedElements(module);
   }

   private B2ModelBuilder lookup() throws Exception
   {
      return (B2ModelBuilder) lookup(IB2ModelBuilder.class);
   }
}
