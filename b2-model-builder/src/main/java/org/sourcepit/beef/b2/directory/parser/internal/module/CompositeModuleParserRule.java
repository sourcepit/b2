/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.module;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.beef.b2.directory.parser.module.IModuleParser;
import org.sourcepit.beef.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.beef.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.internal.model.B2ModelFactory;
import org.sourcepit.beef.b2.internal.model.CompositeModule;

@Named("compositeModule")
public class CompositeModuleParserRule extends AbstractModuleParserRule<CompositeModule>
{
   @Inject
   private IModuleParser moduleParser;

   @Override
   protected CompositeModule doParse(final IModuleParsingRequest request)
   {
      final List<AbstractModule> modules = new ArrayList<AbstractModule>();

      final File baseDir = request.getModuleDirectory();

      baseDir.listFiles(new FileFilter()
      {
         public boolean accept(File member)
         {
            if (member.isDirectory() && member.exists())
            {
               if (new File(member, "module.xml").exists())
               {
                  final ModuleParsingRequest copy = ModuleParsingRequest.copy(request);
                  copy.setModuleDirectory(member);

                  final AbstractModule module = moduleParser.parse(copy);
                  if (module != null)
                  {
                     modules.add(module);
                  }
               }
            }
            return false;
         }
      });

      // if (modules.isEmpty())
      // {
      // return null;
      // }

      final CompositeModule compositeModule = B2ModelFactory.eINSTANCE.createCompositeModule();
      compositeModule.setId(getModuleId(request.getConverter(), baseDir));
      compositeModule.setVersion(getModuleVersion(request.getConverter()));
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
