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

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class FacetsParser {
   @Inject
   private List<AbstractFacetsParserRule<? extends AbstractFacet>> rules;

   public FacetsParseResult<? extends AbstractFacet> parse(File directory, PropertiesSource properties) {
      checkArgs(directory, properties);

      for (AbstractFacetsParserRule<? extends AbstractFacet> rule : rules) {
         final FacetsParseResult<? extends AbstractFacet> result = rule.parse(directory, properties);
         if (result != null) {
            return result;
         }
      }
      return null;
   }

   private void checkArgs(File directory, PropertiesSource properties) {
      if (directory == null) {
         throw new IllegalArgumentException("Directoy must not be null.");
      }

      if (properties == null) {
         throw new IllegalArgumentException("properties must not be null.");
      }
   }
}
