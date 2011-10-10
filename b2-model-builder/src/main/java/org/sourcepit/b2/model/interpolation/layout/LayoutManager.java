/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.layout;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class LayoutManager
{
   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   public Map<String, IInterpolationLayout> getLayoutMap()
   {
      return layoutMap;
   }

   public IInterpolationLayout getLayout(String layoutId)
   {
      final IInterpolationLayout layout = layoutMap.get(layoutId);
      if (layout == null)
      {
         throw new IllegalStateException("Layout " + layoutId + " is not supported.");
      }
      return layout;
   }
}
