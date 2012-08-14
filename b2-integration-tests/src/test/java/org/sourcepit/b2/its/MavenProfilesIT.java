/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import static org.hamcrest.core.IsEqual.*;
import org.junit.Test;

public class MavenProfilesIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "clean", "verify");
      assertThat(err, is(0));

      Model pomReactor = loadMavenModel(moduleDir);

      List<Profile> profiles = pomReactor.getProfiles();
      assertThat(getProfile(profiles, "reactor-profile"), notNullValue());
      assertThat(getProfile(profiles, "parent-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-profile"), nullValue());

      // assert that properties in profiles are not replaced
      assertThat(getProfile(profiles, "parent-parent-profile").getProperties().getProperty("foo"),
         equalTo("${basedir}"));

      Model pomParentParent = loadMavenModel(new File(moduleDir, "parent-parent"));

      profiles = pomParentParent.getProfiles();
      assertThat(getProfile(profiles, "reactor-profile"), nullValue());
      assertThat(getProfile(profiles, "parent-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-parent-profile"), nullValue());
      assertThat(getProfile(profiles, "module-profile"), nullValue());

      // assert that properties in profiles are not replaced
      assertThat(getProfile(profiles, "parent-parent-profile").getProperties().getProperty("foo"),
         equalTo("${basedir}"));

      Model pomModuleParent = loadMavenModel(new File(moduleDir, "module-parent"));

      profiles = pomModuleParent.getProfiles();
      assertThat(getProfile(profiles, "reactor-profile"), nullValue());
      assertThat(getProfile(profiles, "parent-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-profile"), nullValue());

      // assert that properties in profiles are not replaced
      assertThat(getProfile(profiles, "parent-parent-profile").getProperties().getProperty("foo"),
         equalTo("${basedir}"));

      Model pomModule = loadMavenModel(new File(moduleDir, "module"));

      profiles = pomModule.getProfiles();
      assertThat(getProfile(profiles, "reactor-profile"), nullValue());
      assertThat(getProfile(profiles, "parent-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-profile"), notNullValue());

      // assert that properties in profiles are not replaced
      assertThat(getProfile(profiles, "parent-parent-profile").getProperties().getProperty("foo"),
         equalTo("${basedir}"));
   }

   private Profile getProfile(List<Profile> profiles, String id)
   {
      for (Profile profile : profiles)
      {
         if (equals(profile.getId(), id))
         {
            return profile;
         }
      }
      return null;
   }

   private boolean equals(Object o1, Object o2)
   {
      if (o1 == null)
      {
         return o2 == null;
      }
      return o1.equals(o2);
   }

}
