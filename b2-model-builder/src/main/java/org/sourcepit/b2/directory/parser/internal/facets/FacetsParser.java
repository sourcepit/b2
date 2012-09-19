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

import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class FacetsParser
{
   @Inject
   private List<AbstractFacetsParserRule<? extends AbstractFacet>> rules;

   public FacetsParseResult<? extends AbstractFacet> parse(File directory, PropertiesSource properties)
   {
      checkArgs(directory, properties);

      for (AbstractFacetsParserRule<? extends AbstractFacet> rule : rules)
      {
         final FacetsParseResult<? extends AbstractFacet> result = rule.parse(directory, properties);
         if (result != null)
         {
            return result;
         }
      }
      return null;
   }

   private void checkArgs(File directory, PropertiesSource properties)
   {
      if (directory == null)
      {
         throw new IllegalArgumentException("Directoy must not be null.");
      }

      if (properties == null)
      {
         throw new IllegalArgumentException("properties must not be null.");
      }
   }
}
