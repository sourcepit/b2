/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.plugin.LegacySupport;
import org.sourcepit.b2.model.interpolation.internal.module.ResolutionContextResolver;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.collections.MultiValueMap;

@Named
public class MavenResolutionContextResolver implements ResolutionContextResolver
{
   @Inject
   private LegacySupport buildContext;

   public void determineForeignResolutionContext(MultiValueMap<AbstractModule, String> moduleToAssemblies,
      AbstractModule module, boolean isTest)
   {

   }

}
