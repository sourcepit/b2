/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.junit.Test;

import com.google.common.base.Predicate;

public class MavenDepenenciesUtilsTest
{

   @Test
   public void testRemoveDependencies()
   {
      final Model model = new Model();
      model.getDependencies().add(newDependency("foo", "bar", "1", "module"));
      model.getDependencies().add(newDependency("foo", "bar", "1", "jar"));

      final Profile profile = new Profile();
      profile.getDependencies().add(newDependency("foo", "bar", "2", "module"));
      profile.getDependencies().add(newDependency("foo", "bar", "2", "jar"));

      model.getProfiles().add(profile);

      MavenDepenenciesUtils.removeDependencies(model, new Predicate<Dependency>()
      {
         public boolean apply(Dependency input)
         {
            return "module".equals(input.getType());
         }
      });

      List<Dependency> dependencies = model.getDependencies();
      assertEquals(1, dependencies.size());
      assertEquals("jar", dependencies.get(0).getType());
      
      dependencies = profile.getDependencies();
      assertEquals(1, dependencies.size());
      assertEquals("jar", dependencies.get(0).getType());
   }

   private static Dependency newDependency(String groupId, String artifactId, String version, String type)
   {
      final Dependency dependency = new Dependency();
      dependency.setGroupId(groupId);
      dependency.setArtifactId(artifactId);
      dependency.setVersion(version);
      if (type != null)
      {
         dependency.setType(type);
      }
      return dependency;
   }

}
