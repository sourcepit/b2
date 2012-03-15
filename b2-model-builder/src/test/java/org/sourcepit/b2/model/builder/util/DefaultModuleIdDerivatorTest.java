/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;

public class DefaultModuleIdDerivatorTest
{
   @Test
   public void test()
   {
      ModuleProject mavenArtifact = SessionModelFactory.eINSTANCE.createModuleProject();
      
      B2Session session = SessionModelFactory.eINSTANCE.createB2Session();
      session.getProjects().add(mavenArtifact);
      session.setCurrentProject(mavenArtifact);
      
      mavenArtifact.setGroupId("commons-io");
      mavenArtifact.setArtifactId("commons-io");
      
      B2SessionService sessionService = new B2SessionService();
      sessionService.setCurrentSession(session);
      
      DefaultModuleIdDerivator moduleIdDerivator = new DefaultModuleIdDerivator(sessionService);

      String name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("commons.io"));

      mavenArtifact.setGroupId("org.junit");
      mavenArtifact.setArtifactId("junit");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.junit"));

      mavenArtifact.setGroupId("org.osgi");
      mavenArtifact.setArtifactId("org.osgi.core");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.osgi.core"));

      mavenArtifact.setGroupId("org.aspectj");
      mavenArtifact.setArtifactId("aspectjrt");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.aspectj.rt"));

      mavenArtifact.setGroupId("org.hamcrest");
      mavenArtifact.setArtifactId("hamcrest-core");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.hamcrest.core"));

      mavenArtifact.setGroupId("org.hamcrest");
      mavenArtifact.setArtifactId("hamcrest_core");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.hamcrest.core"));

      mavenArtifact.setGroupId("org.hamcrest");
      mavenArtifact.setArtifactId("hamcrest.core");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.hamcrest.core"));

      mavenArtifact.setGroupId("org.hamcrest");
      mavenArtifact.setArtifactId("hamcrest._-core");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.hamcrest.core"));

      mavenArtifact.setGroupId("org.foo");
      mavenArtifact.setArtifactId("bar");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.foo.bar"));

      mavenArtifact.setGroupId("org.sourcepit.common");
      mavenArtifact.setArtifactId("common-manifest");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.sourcepit.common.manifest"));

      mavenArtifact.setGroupId("org.sourcepit.tools");
      mavenArtifact.setArtifactId("osgify-core");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.sourcepit.tools.osgify.core"));

      mavenArtifact.setGroupId("org.sourcepit-tools");
      mavenArtifact.setArtifactId("osgify_maven-plugin");

      name = moduleIdDerivator.deriveModuleId(null);
      assertThat(name, IsEqual.equalTo("org.sourcepit.tools.osgify.maven.plugin"));
   }
}
