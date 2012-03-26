/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.List;

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;


public interface IAggregationService
{
   List<FeatureProject> resolveProductIncludes(AbstractModule module, String productClassifier, IConverter converter);

   List<FeatureProject> resolveFeatureIncludes(AbstractModule module, String featureClassifier, IConverter converter);

   List<FeatureProject> resolveCategoryIncludes(AbstractModule module, String categoryClassifer, IConverter converter);
}
