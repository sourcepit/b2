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

package org.sourcepit.b2.model.builder;

import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class B2ModelBuildingRequest extends ModuleParsingRequest implements IB2ModelBuildingRequest {
   private boolean interpolate;

   public boolean isInterpolate() {
      return interpolate;
   }

   public void setInterpolate(boolean interpolateDerivedElements) {
      this.interpolate = interpolateDerivedElements;
   }

   public static PropertiesMap newDefaultProperties() {
      final LinkedPropertiesMap defaultProperties = new LinkedPropertiesMap();
      defaultProperties.load(B2ModelBuildingRequest.class.getClassLoader(), "META-INF/b2/converter.properties");
      return defaultProperties;
   }
}
