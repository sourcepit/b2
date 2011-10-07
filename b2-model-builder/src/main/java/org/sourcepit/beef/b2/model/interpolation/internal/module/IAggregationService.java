/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.interpolation.internal.module;

import java.util.List;

import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.FeatureProject;


public interface IAggregationService
{
   List<FeatureProject> resolveProductIncludes(AbstractModule module, String productClassifier, IConverter converter);

   List<FeatureProject> resolveFeatureIncludes(AbstractModule module, String featureClassifier, IConverter converter);

   List<FeatureProject> resolveCategoryIncludes(AbstractModule module, String categoryClassifer, IConverter converter);
}