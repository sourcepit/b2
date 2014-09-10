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

import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;


public interface FeaturesConverter extends BasicConverter
{
   PathMatcher getAggregatorFeatureMatcherForAssembly(PropertiesSource moduleProperties, String assemblyName);

   PathMatcher getFeatureMatcherForAssembly(PropertiesSource moduleProperties, String assemplyName);

   PathMatcher getPluginMatcherForAssembly(PropertiesSource moduleProperties, String assemblyName);

   List<FeatureInclude> getIncludedFeaturesForAssembly(PropertiesSource moduleProperties, String assemblyName);

   List<PluginInclude> getIncludedPluginsForAssembly(PropertiesSource moduleProperties, String assemblyName);

   List<RuledReference> getRequiredFeaturesForAssembly(PropertiesSource moduleProperties, String assemblyName);

   List<RuledReference> getRequiredPluginsForAssembly(PropertiesSource moduleProperties, String assemblyName);

   PathMatcher getPluginMatcherForFacet(PropertiesSource moduleProperties, String facetName);

   List<FeatureInclude> getIncludedFeaturesForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource);

   List<PluginInclude> getIncludedPluginsForFacet(PropertiesSource moduleProperties, String facetName, boolean isSource);

   List<RuledReference> getRequiredFeaturesForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource);

   List<RuledReference> getRequiredPluginsForFacet(PropertiesSource moduleProperties, String facetName, boolean isSource);

   String getFeatureIdForAssembly(PropertiesSource moduleProperties, String assemblyName, String moduleId);

   String getFeatureIdForFacet(PropertiesSource moduleProperties, String facetName, String moduleId, boolean isSource);

   boolean isSkipBrandingPlugins(PropertiesSource moduleProperties);

   String getBrandingPluginIdForAssembly(PropertiesSource moduleProperties, String assemblyName, String moduleId);

   String getBrandingPluginIdForFacet(PropertiesSource moduleProperties, String facetName, String moduleId,
      boolean isSource);

   String getSourcePluginId(PropertiesSource moduleProperties, String pluginId);

}