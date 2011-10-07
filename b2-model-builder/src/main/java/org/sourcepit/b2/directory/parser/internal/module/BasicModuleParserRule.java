/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.facets.FacetsParseResult;
import org.sourcepit.b2.directory.parser.internal.facets.FacetsParser;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;

@Named("module")
public class BasicModuleParserRule extends AbstractModuleParserRule<BasicModule>
{
   @Inject
   private FacetsParser facetsParser;

   @Override
   protected BasicModule doParse(IModuleParsingRequest request)
   {
      final File baseDir = request.getModuleDirectory();

      final FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(baseDir, request.getConverter());
      if (result == null)
      {
         return null;
      }

      final IConverter converter = request.getConverter();
      final List<? extends AbstractFacet> facets = result.getFacets();

      final BasicModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.setId(getModuleId(converter, baseDir));
      module.setVersion(getModuleVersion(converter));
      module.setDirectory(baseDir);
      module.getFacets().addAll(facets);
      module.setLayoutId(result.getLayout());
      return module;
   }
}
