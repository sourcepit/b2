/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;

import org.junit.Rule;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.guplex.test.GuplexTest;

public abstract class AbstractTestEnvironmentTest extends GuplexTest
{
   public final Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = newWorkspace();

   @Override
   protected final boolean isUseIndex()
   {
      return true;
   }

   protected Workspace newWorkspace()
   {
      return new Workspace(new File(env.getBuildDir(), "test-ws"), false);
   }
}
