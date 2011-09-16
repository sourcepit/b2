/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelReader;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.beef.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.beef.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.beef.b2.model.builder.IB2ModelBuildingRequest;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.beef.b2.model.builder.util.DefaultConverter;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.internal.util.EWalkerImpl;
import org.sourcepit.beef.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;

public abstract class AbstractPomGeneratorTest extends AbstractInjectedWorkspaceTest
{
   @Inject
   protected IB2ModelBuilder modelBuilder;

   @Inject
   protected PomGenerator pomGenerator;

   @Inject
   protected Map<String, IInterpolationLayout> layoutMap;

   protected IInterpolationLayout getLayout(BasicModule module)
   {
      IInterpolationLayout layout = layoutMap.get(module.getLayoutId());
      return layout;
   }

   @SuppressWarnings("unchecked")
   protected <T extends AbstractModule> T buildModel(String path) throws IOException
   {
      File moduleDir = workspace.importResources(path);
      assertTrue(moduleDir.canRead());
      BasicModule module = buildModel(moduleDir.getAbsoluteFile());
      return (T) module;
   }

   @SuppressWarnings("unchecked")
   protected <T extends AbstractModule> T buildModel(File moduleDir)
   {
      B2ModelBuildingRequest request = newModelBuildingRequest(moduleDir);
      return (T) buildModel(request);
   }

   @SuppressWarnings("unchecked")
   protected <T extends AbstractModule> T buildModel(IB2ModelBuildingRequest request)
   {
      return (T) modelBuilder.build(request);
   }

   protected static B2ModelBuildingRequest newModelBuildingRequest(File moduleDir)
   {
      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(moduleDir);
      request.setInterpolate(false);
      return request;
   }

   protected void assertIsGeneratorInput(EObject eObject)
   {
      assertTrue(pomGenerator.isGeneratorInput(eObject));
   }

   protected void generatePom(EObject eObject, PropertiesMap properties)
   {
      pomGenerator.generate(eObject, ConverterUtils.newDefaultTestConverter(properties), new DefaultTemplateCopier());
   }

   protected void generateAllPoms(EObject eObject, PropertiesMap properties)
   {
      final DefaultConverter converter = ConverterUtils.newDefaultTestConverter(properties);
      final DefaultTemplateCopier templateCopier = new DefaultTemplateCopier();
      new EWalkerImpl(pomGenerator.isReverse(), true)
      {
         @Override
         protected boolean visit(EObject eObject)
         {
            if (pomGenerator.isGeneratorInput(eObject))
            {
               pomGenerator.generate(eObject, converter, templateCopier);
            }
            return true;
         }
      }.walk(eObject);
   }

   protected static void assertNoPomFiles(File file)
   {
      if (file.isFile())
      {
         assertFalse("pom.xml".equals(file.getName()));
      }
      else
      {
         File[] members = file.listFiles();
         if (members != null)
         {
            for (File member : members)
            {
               assertNoPomFiles(member);
            }
         }
      }
   }

   protected static Model readMavenModel(File pomFile)
   {
      final Map<String, String> options = new HashMap<String, String>();
      options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());
      try
      {
         return new DefaultModelReader().read(pomFile, options);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }
}
