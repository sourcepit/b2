/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
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
