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

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.facets.FacetsParseResult;
import org.sourcepit.b2.directory.parser.internal.facets.FacetsParser;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named("module")
public class BasicModuleParserRule extends AbstractModuleParserRule<BasicModule> {
   @Inject
   private FacetsParser facetsParser;

   @Override
   protected BasicModule doParse(IModuleParsingRequest request) {
      final File baseDir = request.getModuleDirectory().getFile();
      final PropertiesSource properties = request.getModuleProperties();

      final FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(baseDir, properties);
      if (result == null) {
         return null;
      }

      final List<? extends AbstractFacet> facets = result.getFacets();

      final BasicModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.setId(getModuleId(module, properties));
      module.setVersion(getModuleVersion(properties));
      module.setDirectory(baseDir);
      module.getFacets().addAll(facets);
      module.setLayoutId(result.getLayout());
      return module;
   }
}
