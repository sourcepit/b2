/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;


import java.util.List;

import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;


public interface SitesConverter extends BasicConverter
{
   List<String> getAssemblyCategories(PropertiesSource moduleProperties, String assemblyName);

   PathMatcher getAssemblySiteFeatureMatcher(PropertiesSource moduleProperties, String assemblyName);

   PathMatcher getAssemblyCategoryFeatureMatcher(PropertiesSource moduleProperties, String moduleId,
      String assemblyName, String category);

   String getSiteId(PropertiesSource moduleProperties, String moduleId, String classifier);

}
