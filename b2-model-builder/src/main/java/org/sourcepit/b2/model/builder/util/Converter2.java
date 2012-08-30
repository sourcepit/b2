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
import org.sourcepit.common.utils.props.PropertiesSource;


public interface Converter2
{
   String getFacetClassifier(PropertiesSource moduleProperties, String facetName);
   
   List<FeatureInclude> getIncludedFeatures(PropertiesSource moduleProperties, String facetName, boolean isSource);

   List<PluginInclude> getIncludedPlugins(PropertiesSource moduleProperties, String facetName, boolean isSource);

   List<RuledReference> getRequiredFeatures(PropertiesSource moduleProperties, String facetName, boolean isSource);

   List<RuledReference> getRequiredPlugins(PropertiesSource moduleProperties, String facetName, boolean isSource);

   String getFeatureId(PropertiesSource moduleProperties, String moduleId, String classifier, boolean isSource);

   String getSourcePluginId(PropertiesSource moduleProperties, String pluginId);

}
