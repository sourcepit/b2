/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
public class BasicModuleParserRule extends AbstractModuleParserRule<BasicModule>
{
   @Inject
   private FacetsParser facetsParser;

   @Override
   protected BasicModule doParse(IModuleParsingRequest request)
   {
      final File baseDir = request.getModuleFiles().getModuleDir();
      final PropertiesSource properties = request.getModuleProperties();

      final FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(baseDir, properties);
      if (result == null)
      {
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
