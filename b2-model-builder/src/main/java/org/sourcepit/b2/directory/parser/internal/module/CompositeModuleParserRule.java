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

import static org.sourcepit.b2.files.ModuleDirectory.FLAG_MODULE_DIR;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.files.FileVisitor;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named("compositeModule")
public class CompositeModuleParserRule extends AbstractModuleParserRule<CompositeModule> {
   @Override
   protected CompositeModule doParse(final IModuleParsingRequest request) {
      final List<AbstractModule> modules = new ArrayList<AbstractModule>();

      final File baseDir = request.getModuleDirectory().getFile();
      final PropertiesSource properties = request.getModuleProperties();

      final List<File> moduleDirs = request.getModuleDirectory().getFiles(new FileVisitor<RuntimeException>() {
         @Override
         public boolean visit(File file, int flags) {
            return (flags & FLAG_MODULE_DIR) != 0;
         }
      }, 0xff);

      for (File moduleDir : moduleDirs) {
         final Map<File, AbstractModule> modulesCache = request.getModulesCache();

         final AbstractModule nestedModule = modulesCache.get(moduleDir);

         if (nestedModule == null) {
            throw new IllegalStateException("Invalid build order");
         }

         modules.add(nestedModule);
      }

      // baseDir.listFiles(new FileFilter()
      // {
      // public boolean accept(File member)
      // {
      // if (converter.isPotentialModuleDirectory(request.getModuleProperties(), baseDir, member))
      // {
      // if (moduleFilter == null || moduleFilter.accept(member))
      // {
      // final Map<File, AbstractModule> modulesCache = request.getModulesCache();
      //
      // final AbstractModule nestedModule = modulesCache.get(member);
      //
      // if (nestedModule == null)
      // {
      // throw new IllegalStateException("Invalid build order");
      // }
      //
      // modules.add(nestedModule);
      // }
      // }
      // return false;
      // }
      // });

      final CompositeModule compositeModule = ModuleModelFactory.eINSTANCE.createCompositeModule();
      compositeModule.setId(getModuleId(compositeModule, properties));
      compositeModule.setVersion(getModuleVersion(properties));
      compositeModule.setDirectory(baseDir);
      compositeModule.setLayoutId("composite");
      compositeModule.getModules().addAll(modules);
      return compositeModule;
   }

   @Override
   protected int getPriority() {
      return 0;
   }
}
