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

package org.sourcepit.b2.examples;

import java.io.File;
import java.io.IOException;

import junit.framework.AssertionFailedError;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.OS;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.ExternalProcess;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.path.PathUtils;

public abstract class AbstractB2ExamplesIT {
   private final static boolean DEBUG = false;

   private final Environment env = newEnvironment();

   @Rule
   public ExternalProcess process = new ExternalProcess();

   @Rule
   public Workspace ws = new Workspace(getBaseDir(), false);

   @Rule
   public TestName name = new TestName();

   protected File getBaseDir() {
      return getEnvironment().getBuildDir();
   }

   protected Environment newEnvironment() {
      return Environment.get("example-it.properties");
   }

   protected Environment getEnvironment() {
      return env;
   }

   protected Workspace getWs() {
      return ws;
   }

   protected CommandLine createCmd() throws AssertionFailedError {
      final CommandLine command;

      final File baseDir = getBaseDir();
      if (OS.isFamilyWindows() || OS.isFamilyWin9x()) {
         final String executable = isDebug(name.getMethodName()) ? "debug.bat" : "run.bat";
         command = process.newCommandLine(new File(baseDir, executable));
      }
      else if (OS.isFamilyUnix() || OS.isFamilyMac()) {
         command = process.newCommandLine("sh", new File(baseDir, "run.sh").getAbsolutePath());
      }
      else {
         throw new AssertionFailedError("Os family");
      }

      final File projectDir = getExampleProjectDir();
      command.addArgument(PathUtils.getRelativePath(projectDir, baseDir, "/"));

      return command;
   }

   protected File getExampleProjectDir() {
      return new File(getBaseDir(), "example-modules/" + getTestProjectName());
   }

   protected String getTestProjectName() {
      return name.getMethodName().substring("test".length()).toLowerCase().replace('_', '-');
   }

   protected boolean isDebug(String testName) {
      return DEBUG;
   }

   protected void execute(String... args) throws ExecuteException, IOException {
      CommandLine command = createCmd();
      if (args != null) {
         command.addArguments(args);
      }
      process.execute(getEnvironment().newEnvs(), getBaseDir(), command);
   }

   protected void execute() throws ExecuteException, IOException {
      execute((String[]) null);
   }
}
