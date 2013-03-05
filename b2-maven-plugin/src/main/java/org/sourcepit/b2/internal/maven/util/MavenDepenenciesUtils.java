/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
