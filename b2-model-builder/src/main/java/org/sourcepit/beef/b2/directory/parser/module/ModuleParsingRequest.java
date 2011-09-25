/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.module;

import java.io.File;

import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;

public class ModuleParsingRequest implements IModuleParsingRequest
{
   private File moduleDirectory;

   private IConverter converter;

   private IModelCache modelCache;

   private IModuleFilter moduleFilter;

   public static ModuleParsingRequest copy(IModuleParsingRequest request)
   {
      ModuleParsingRequest copy = new ModuleParsingRequest();
      copy.setModuleDirectory(request.getModuleDirectory());
      copy.setConverter(request.getConverter());
      copy.setModelCache(request.getModelCache());
      return copy;
   }

   /**
    * {@inheritDoc}
    */
   public File getModuleDirectory()
   {
      return moduleDirectory;
   }

   public void setModuleDirectory(File moduleDirectory)
   {
      this.moduleDirectory = moduleDirectory;
   }

   /**
    * {@inheritDoc}
    */
   public IConverter getConverter()
   {
      return converter;
   }

   public void setConverter(IConverter converter)
   {
      this.converter = converter;
   }

   /**
    * {@inheritDoc}
    */
   public IModelCache getModelCache()
   {
      return modelCache;
   }

   public void setModelCache(IModelCache modelCache)
   {
      this.modelCache = modelCache;
   }

   public IModuleFilter getModuleFilter()
   {
      return moduleFilter;
   }

   public void setModuleFilter(IModuleFilter moduleFilter)
   {
      this.moduleFilter = moduleFilter;
   }
}
