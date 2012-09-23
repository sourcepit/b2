/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelReader;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.IB2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.internal.util.EWalkerImpl;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.inject.Binder;

public abstract class AbstractPomGeneratorTest extends AbstractB2SessionWorkspaceTest
{
   @Inject
   protected IB2ModelBuilder modelBuilder;

   @Inject
   protected PomGenerator pomGenerator;

   @Inject
   protected Map<String, IInterpolationLayout> layoutMap;

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);
   }

   protected IInterpolationLayout getLayout(BasicModule module)
   {
      return layoutMap.get(module.getLayoutId());
   }

   @SuppressWarnings("unchecked")
   protected <T extends AbstractModule> T buildModel(File moduleDir) throws Exception
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
      request.setModuleDirectory(moduleDir);
      request.setInterpolate(false);
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
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
      final PropertiesSource source = ConverterUtils.newDefaultTestConverter(properties);
      final DefaultTemplateCopier templateCopier = new DefaultTemplateCopier();
      new EWalkerImpl(pomGenerator.isReverse(), true)
      {
         @Override
         protected boolean visit(EObject eObject)
         {
            if (pomGenerator.isGeneratorInput(eObject))
            {
               pomGenerator.generate(eObject, source, templateCopier);
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
