/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.b2.model.internal.builder;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.module.IModuleParser;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.IB2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.IModelCache;
import org.sourcepit.b2.model.interpolation.module.IModuleInterpolator;
import org.sourcepit.b2.model.interpolation.module.ModuleInterpolationRequest;
import org.sourcepit.b2.model.module.AbstractModule;

/**
 * @author Bernd
 */
@Named
public class B2ModelBuilder implements IB2ModelBuilder
{
   @Inject
   private IModuleParser moduleParser;

   @Inject
   private IModuleInterpolator interpolator;

   /**
    * {@inheritDoc}
    */
   public AbstractModule build(IB2ModelBuildingRequest request)
   {
      checkRequest(request);

      final IModelCache cache = request.getModelCache();
      if (cache != null)
      {
         AbstractModule module = cache.get(request.getModuleDirectory());
         if (module != null)
         {
            return module;
         }
      }

      final AbstractModule module = moduleParser.parse(request);

      if (request.isInterpolate())
      {
         final ModuleInterpolationRequest iRequest = new ModuleInterpolationRequest();
         iRequest.setModule(module);
         iRequest.setConverter(request.getConverter());
         iRequest.setModelCache(request.getModelCache());
         interpolator.interpolate(iRequest);
      }

      return module;
   }

   protected void checkRequest(IB2ModelBuildingRequest request)
   {
      if (request == null)
      {
         throw new IllegalArgumentException("request must not be null.");
      }

      final File moduleDir = request.getModuleDirectory();
      if (moduleDir == null)
      {
         throw new IllegalArgumentException("directory must not be null.");
      }

      final IConverter converter = request.getConverter();
      if (converter == null)
      {
         throw new IllegalArgumentException("converter must not be null.");
      }
   }
}
