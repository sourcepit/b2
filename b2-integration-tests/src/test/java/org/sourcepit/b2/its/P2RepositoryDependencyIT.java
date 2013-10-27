/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.junit.Test;

public class P2RepositoryDependencyIT extends AbstractB2IT
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
      final int err = build(moduleDir, "-e", "-B", "clean");
      assertThat(err, is(0));

      final Model pom = loadMavenModel(moduleDir);
      assertThat(0, is(pom.getDependencies().size()));
      assertThat(2, is(pom.getRepositories().size()));
   }

   @Test
   public void testDepenencyFromProfile() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());

      {
         final File moduleFile = new File(moduleDir, "module.xml");
         final Model pom = readMavenModel(moduleFile);

         Profile profile = new Profile();
         profile.setId("p2");
         profile.getDependencies().addAll(pom.getDependencies());
         pom.getProfiles().add(profile);
         pom.getDependencies().clear();

         writeMavenModel(moduleFile, pom);
      }

      final int err = build(moduleDir, "-e", "-B", "clean", "-P", "p2");
      assertThat(err, is(0));

      final Model pom = readMavenModel(new File(moduleDir, "pom.xml"));
      assertThat(0, is(pom.getDependencies().size()));
      assertThat(2, is(pom.getRepositories().size()));

      final Map<String, Profile> profiles = new LinkedHashMap<String, Profile>();
      for (Profile profile : pom.getProfiles())
      {
         profiles.put(profile.getId(), profile);
      }

      Profile profile = profiles.get("p2");
      assertThat(0, is(profile.getDependencies().size()));
   }

}
