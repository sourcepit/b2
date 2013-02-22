/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.internal.builder.B2ModelBuilder;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;

/**
 * @author Bernd
 */
public class DecouplingModelCacheTest extends AbstractB2SessionWorkspaceTest
{
   @Inject
   private LayoutManager layoutManager;

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
      request.setModuleDirectory(simpleFile);

      BasicModule simpleModule = (BasicModule) builder.build(request);

      sessionService.getCurrentModules().add(simpleModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(structuredFile);

      BasicModule structuredModule = (BasicModule) builder.build(request);

      sessionService.getCurrentModules().add(structuredModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(parentFile);

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
      request.setModuleDirectory(simpleFile);

      BasicModule simpleModule = (BasicModule) builder.build(request);

      sessionService.getCurrentModules().add(simpleModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(structuredFile);

      BasicModule structuredModule = (BasicModule) builder.build(request);

      sessionService.getCurrentModules().add(structuredModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(parentFile);

      CompositeModule compositeModule = (CompositeModule) builder.build(request);
      assertEquals("composite", compositeModule.getLayoutId());

      final EList<AbstractModule> modules = compositeModule.getModules();
      assertEquals(2, modules.size());

      // folder ordering differs between win and linux, we can't rely on the index


      // AbstractModule _simpleModule = modelCache.get(simpleModule.getDirectory());
      // AbstractModule _structuredModule = modelCache.get(structuredModule.getDirectory());
      // int idxSimple = modules.indexOf(_simpleModule);
      // int idxStructured = modules.indexOf(_structuredModule);
      // assertEquals(_simpleModule.getId(), modules.get(idxSimple).getId());
      // assertEquals(_structuredModule.getId(), modules.get(idxStructured).getId());
      // assertEquals(simpleModule.getId(), modules.get(idxSimple).getId());
      // assertEquals(structuredModule.getId(), modules.get(idxStructured).getId());
      // EcoreUtils.assertEEquals(simpleModule, (BasicModule) modules.get(idxSimple));
      // EcoreUtils.assertEEquals(structuredModule, (BasicModule) modules.get(idxStructured));
   }


   public void _testDecoupled_Interpolated() throws Exception
   {
      final File moduleDir = getModuleDirByName("composite-layout");

      // get dummy module files
      final File parentFile = moduleDir;
      final File simpleFile = new File(moduleDir, "simple-layout");
      final File structuredFile = new File(moduleDir, "structured-layout");

      File simpleSite = new File(simpleFile, "example.site");
      assertTrue(simpleSite.exists());
      FileUtils.deleteDirectory(simpleSite);
      assertFalse(simpleSite.exists());

      File structuredSite = new File(structuredFile, "sites");
      assertTrue(structuredSite.exists());
      FileUtils.deleteDirectory(structuredSite);
      assertFalse(structuredSite.exists());

      final B2ModelBuilder builder = lookup();

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(simpleFile);
      request.setInterpolate(true);

      BasicModule simpleModule = (BasicModule) builder.build(request);

      sessionService.getCurrentModules().add(simpleModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(structuredFile);
      request.setInterpolate(false);

      BasicModule structuredModule = (BasicModule) builder.build(request);

      sessionService.getCurrentModules().add(structuredModule);

      request = new B2ModelBuildingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setModuleDirectory(parentFile);
      request.setInterpolate(true);

      CompositeModule compositeModule = (CompositeModule) builder.build(request);
      assertEquals("composite", compositeModule.getLayoutId());

      final EList<AbstractModule> modules = compositeModule.getModules();
      assertEquals(2, modules.size());

      // folder ordering differs between win and linux, we can't rely on the index


      // AbstractModule _simpleModule = modelCache.get(simpleModule.getDirectory());
      // AbstractModule _structuredModule = modelCache.get(structuredModule.getDirectory());
      // int idxSimple = modules.indexOf(_simpleModule);
      // int idxStructured = modules.indexOf(_structuredModule);
      // assertEquals(_simpleModule.getId(), modules.get(idxSimple).getId());
      // assertEquals(_structuredModule.getId(), modules.get(idxStructured).getId());
      // assertEquals(simpleModule.getId(), modules.get(idxSimple).getId());
      // assertEquals(structuredModule.getId(), modules.get(idxStructured).getId());
      // EcoreUtils.assertEEquals(simpleModule, (BasicModule) modules.get(idxSimple));
      // EcoreUtils.assertEEquals(structuredModule, (BasicModule) modules.get(idxStructured));
   }

   private B2ModelBuilder lookup() throws Exception
   {
      return (B2ModelBuilder) lookup(IB2ModelBuilder.class);
   }
}
