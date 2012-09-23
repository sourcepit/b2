/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.List;

import org.sourcepit.common.utils.props.PropertiesSource;


public interface BasicConverter
{
   enum AggregatorMode
   {
      OFF, AGGREGATE, UNWRAP
   }

   AggregatorMode getAggregatorMode(PropertiesSource moduleProperties, String assemblyName);

   List<String> getAssemblyNames(PropertiesSource moduleProperties);

   String getAssemblyClassifier(PropertiesSource moduleProperties, String assemblyName);

   String getFacetClassifier(PropertiesSource moduleProperties, String facetName);

   boolean isSkipInterpolator(PropertiesSource moduleProperties);

   boolean isSkipGenerator(PropertiesSource moduleProperties);

   boolean isPotentialModuleDirectory(PropertiesSource moduleProperties, File baseDir, File file);
   
   String getModuleVersion(PropertiesSource moduleProperties);

   String getNameSpace(PropertiesSource moduleProperties);
}