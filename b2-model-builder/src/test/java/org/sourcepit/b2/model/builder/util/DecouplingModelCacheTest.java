/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.createModuleDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.internal.builder.B2ModelBuilder;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;

/**
 * @author Bernd
 */
public class DecouplingModelCacheTest extends AbstractB2SessionWorkspaceTest
{
   @Override
   protected String setUpModulePath()
   {
      return "composed-component";
   }

   public void testUndecoupled() throws Exception
   {
      final File moduleDir = getModuleDirByName("composite-layout");

      // get dummy module files
      final File parentFile = moduleDir;
      final File simpleFile = new File(moduleDir, "simple-layout");
      final File structuredFile = new File(moduleDir, "structured-layout");

      final B2ModelBuilder builder = lookup();

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(new ModuleDirectory(simpleFile, null));

      BasicModule simpleModule = (BasicModule) builder.build(request);

      List<AbstractModule> currentModules = new ArrayList<AbstractModule>();
      currentModules.add(simpleModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(new ModuleDirectory(structuredFile, null));

      BasicModule structuredModule = (BasicModule) builder.build(request);

      currentModules.add(structuredModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(new ModuleDirectory(parentFile, null));

      for (AbstractModule module : currentModules)
      {
         request.getModulesCache().put(module.getDirectory(), module);
      }
      
      request.setModuleDirectory(createModuleDirectory(moduleDir, simpleFile, structuredFile));

      CompositeModule compositeModule = (CompositeModule) builder.build(request);
      assertEquals("composite", compositeModule.getLayoutId());

      final EList<AbstractModule> modules = compositeModule.getModules();
      assertEquals(2, modules.size());

      // folder ordering differs between win and linux, we can't rely on the index
      int idx = modules.indexOf(simpleModule);
      assertTrue(idx > -1);
      assertSame(simpleModule, modules.get(idx));

      idx = modules.indexOf(structuredModule);
      assertTrue(idx > -1);
      assertSame(structuredModule, modules.get(idx));
   }

   public void testDecoupled() throws Exception
   {
      final File moduleDir = getModuleDirByName("composite-layout");

      // get dummy module files
      final File parentFile = moduleDir;
      final File simpleFile = new File(moduleDir, "simple-layout");
      final File structuredFile = new File(moduleDir, "structured-layout");

      final B2ModelBuilder builder = lookup();

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(new ModuleDirectory(simpleFile, null));

      BasicModule simpleModule = (BasicModule) builder.build(request);

      List<AbstractModule> currentModules = new ArrayList<AbstractModule>();
      currentModules.add(simpleModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(new ModuleDirectory(structuredFile, null));

      BasicModule structuredModule = (BasicModule) builder.build(request);

      currentModules.add(structuredModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(new ModuleDirectory(parentFile, null));

      for (AbstractModule module : currentModules)
      {
         request.getModulesCache().put(module.getDirectory(), module);
      }
      
      request.setModuleDirectory(createModuleDirectory(moduleDir, simpleFile, structuredFile));

      CompositeModule compositeModule = (CompositeModule) builder.build(request);
      assertEquals("composite", compositeModule.getLayoutId());

      final EList<AbstractModule> modules = compositeModule.getModules();
      assertEquals(2, modules.size());
   }

   private B2ModelBuilder lookup() throws Exception
   {
      return (B2ModelBuilder) lookup(IB2ModelBuilder.class);
   }
}
