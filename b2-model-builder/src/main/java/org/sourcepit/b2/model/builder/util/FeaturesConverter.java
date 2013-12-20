/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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

   /**
    * 'b2' [ '.assemblies' [ '.' assemblyName ] ] '.includedFeatures'
    */
   List<FeatureInclude> getIncludedFeaturesForAssembly(PropertiesSource moduleProperties, String assemblyName);

   /**
    * 'b2' [ '.assemblies' [ '.' assemblyName ] ] '.includedPlugins'
    */
   List<PluginInclude> getIncludedPluginsForAssembly(PropertiesSource moduleProperties, String assemblyName);

   /**
    * 'b2' [ '.assemblies' [ '.' assemblyName ] ] '.requiredFeatures'
    */
   List<RuledReference> getRequiredFeaturesForAssembly(PropertiesSource moduleProperties, String assemblyName);

   /**
    * 'b2' [ '.assemblies' [ '.' assemblyName ] ] '.requiredPlugins'
    */
   List<RuledReference> getRequiredPluginsForAssembly(PropertiesSource moduleProperties, String assemblyName);

   PathMatcher getPluginMatcherForFacet(PropertiesSource moduleProperties, String facetName);

   /**
    * 'b2' [ '.facets' [ '.' facetName ] ] ( '.includedFeatures' | '.includedSourceFeatures' )
    */
   List<FeatureInclude> getIncludedFeaturesForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource);

   /**
    * 'b2' [ '.facets' [ '.' facetName ] ] ( '.includedPlugins' | '.includedSourcePlugins' )
    */
   List<PluginInclude> getIncludedPluginsForFacet(PropertiesSource moduleProperties, String facetName, boolean isSource);

   /**
    * 'b2' [ '.facets' [ '.' facetName ] ] ( '.requiredFeatures' | '.requiredSourceFeatures' )
    */
   List<RuledReference> getRequiredFeaturesForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource);

   /**
    * 'b2' [ '.facets' [ '.' facetName ] ] ( '.requiredPlugins' | '.requiredSourcePlugins' )
    */
   List<RuledReference> getRequiredPluginsForFacet(PropertiesSource moduleProperties, String facetName, boolean isSource);

   String getFeatureIdForAssembly(PropertiesSource moduleProperties, String assemblyName, String moduleId);

   String getFeatureIdForFacet(PropertiesSource moduleProperties, String facetName, String moduleId, boolean isSource);

   boolean isSkipBrandingPlugins(PropertiesSource moduleProperties);

   String getBrandingPluginIdForAssembly(PropertiesSource moduleProperties, String assemblyName, String moduleId);

   String getBrandingPluginIdForFacet(PropertiesSource moduleProperties, String facetName, String moduleId,
      boolean isSource);

   String getSourcePluginId(PropertiesSource moduleProperties, String pluginId);

}