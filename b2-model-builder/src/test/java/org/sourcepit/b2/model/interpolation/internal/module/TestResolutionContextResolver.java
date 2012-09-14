/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import javax.inject.Named;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

@Named
public class TestResolutionContextResolver implements ResolutionContextResolver
{
   public SetMultimap<AbstractModule, String> resolveResolutionContext(AbstractModule module,
      FeatureProject resolutionTarget)
   {
      return LinkedHashMultimap.create();
   }

}
