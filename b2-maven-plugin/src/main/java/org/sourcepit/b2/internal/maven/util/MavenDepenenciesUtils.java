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

package org.sourcepit.b2.internal.maven.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.ModelBase;
import org.apache.maven.model.Profile;

import com.google.common.base.Predicate;

public final class MavenDepenenciesUtils
{
   private MavenDepenenciesUtils()
   {
      super();
   }

   public static void removeDependencies(ModelBase mavenModel, Predicate<Dependency> predicate)
   {
      final List<Dependency> filteredDependencies = new ArrayList<Dependency>();

      for (Dependency dependency : mavenModel.getDependencies())
      {
         if (!predicate.apply(dependency))
         {
            filteredDependencies.add(dependency);
         }
      }

      mavenModel.setDependencies(filteredDependencies);

      if (mavenModel instanceof Model)
      {
         for (Profile profile : ((Model) mavenModel).getProfiles())
         {
            removeDependencies(profile, predicate);
         }
      }
   }
}
