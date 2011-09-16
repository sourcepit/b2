/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.interpolation.layout;

import java.util.List;

import javax.inject.Inject;

import org.sonatype.guice.bean.containers.InjectedTestCase;
import org.sourcepit.beef.b2.model.module.ModuleFactory;
import org.sourcepit.beef.b2.model.module.BasicModule;

public class InterpolationLayoutsTest extends InjectedTestCase
{
   @Inject
   private List<IInterpolationLayout> layouts;

   public void testIdOfProject() throws Exception
   {
      BasicModule module = ModuleFactory.eINSTANCE.createBasicModule();
      module.setId("foo.module");
      assertFalse(layouts.isEmpty());

      for (IInterpolationLayout layout : layouts)
      {
         assertEquals("foo.module.feature", layout.idOfFeatureProject(module, null));
         assertEquals("foo.module.feature", layout.idOfFeatureProject(module, ""));
         assertEquals("foo.module.sdk.feature", layout.idOfFeatureProject(module, "sdk"));
      }
   }
}
