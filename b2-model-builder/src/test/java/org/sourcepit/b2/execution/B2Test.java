/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.execution;

import java.io.File;

import javax.inject.Inject;

import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.generator.DefaultTemplateCopier;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.test.internal.harness.B2ModelHarness;
import org.sourcepit.common.utils.props.PropertiesMap;

public class B2Test extends AbstractB2SessionWorkspaceTest {
   @Inject
   private B2 b2;

   @Inject
   private BasicConverter converter;

   @Override
   protected String setUpModulePath() {
      return "composed-component/simple-layout";
   }

   public void testSkipInterpolator() throws Exception {
      File moduleDir = getModuleDirs().get(0);
      assertTrue(moduleDir.canRead());

      PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();
      properties.put("b2.skipInterpolator", "true");
      properties.put("b2.skipGenerator", "true");

      assertTrue(converter.isSkipInterpolator(properties));
      assertTrue(converter.isSkipGenerator(properties));

      B2Request request = new B2Request();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
      request.setModuleProperties(properties);
      request.setTemplates(new DefaultTemplateCopier());

      AbstractModule module = b2.generate(request);

      assertNotNull(module);
      B2ModelHarness.assertHasNoDerivedElements(module);
   }

}
