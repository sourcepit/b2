/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.layout;

import org.sourcepit.b2.model.module.AbstractModule;

public interface IInterpolationLayout
{
   String DEFAULT_PLUGINSFACET_NAME = "plugins";

   String DEFAULT_FEATURES_FACET_NAME = "features";

   String DEFAULT_SITES_FACET_NAME = "sites";

   String pathOfMetaDataFile(AbstractModule module, String path);

   String pathOfFacetMetaData(AbstractModule module, String facetType, String name);

   String pathOfFeatureProject(AbstractModule module, String classifier);

   String idOfFeatureProject(AbstractModule module, String classifier);

   String pathOfSiteProject(AbstractModule module, String classifier);

   String idOfSiteProject(AbstractModule module, String classifier);
}
