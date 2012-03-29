/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import org.sourcepit.common.maven.testing.EmbeddedMavenEnvironmentTest;
import org.sourcepit.common.testing.Environment;

public abstract class AbstractB2SessionWorkspaceTest2 extends EmbeddedMavenEnvironmentTest
{
   @Override
   protected Environment newEnvironment()
   {
      return Environment.get("env-test.properties");
   }
}
