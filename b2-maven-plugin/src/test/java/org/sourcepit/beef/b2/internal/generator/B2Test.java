/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;

import javax.inject.Inject;

import org.sourcepit.beef.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.beef.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.internal.test.harness.B2ModelHarness;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;

public class B2Test extends AbstractInjectedWorkspaceTest
{
   @Inject
   private B2 b2;

   public void testSkipInterpolator() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.canRead());

      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.skipInterpolator", "true");
      properties.put("b2.skipGenerator", "true");

      IConverter converter = ConverterUtils.newDefaultTestConverter(properties);
      assertTrue(converter.isSkipInterpolator());
      assertTrue(converter.isSkipGenerator());

      AbstractModule module = b2.generate(moduleDir, null, null, converter, new DefaultTemplateCopier());

      assertNotNull(module);
      B2ModelHarness.assertHasDerivedElements(module);
   }
}
