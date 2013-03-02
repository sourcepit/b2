/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class DefaultModuleIdDerivatorTest
{
   @Test
   public void test()
   {
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.groupId", "commons-io");
      properties.put("project.artifactId", "commons-io");

      DefaultModuleIdDerivator moduleIdDerivator = new DefaultModuleIdDerivator();

      String name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("commons.io"));

      properties.put("project.groupId", "org.junit");
      properties.put("project.artifactId", "junit");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.junit"));

      properties.put("project.groupId", "org.osgi");
      properties.put("project.artifactId", "org.osgi.core");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.osgi.core"));

      properties.put("project.groupId", "org.aspectj");
      properties.put("project.artifactId", "aspectjrt");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.aspectj.rt"));

      properties.put("project.groupId", "org.hamcrest");
      properties.put("project.artifactId", "hamcrest-core");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.hamcrest.core"));

      properties.put("project.groupId", "org.hamcrest");
      properties.put("project.artifactId", "hamcrest_core");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.hamcrest.core"));

      properties.put("project.groupId", "org.hamcrest");
      properties.put("project.artifactId", "hamcrest.core");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.hamcrest.core"));

      properties.put("project.groupId", "org.hamcrest");
      properties.put("project.artifactId", "hamcrest._-core");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.hamcrest.core"));

      properties.put("project.groupId", "org.foo");
      properties.put("project.artifactId", "bar");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.foo.bar"));

      properties.put("project.groupId", "org.sourcepit.common");
      properties.put("project.artifactId", "common-manifest");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.sourcepit.common.manifest"));

      properties.put("project.groupId", "org.sourcepit.tools");
      properties.put("project.artifactId", "osgify-core");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.sourcepit.tools.osgify.core"));

      properties.put("project.groupId", "org.sourcepit-tools");
      properties.put("project.artifactId", "osgify_maven-plugin");

      name = moduleIdDerivator.deriveModuleId(null, properties);
      assertThat(name, IsEqual.equalTo("org.sourcepit.tools.osgify.maven.plugin"));
   }
}
