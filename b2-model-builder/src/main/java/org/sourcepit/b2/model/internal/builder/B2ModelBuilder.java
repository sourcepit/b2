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

package org.sourcepit.b2.model.internal.builder;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.module.IModuleParser;
import org.sourcepit.b2.files.ModuleDirectory;
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
public class B2ModelBuilder implements IB2ModelBuilder {
   @Inject
   private IModuleParser moduleParser;

   @Inject
   private IModuleInterpolator interpolator;

   /**
    * {@inheritDoc}
    */
   public AbstractModule build(IB2ModelBuildingRequest request) {
      checkRequest(request);

      final AbstractModule module = moduleParser.parse(request);

      if (request.isInterpolate()) {
         final ModuleInterpolationRequest iRequest = new ModuleInterpolationRequest();
         iRequest.setModule(module);
         iRequest.setModuleProperties(request.getModuleProperties());
         interpolator.interpolate(iRequest);
      }

      return module;
   }

   protected void checkRequest(IB2ModelBuildingRequest request) {
      if (request == null) {
         throw new IllegalArgumentException("request must not be null.");
      }

      final ModuleDirectory moduleDirectory = request.getModuleDirectory();
      if (moduleDirectory == null) {
         throw new IllegalArgumentException("moduleDirectory must not be null.");
      }

      final File moduleDir = moduleDirectory.getFile();
      if (moduleDir == null) {
         throw new IllegalArgumentException("directory must not be null.");
      }

      final PropertiesSource properties = request.getModuleProperties();
      if (properties == null) {
         throw new IllegalArgumentException("properties must not be null.");
      }
   }
}
