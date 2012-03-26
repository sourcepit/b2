/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.layout;

import java.util.List;

import javax.inject.Inject;

import org.sonatype.guice.bean.containers.InjectedTestCase;
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
