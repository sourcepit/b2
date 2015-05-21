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

package org.sourcepit.b2.model.interpolation.module;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.props.PropertiesSource;

/**
 * @author Bernd
 */
public class ModuleInterpolationRequest implements IModuleInterpolationRequest {
   private AbstractModule module;

   private PropertiesSource moduleProperties;

   public AbstractModule getModule() {
      return module;
   }

   public void setModule(AbstractModule module) {
      this.module = module;
   }

   public PropertiesSource getModuleProperties() {
      return moduleProperties;
   }

   public void setModuleProperties(PropertiesSource moduleProperties) {
      this.moduleProperties = moduleProperties;
   }

}
