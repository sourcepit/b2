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

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.Repository;
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

      Model pomParentParent = loadMavenModel(new File(moduleDir, "parent-parent"));
      List<Profile> profiles = pomParentParent.getProfiles();

      assertParentParentProfile(profiles);
      // assert that values of duplicate profiles ids are merged and not just overridden
      assertThat(getProfile(profiles, "parent-parent-profile").getProperties().getProperty("name"),
         equalTo("parent-parent-profile"));
      // assert that repo of active parent profile is not merged into actual pom
      assertNull(getRepository(pomParentParent.getRepositories(), "parent-parent-repository"));

      assertThat(getProfile(profiles, "reactor-profile"), nullValue());
      assertThat(getProfile(profiles, "parent-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-parent-profile"), nullValue());
      assertThat(getProfile(profiles, "module-profile"), nullValue());

      Model pomReactor = loadMavenModel(moduleDir);
      profiles = pomReactor.getProfiles();

      assertParentParentProfile(profiles);
      // assert that values of duplicate profiles ids are merged and not just overridden
      assertThat(getProfile(profiles, "parent-parent-profile").getProperties().getProperty("name"),
         equalTo("module-parent-profile"));
      // assert that repo of active parent profile is not merged into actual pom
      assertNull(getRepository(pomReactor.getRepositories(), "parent-parent-repository"));

      assertThat(getProfile(profiles, "reactor-profile"), notNullValue());
      assertThat(getProfile(profiles, "parent-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-profile"), nullValue());

      Model pomModuleParent = loadMavenModel(new File(moduleDir, "module-parent"));
      profiles = pomModuleParent.getProfiles();

      assertParentParentProfile(profiles);
      // assert that values of duplicate profiles ids are merged and not just overridden
      assertThat(getProfile(profiles, "parent-parent-profile").getProperties().getProperty("name"),
         equalTo("module-parent-profile"));
      // assert that repo of active parent profile is not merged into actual pom
      assertNull(getRepository(pomModuleParent.getRepositories(), "parent-parent-repository"));

      assertThat(getProfile(profiles, "reactor-profile"), nullValue());
      assertThat(getProfile(profiles, "parent-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-profile"), nullValue());

      Model pomModule = loadMavenModel(new File(moduleDir, "module"));
      profiles = pomModule.getProfiles();

      assertParentParentProfile(profiles);
      // assert that values of duplicate profiles ids are merged and not just overridden
      assertThat(getProfile(profiles, "parent-parent-profile").getProperties().getProperty("name"),
         equalTo("module-profile"));
      // assert that repo of active parent profile is not merged into actual pom
      assertNull(getRepository(pomModule.getRepositories(), "parent-parent-repository"));

      assertThat(getProfile(profiles, "reactor-profile"), nullValue());
      assertThat(getProfile(profiles, "parent-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-parent-profile"), notNullValue());
      assertThat(getProfile(profiles, "module-profile"), notNullValue());
   }

   private static void assertParentParentProfile(List<Profile> profiles)
   {
      Profile parentParentProfile;
      parentParentProfile = getProfile(profiles, "parent-parent-profile");
      assertNotNull(parentParentProfile);
      assertTrue(parentParentProfile.getActivation().isActiveByDefault());
      assertNotNull(getRepository(parentParentProfile.getRepositories(), "parent-parent-repository"));

      // assert that properties in profiles are not replaced
      assertThat(parentParentProfile.getProperties().getProperty("foo"), equalTo("${basedir}"));
   }

   private static Profile getProfile(List<Profile> profiles, String id)
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

   private static Repository getRepository(List<Repository> repositories, String id)
   {
      for (Repository profile : repositories)
      {
         if (equals(profile.getId(), id))
         {
            return profile;
         }
      }
      return null;
   }

   private static boolean equals(Object o1, Object o2)
   {
      if (o1 == null)
      {
         return o2 == null;
      }
      return o1.equals(o2);
   }

}
