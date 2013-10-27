/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import java.io.File;

import javax.inject.Inject;

import org.sourcepit.b2.files.ModuleFiles;
import org.sourcepit.b2.internal.generator.DefaultTemplateCopier;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.test.internal.harness.B2ModelHarness;
import org.sourcepit.common.utils.props.PropertiesMap;

public class B2Test extends AbstractB2SessionWorkspaceTest
{
   @Inject
   private B2 b2;

   @Inject
   private BasicConverter converter;

   @Override
   protected String setUpModulePath()
   {
      return "composed-component/simple-layout";
   }

   public void testSkipInterpolator() throws Exception
   {
      File moduleDir = getModuleDirs().get(0);
      assertTrue(moduleDir.canRead());

      PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();
      properties.put("b2.skipInterpolator", "true");
      properties.put("b2.skipGenerator", "true");

      assertTrue(converter.isSkipInterpolator(properties));
      assertTrue(converter.isSkipGenerator(properties));

      B2Request request = new B2Request();
      request.setModuleFiles(new ModuleFiles(moduleDir, null));
      request.setModuleProperties(properties);
      request.setTemplates(new DefaultTemplateCopier());

      AbstractModule module = b2.generate(request);

      assertNotNull(module);
      B2ModelHarness.assertHasNoDerivedElements(module);
   }

}
