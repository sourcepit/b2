/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;
import org.sourcepit.beef.b2.model.module.AbstractModule;

public class B2GenerationRequest implements IB2GenerationRequest
{
   private AbstractModule module;

   private IModelCache modelCache;

   private IConverter converter;

   private ITemplates templates;

   /**
    * {@inheritDoc}
    */
   public AbstractModule getModule()
   {
      return module;
   }

   public void setModule(AbstractModule module)
   {
      this.module = module;
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
   public ITemplates getTemplates()
   {
      return templates;
   }

   public void setTemplates(ITemplates templates)
   {
      this.templates = templates;
   }
}
