/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import org.codehaus.plexus.interpolation.ValueSource;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.tools.shared.resources.harness.StringInterpolator;


public interface IConverter
{
   // TODO session
   String getModuleVersion();

   String getNameSpace();

   boolean isSkipInterpolator();

   boolean isSkipGenerator();

   // TODO converter
   PropertiesMap getProperties();

   Collection<ValueSource> getValueSources();

   StringInterpolator newInterpolator();

   String interpolate(String value);

   String interpolate(String value, PropertiesMap properties);

   String getFacetName(File facetFolder);

   // TODO converter helper?
   String toClassifierLabel(String classifier);

   String convertFacetNameToFeatureClassifier(String facetName);

   String getSourceClassiferForFeature(String featureClassifer);

   String createSourceFeatureClassifer(String featureClassifer);

   String getSourceClassiferForPlugin(String pluginId);

   String createSourcePluginId(String pluginId);

   Set<String> getFeatureClassifiers(Set<String> pluginFacetNames);

   PathMatcher createIdMatcherForFeature(String layout, String featureClassifer);

   Set<String> getCategoryClassifiers();

   PathMatcher createIdMatcherForCategory(String layout, String categoryClassifer);

   Set<String> getSiteClassifiers();

   Set<String> getCategoryClassifiersForSite(String siteClassifer);

   boolean isPotentialModuleDirectory(File paseDir, File file);
}
