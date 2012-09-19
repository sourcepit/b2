/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.props.PropertiesSource;

public interface IncludesAndRequirementsResolver
{
   void appendIncludesAndRequirements(PropertiesSource moPropertiesSource, AbstractModule module,
      FeatureProject assemblyFeature, String assemblyName);

   void appendIncludesAndRequirements(PropertiesSource moduleProperties, AbstractModule module,
      PluginsFacet pluginsFacet, FeatureProject facetFeatrue);
}
