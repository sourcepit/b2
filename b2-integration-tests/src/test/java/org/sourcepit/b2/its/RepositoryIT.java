/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.junit.Test;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;

public class RepositoryIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void testLocal() throws Exception
   {
      final File modulesDir = getResource(getClass().getSimpleName());

      // build module a
      final File moduleADir = new File(modulesDir, "module-a");
      assertTrue(moduleADir.exists());
      int err = build(moduleADir, "-e", "-B", "install", "-P !p2-repo");
      assertThat(err, is(0));

      // build module b (depends on module a)
      final File moduleBDir = new File(modulesDir, "module-b");
      assertTrue(moduleBDir.exists());
      err = build(moduleBDir, "-e", "-B", "verify", "-P !p2-repo");
      assertThat(err, is(0));

      // Bug #46 assure usage of base version
      B2Session session = loadSession(moduleBDir);
      ModuleProject moduleProject = session.getProjects().get(0);
      ModuleDependency moduleDependency = moduleProject.getDependencies().get(0);
      assertThat(moduleDependency.getVersionRange(), equalTo("1.0.0-SNAPSHOT"));
   }

   @Test
   public void testRemote() throws Exception
   {
      final File modulesDir = getResource(getClass().getSimpleName());

      // build module a
      final File moduleADir = new File(modulesDir, "module-a");
      assertTrue(moduleADir.exists());
      int err = build(moduleADir, "-e", "-B", "deploy", "-P !p2-repo");
      assertThat(err, is(0));

      // remove module a from local repository
      final Model pom = loadMavenModel(moduleADir);
      deleteFromLocalRepo(pom);

      // build module b (depends on module a)
      final File moduleBDir = new File(modulesDir, "module-b");
      assertTrue(moduleBDir.exists());
      err = build(moduleBDir, "-e", "-B", "verify", "-P !p2-repo");
      assertThat(err, is(0));

      // Bug #46 assure usage of base version
      B2Session session = loadSession(moduleBDir);
      ModuleProject moduleProject = session.getProjects().get(0);
      ModuleDependency moduleDependency = moduleProject.getDependencies().get(0);
      assertThat(moduleDependency.getVersionRange(), equalTo("1.0.0-SNAPSHOT"));
   }

   private void deleteFromLocalRepo(final Model pom) throws IOException
   {
      final File localRepo = environment.getPropertyAsFile("it.localRepository");
      assertTrue(localRepo.exists());
      final File localBuild = new File(localRepo, pom.getGroupId().replace('.', '/') + "/" + pom.getArtifactId() + "/"
         + pom.getVersion());
      assertTrue(localBuild.exists());
      FileUtils.forceDelete(localBuild);
      assertFalse(localBuild.exists());
   }

}
