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

package org.sourcepit.b2.directory.parser.internal.facets;

import java.util.Collections;
import java.util.List;

import org.sourcepit.b2.model.module.AbstractFacet;

public class FacetsParseResult<F extends AbstractFacet>
{
   private final List<F> facets;

   private final String layout;

   public FacetsParseResult(String layout, List<F> facets)
   {
      if (layout == null)
      {
         throw new IllegalArgumentException("Layout must not be null.");
      }
      this.layout = layout;
      if (facets == null)
      {
         throw new IllegalArgumentException("Facets must not be null.");
      }
      this.facets = facets;
   }

   public String getLayout()
   {
      return layout;
   }

   public List<F> getFacets()
   {
      return Collections.unmodifiableList(facets);
   }
}
