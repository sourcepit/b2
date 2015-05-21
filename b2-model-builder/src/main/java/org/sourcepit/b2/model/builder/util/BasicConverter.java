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

package org.sourcepit.b2.model.builder.util;

import java.util.List;

import org.sourcepit.common.utils.props.PropertiesSource;


public interface BasicConverter {
   enum AggregatorMode {
      OFF, AGGREGATE, UNWRAP
   }

   AggregatorMode getAggregatorMode(PropertiesSource moduleProperties, String assemblyName);

   List<String> getAssemblyNames(PropertiesSource moduleProperties);

   String getAssemblyClassifier(PropertiesSource moduleProperties, String assemblyName);

   String getFacetClassifier(PropertiesSource moduleProperties, String facetName);

   boolean isSkipInterpolator(PropertiesSource moduleProperties);

   boolean isSkipGenerator(PropertiesSource moduleProperties);

   String getModuleVersion(PropertiesSource moduleProperties);

   String getNameSpace(PropertiesSource moduleProperties);
}