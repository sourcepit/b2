/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.interpolation.layout;

import java.util.List;

import javax.inject.Inject;

import org.sonatype.guice.bean.containers.InjectedTestCase;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;

public class InterpolationLayoutsTest extends InjectedTestCase
{
   @Inject
   private List<IInterpolationLayout> layouts;

   public void testIdOfProject() throws Exception
   {
      BasicModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
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
