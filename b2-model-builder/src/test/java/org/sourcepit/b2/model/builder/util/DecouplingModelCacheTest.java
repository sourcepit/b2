/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.b2.model.builder.internal.tests.harness.EcoreUtils;
import org.sourcepit.b2.model.builder.util.DecouplingModelCache;
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
      final File moduleDir = getModuleDirByArtifactId("composite-layout");

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

      final DecouplingModelCache modelCache = createCache(null);
      modelCache.put(simpleModule);
      modelCache.put(structuredModule);

      request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(parentFile);
      request.setModelCache(modelCache);

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
      final File moduleDir = getModuleDirByArtifactId("composite-layout");

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

      DecouplingModelCache modelCache = createCache(null);
      modelCache.put(simpleModule);
      modelCache.put(structuredModule);

      // re-init, living eObjects will die
      modelCache = createCache(modelCache.getDirToUriMap());

      request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(parentFile);
      request.setModelCache(modelCache);

      CompositeModule compositeModule = (CompositeModule) builder.build(request);
      assertEquals("composite", compositeModule.getLayoutId());

      final EList<AbstractModule> modules = compositeModule.getModules();
      assertEquals(2, modules.size());

      // folder ordering differs between win and linux, we can't rely on the index
      AbstractModule _simpleModule = modelCache.get(simpleModule.getDirectory());
      AbstractModule _structuredModule = modelCache.get(structuredModule.getDirectory());
      int idxSimple = modules.indexOf(_simpleModule);
      int idxStructured = modules.indexOf(_structuredModule);
      assertSame(_simpleModule, modules.get(idxSimple));
      assertSame(_structuredModule, modules.get(idxStructured));
      assertNotSame(simpleModule, modules.get(idxSimple));
      assertNotSame(structuredModule, modules.get(idxStructured));
      EcoreUtils.assertEEquals(simpleModule, (BasicModule) modules.get(idxSimple));
      EcoreUtils.assertEEquals(structuredModule, (BasicModule) modules.get(idxStructured));
   }


   public void testDecoupled_Interpolated() throws Exception
   {
      final File moduleDir = getModuleDirByArtifactId("composite-layout");

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
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(structuredFile);
      request.setInterpolate(false);

      BasicModule structuredModule = (BasicModule) builder.build(request);

      request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(simpleFile);
      request.setInterpolate(true);

      BasicModule simpleModule = (BasicModule) builder.build(request);

      DecouplingModelCache modelCache = createCache(null);
      modelCache.put(simpleModule);
      modelCache.put(structuredModule);

      // re-init, living eObjects will die
      modelCache = createCache(modelCache.getDirToUriMap());

      request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(parentFile);
      request.setModelCache(modelCache);
      request.setInterpolate(true);

      CompositeModule compositeModule = (CompositeModule) builder.build(request);
      assertEquals("composite", compositeModule.getLayoutId());

      final EList<AbstractModule> modules = compositeModule.getModules();
      assertEquals(2, modules.size());

      // folder ordering differs between win and linux, we can't rely on the index
      AbstractModule _simpleModule = modelCache.get(simpleModule.getDirectory());
      AbstractModule _structuredModule = modelCache.get(structuredModule.getDirectory());
      int idxSimple = modules.indexOf(_simpleModule);
      int idxStructured = modules.indexOf(_structuredModule);
      assertSame(_simpleModule, modules.get(idxSimple));
      assertSame(_structuredModule, modules.get(idxStructured));
      assertNotSame(simpleModule, modules.get(idxSimple));
      assertNotSame(structuredModule, modules.get(idxStructured));
      EcoreUtils.assertEEquals(simpleModule, (BasicModule) modules.get(idxSimple));
      EcoreUtils.assertEEquals(structuredModule, (BasicModule) modules.get(idxStructured));
   }

   private DecouplingModelCache createCache(Map<File, String> dirToUriMap)
   {
      ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());
      final DecouplingModelCache modelCache = new DecouplingModelCache(resourceSet, layoutManager);
      if (dirToUriMap != null)
      {
         modelCache.getDirToUriMap().putAll(dirToUriMap);
      }
      return modelCache;
   }

   private B2ModelBuilder lookup() throws Exception
   {
      return (B2ModelBuilder) lookup(IB2ModelBuilder.class);
   }
}
