/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.internal.builder;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.module.IModuleParser;
import org.sourcepit.b2.files.ModuleFiles;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.IB2ModelBuildingRequest;
import org.sourcepit.b2.model.interpolation.module.IModuleInterpolator;
import org.sourcepit.b2.model.interpolation.module.ModuleInterpolationRequest;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.props.PropertiesSource;

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

      final AbstractModule module = moduleParser.parse(request);

      if (request.isInterpolate())
      {
         final ModuleInterpolationRequest iRequest = new ModuleInterpolationRequest();
         iRequest.setModule(module);
         iRequest.setModuleProperties(request.getModuleProperties());
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
      
      final ModuleFiles moduleFiles = request.getModuleFiles();
      if (moduleFiles == null)
      {
         throw new IllegalArgumentException("moduleFiles must not be null.");
      }

      final File moduleDir = moduleFiles.getModuleDir();
      if (moduleDir == null)
      {
         throw new IllegalArgumentException("directory must not be null.");
      }

      final PropertiesSource properties = request.getModuleProperties();
      if (properties == null)
      {
         throw new IllegalArgumentException("properties must not be null.");
      }
   }
}
