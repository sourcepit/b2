/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.interpolation.module;

import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;

/**
 * @author Bernd
 */
public class ModuleInterpolationRequest implements IModuleInterpolationRequest
{
   private AbstractModule module;

   private IConverter converter;

   private IModelCache modelCache;

   public AbstractModule getModule()
   {
      return module;
   }

   public void setModule(AbstractModule module)
   {
      this.module = module;
   }

   public IConverter getConverter()
   {
      return converter;
   }

   public void setConverter(IConverter converter)
   {
      this.converter = converter;
   }

   public IModelCache getModelCache()
   {
      return modelCache;
   }

   public void setModelCache(IModelCache modelCache)
   {
      this.modelCache = modelCache;
   }
}
