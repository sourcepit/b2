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

package org.sourcepit.b2.internal.generator;

import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

public class ConverterUtils {

   public static PropertiesSource newDefaultTestConverter(PropertiesMap properties) {
      PropertiesMap defaultProperties = B2ModelBuildingRequest.newDefaultProperties();
      if (properties != null) {
         defaultProperties.putAll(properties);
      }
      return defaultProperties;
   }

}
