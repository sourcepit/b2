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

package org.sourcepit.b2.model.interpolation.layout;

import org.sourcepit.b2.model.module.AbstractModule;

public interface IInterpolationLayout {
   String DEFAULT_PLUGINSFACET_NAME = "plugins";

   String DEFAULT_FEATURES_FACET_NAME = "features";

   String DEFAULT_SITES_FACET_NAME = "sites";

   String pathOfMetaDataFile(AbstractModule module, String path);

   String pathOfFacetMetaData(AbstractModule module, String facetName, String name);

   String pathOfFeatureProject(AbstractModule module, String classifier);

   @Deprecated
   String idOfFeatureProject(AbstractModule module, String classifier);

   String pathOfSiteProject(AbstractModule module, String classifier);

   String idOfSiteProject(AbstractModule module, String classifier);
}
