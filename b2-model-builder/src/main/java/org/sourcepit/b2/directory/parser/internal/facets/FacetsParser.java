/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.facets;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.AbstractFacet;

@Named
public class FacetsParser
{
   @Inject
   private List<AbstractFacetsParserRule<? extends AbstractFacet>> rules;

   public FacetsParseResult<? extends AbstractFacet> parse(File directory, IConverter converter)
   {
      checkArgs(directory, converter);

      for (AbstractFacetsParserRule<? extends AbstractFacet> rule : rules)
      {
         final FacetsParseResult<? extends AbstractFacet> result = rule.parse(directory, converter);
         if (result != null)
         {
            return result;
         }
      }
      return null;
   }

   private void checkArgs(File directory, IConverter converter)
   {
      if (directory == null)
      {
         throw new IllegalArgumentException("Directoy must not be null.");
      }

      if (converter == null)
      {
         throw new IllegalArgumentException("converter must not be null.");
      }
   }
}
