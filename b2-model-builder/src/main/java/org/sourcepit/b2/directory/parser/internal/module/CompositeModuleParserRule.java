/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.module.IModuleFilter;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named("compositeModule")
public class CompositeModuleParserRule extends AbstractModuleParserRule<CompositeModule>
{
   @Inject
   private BasicConverter converter;

   @Override
   protected CompositeModule doParse(final IModuleParsingRequest request)
   {
      final List<AbstractModule> modules = new ArrayList<AbstractModule>();

      final File baseDir = request.getModuleDirectory();
      final IModuleFilter moduleFilter = request.getModuleFilter();
      final PropertiesSource properties = request.getModuleProperties();

      baseDir.listFiles(new FileFilter()
      {
         public boolean accept(File member)
         {
            if (converter.isPotentialModuleDirectory(request.getModuleProperties(), baseDir, member))
            {
               if (moduleFilter == null || moduleFilter.accept(member))
               {
                  final Map<File, AbstractModule> modulesCache = request.getModulesCache();

                  final AbstractModule nestedModule = modulesCache.get(member);

                  if (nestedModule == null)
                  {
                     throw new IllegalStateException("Invalid build order");
                  }

                  modules.add(nestedModule);
               }
            }
            return false;
         }
      });

      final CompositeModule compositeModule = ModuleModelFactory.eINSTANCE.createCompositeModule();
      compositeModule.setId(getModuleId(compositeModule, properties));
      compositeModule.setVersion(getModuleVersion(properties));
      compositeModule.setDirectory(baseDir);
      compositeModule.setLayoutId("composite");
      compositeModule.getModules().addAll(modules);
      return compositeModule;
   }

   @Override
   protected int getPriority()
   {
      return 0;
   }
}
