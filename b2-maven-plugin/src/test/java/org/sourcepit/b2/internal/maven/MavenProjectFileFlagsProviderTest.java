/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import static org.apache.commons.io.IOUtils.copy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_HIDDEN;
import static org.sourcepit.common.utils.io.IO.fileOut;
import static org.sourcepit.common.utils.io.IO.write;
import static org.sourcepit.common.utils.path.PathUtils.getRelativePath;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.maven.model.Model;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.io.Write.ToStream;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

public class MavenProjectFileFlagsProviderTest
{
   private final Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = newWorkspace();

   protected Workspace newWorkspace()
   {
      return new Workspace(new File(env.getBuildDir(), "ws"), false);
   }

   @Test
   public void testCollectMavenModels()
   {
      init(ws.getRoot());

      final File basePomFile = new File(ws.getRoot(), "pom.xml");

      final Map<File, Model> fileToModelMap = new HashMap<File, Model>();
      final Multimap<File, File> pomToModuleDirectoryMap = HashMultimap.create();
      MavenFileFlagsProvider.collectMavenModels(basePomFile, fileToModelMap, pomToModuleDirectoryMap);

      assertEquals(6, fileToModelMap.size());
      assertEquals(2, pomToModuleDirectoryMap.asMap().size());

      Collection<File> moduleDirectory = pomToModuleDirectoryMap.get(basePomFile);
      assertEquals(3, moduleDirectory.size());

      moduleDirectory = pomToModuleDirectoryMap.get(new File(ws.getRoot(), "3/foo.xml"));
      assertEquals(2, moduleDirectory.size());
   }

   @Test
   public void testGetFileFlags()
   {
      final File moduleDir = ws.getRoot();
      init(moduleDir);

      final MavenFileFlagsProvider provider = new MavenFileFlagsProvider();
      final PropertiesSource properties = new LinkedPropertiesMap();

      final Map<File, Integer> fileFlags = provider.getAlreadyKnownFileFlags(moduleDir, properties);
      assertEquals(12, fileFlags.size());

      assertTrue(fileFlags.containsKey(new File(moduleDir, "pom.xml")));

      for (Integer flags : fileFlags.values())
      {
         assertEquals(FLAG_DERIVED | FLAG_HIDDEN, flags.intValue());
      }
   }

   private static void init(File baseDir)
   {
      final Multimap<File, File> parentToModules = LinkedHashMultimap.create();

      final File basePomFile = new File(baseDir, "pom.xml");
      parentToModules.put(basePomFile, new File(baseDir, "1"));
      parentToModules.put(basePomFile, new File(baseDir, "2/pom.xml"));
      parentToModules.put(basePomFile, new File(baseDir, "3/foo.xml"));
      parentToModules.put(new File(baseDir, "3/foo.xml"), new File(baseDir, "3/1/bar.xml"));
      parentToModules.put(new File(baseDir, "3/foo.xml"), new File(baseDir, "3/1/1/"));

      final Set<File> all = new HashSet<File>();

      for (Entry<File, Collection<File>> entry : parentToModules.asMap().entrySet())
      {
         final File parent = entry.getKey();
         all.add(parent);

         final Collection<File> modules = entry.getValue();
         all.addAll(modules);

         final StringBuilder sb = new StringBuilder();
         sb.append("<project>\n");
         sb.append("<modules>\n");
         for (File module : modules)
         {
            sb.append("<module>");
            sb.append(getRelativePath(module, parent, "/"));
            sb.append("</module>\n");
         }
         sb.append("</modules>\n");
         sb.append("</project>");
         String content = sb.toString();

         writePom(parent, content);
      }

      for (File file : all)
      {
         if (!file.exists())
         {
            writePom(file, "<project />");
         }
      }
   }

   private static void writePom(File file, String content)
   {
      file = file.getName().endsWith(".xml") ? file : new File(file, "pom.xml");
      write(new ToStream<String>()
      {
         @Override
         public void write(OutputStream output, String content) throws Exception
         {
            copy(new ByteArrayInputStream(content.getBytes("UTF-8")), output);
         }
      }, fileOut(file), content);
   }
}
