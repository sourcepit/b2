/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
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
