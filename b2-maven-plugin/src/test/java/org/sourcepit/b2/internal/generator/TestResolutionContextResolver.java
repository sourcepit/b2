/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.ArrayList;
import java.util.List;

import org.sourcepit.b2.model.interpolation.internal.module.ResolutionContextResolver;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

public class TestResolutionContextResolver implements ResolutionContextResolver
{
   private final List<FeatureProject> requiredFeatures = new ArrayList<FeatureProject>();
   private final List<FeatureProject> requiredTestFeatures = new ArrayList<FeatureProject>();

   public List<FeatureProject> getRequiredFeatures()
   {
      return requiredFeatures;
   }

   public List<FeatureProject> getRequiredTestFeatures()
   {
      return requiredTestFeatures;
   }

   public SetMultimap<AbstractModule, FeatureProject> resolveResolutionContext(AbstractModule module, boolean scopeTest)
   {
      final SetMultimap<AbstractModule, FeatureProject> result = LinkedHashMultimap.create();
      for (FeatureProject featureProject : requiredFeatures)
      {
         result.get(featureProject.getParent().getParent()).add(featureProject);
      }
      if (scopeTest)
      {
         for (FeatureProject featureProject : requiredTestFeatures)
         {
            result.get(featureProject.getParent().getParent()).add(featureProject);
         }
      }
      return result;
   }
}