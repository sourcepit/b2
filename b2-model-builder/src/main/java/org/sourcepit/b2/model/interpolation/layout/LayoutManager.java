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

package org.sourcepit.b2.model.interpolation.layout;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
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
