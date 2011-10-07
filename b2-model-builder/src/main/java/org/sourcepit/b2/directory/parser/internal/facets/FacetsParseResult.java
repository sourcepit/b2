/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
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
