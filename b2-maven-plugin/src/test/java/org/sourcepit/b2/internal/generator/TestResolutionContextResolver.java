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