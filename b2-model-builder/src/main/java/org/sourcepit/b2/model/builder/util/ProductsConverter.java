/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.util.List;

import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

public interface ProductsConverter extends BasicConverter
{
   PathMatcher getResourceMatcherForProduct(PropertiesSource moduleProperties, String productId);
   
   List<StrictReference> getIncludedFeaturesForProduct(PropertiesSource moduleProperties, String productId);

   List<StrictReference> getIncludedPluginsForProduct(PropertiesSource moduleProperties, String productId);

}
