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

import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

public interface ProductsConverter extends BasicConverter {
   PathMatcher getResourceMatcherForProduct(PropertiesSource moduleProperties, String productId);

   List<String> getUpdateSitesForProduct(PropertiesSource moduleProperties, String productId);

   VersionMatchRule getDefaultVersionMatchRuleForProduct(PropertiesSource moduleProperties, String productId);

   List<RuledReference> getIncludedFeaturesForProduct(PropertiesSource moduleProperties, String productId,
      VersionMatchRule defaultVersionMatchRule);

   List<RuledReference> getIncludedPluginsForProduct(PropertiesSource moduleProperties, String productId,
      VersionMatchRule defaultVersionMatchRule);

   VersionMatchRule getVersionMatchRuleForProductInclude(PropertiesSource properties, String productId,
      String featureOrPluginId, VersionMatchRule defaultVersionMatchRule);

}
