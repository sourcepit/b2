/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.module;

import java.io.File;

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.IModelCache;

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
