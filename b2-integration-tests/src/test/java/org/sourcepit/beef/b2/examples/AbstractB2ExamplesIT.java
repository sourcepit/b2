/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.examples;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.OS;
import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.exec.PumpStreamHandler;

public abstract class AbstractB2ExamplesIT extends TestCase
{
   private final static boolean DEBUG = false;

   protected TearDownProcessDestroyer processDestroyer = new TearDownProcessDestroyer();
   protected Map<String, String> environment;
   protected DefaultExecutor executor;
   protected CommandLine command;
   protected File workingDir;

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();

      final String testModule = getName().substring("test".length()).toLowerCase().replace('_', '-');

      workingDir = findExamplesBaseDir();
      assertTrue(workingDir.exists());
      assertTrue(workingDir.canRead());

      final String executable;
      if (OS.isFamilyWindows() || OS.isFamilyWin9x())
      {
         executable = isDebug(getName()) ? "debug.bat" : "run.bat";
         command = new CommandLine(new File(workingDir, executable));
      }
      else if (OS.isFamilyUnix() || OS.isFamilyMac())
      {
         executable = "sh";
         System.out.println(executable);
         command = new CommandLine(executable);
         command.addArgument(new File(workingDir, "run.sh").getAbsolutePath());
      }
      else
      {
         throw new AssertionFailedError("Os family");
      }
      //
      // if (OS.isFamilyUnix() || OS.isFamilyMac())
      // {
      // command.addArgument("run.sh");
      // }
      command.addArgument("example-modules/" + testModule);

      executor = new DefaultExecutor();
      executor.setWorkingDirectory(workingDir);
      executor.setStreamHandler(new PumpStreamHandler(System.out, System.err));
      executor.setProcessDestroyer(processDestroyer);

      environment = new HashMap<String, String>(System.getenv());
      environment.remove("M2_HOME");
      environment.remove("JAVA_HOME");
      environment.put("JAVA_HOME", System.getProperty("java.home"));
   }

   private static File findExamplesBaseDir()
   {
      final File[] examplesDirs = new File("target").listFiles(new FileFilter()
      {
         public boolean accept(File file)
         {
            return file.isDirectory() && file.getName().startsWith("b2-examples-");
         }
      });

      assertNotNull(examplesDirs);
      assertEquals(1, examplesDirs.length);
      return examplesDirs[0];
   }

   protected boolean isDebug(String testName)
   {
      return DEBUG;
   }

   protected void execute() throws ExecuteException, IOException
   {
      System.out.println(command.toString());
      executor.execute(command, environment);
   }

   @Override
   protected void tearDown() throws Exception
   {
      processDestroyer.tearDown();
      super.tearDown();
   }

   private static class TearDownProcessDestroyer implements ProcessDestroyer
   {
      private final List<Process> processes = new ArrayList<Process>();

      public synchronized boolean add(Process process)
      {
         return processes.add(process);
      }

      public synchronized boolean remove(Process process)
      {
         return processes.remove(process);
      }

      public synchronized int size()
      {
         return processes.size();
      }

      public synchronized void tearDown()
      {
         for (Process process : processes)
         {
            try
            {
               process.destroy();
            }
            catch (Throwable t)
            {
               System.err.println("Unable to terminate process during process shutdown");
            }
         }
      }
   }
}
