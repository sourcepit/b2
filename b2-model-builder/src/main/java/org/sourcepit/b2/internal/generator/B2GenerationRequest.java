/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.IModelCache;
import org.sourcepit.b2.model.module.AbstractModule;

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
