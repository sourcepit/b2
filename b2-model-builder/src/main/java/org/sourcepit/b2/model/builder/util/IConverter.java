/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import org.codehaus.plexus.interpolation.ValueSource;
import org.sourcepit.b2.common.internal.utils.PathMatcher;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
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

   // TODO Parser utils?
   String getModuleId(File moduleDir);

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