/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.scm.svn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.logging.Logger;
import org.sourcepit.b2.execution.IB2Listener;
import org.sourcepit.b2.internal.cleaner.AbstractFileVisitor;
import org.sourcepit.b2.internal.cleaner.IFileService;
import org.sourcepit.b2.model.module.AbstractModule;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNPropertyValue;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNPropertyData;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

@Named
public class SCM implements IB2Listener
{
   @Inject
   private IFileService fileService;

   @Inject
   private Logger logger;

   public void startGeneration(AbstractModule module)
   {
      writeFileDump(module.getDirectory());
   }

   public void doSetScmIgnores(AbstractModule module)
   {
      final List<File> fileDump = readFileDump(module.getDirectory());
      final Collection<File> garbage = new LinkedHashSet<File>();
      final Collection<File> newFiles = new LinkedHashSet<File>();
      fileService.accept(module.getDirectory(), new AbstractFileVisitor()
      {
         @Override
         public boolean visit(File file)
         {
            if (!fileDump.remove(file))
            {
               newFiles.add(file);
            }
            return true;
         }

         @Override
         public void visitGarbage(File file)
         {
            garbage.add(file);
         }
      });

      garbage.addAll(newFiles);

      final Map<File, Collection<String>> dirToIgnoresMap = computePotentialIgnores(garbage);
      if (dirToIgnoresMap.isEmpty())
      {
         return;
      }

      final SVNWCClient client = new SVNWCClient((ISVNAuthenticationManager) null, SVNWCUtil.createDefaultOptions(true));

      for (Entry<File, Collection<String>> entry : dirToIgnoresMap.entrySet())
      {
         final File dir = entry.getKey();
         final Collection<String> potentialIgnores = entry.getValue();

         final List<String> actualIgnores;
         try
         {
            actualIgnores = getSvnIgnores(client, dir);
         }
         catch (SVNException e)
         {
            logger.warn("Faild to get actual ignore entries, error is: " + e.getMessage(), e);
            continue;
         }

         final Set<String> newIgnores = new LinkedHashSet<String>();
         for (String potentialIgnore : potentialIgnores)
         {
            if (!actualIgnores.contains(potentialIgnore))
            {
               newIgnores.add(potentialIgnore);
            }
         }

         if (!newIgnores.isEmpty())
         {
            logger.info("Ignore: " + dir.getPath());
            logger.info("Potential ignore entires: " + potentialIgnores);
            logger.info("Actual ignore entires: " + actualIgnores);
            logger.info("New ignore entires: " + newIgnores);

            final List<String> finalIgnores = new ArrayList<String>(actualIgnores);
            finalIgnores.addAll(newIgnores);
            try
            {
               setSvnIgnores(client, dir, finalIgnores);
               logger.info("Set ignore entires: " + finalIgnores);
            }
            catch (SVNException e)
            {
               logger.warn("Faild to set new ignore entries, error is: " + e.getMessage(), e);
               continue;
            }
         }
      }
   }

   private void writeFileDump(File moduleDir)
   {
      try
      {
         File dumFile = new File(moduleDir, ".b2/file.dump");
         if (!dumFile.exists())
         {
            dumFile.getParentFile().mkdirs();
            dumFile.createNewFile();
         }

         final BufferedWriter writer = new BufferedWriter(new FileWriter(dumFile));
         try
         {
            fileService.accept(moduleDir, new AbstractFileVisitor()
            {
               @Override
               public boolean visit(File file)
               {
                  try
                  {
                     writer.write(file.getAbsolutePath());
                     writer.newLine();
                  }
                  catch (IOException e)
                  {
                     throw new IllegalStateException(e);
                  }
                  return true;
               }
            });
            writer.flush();
         }
         finally
         {
            IOUtils.closeQuietly(writer);
         }
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

   }

   private List<File> readFileDump(File moduleDir)
   {
      final List<File> fileDump = new ArrayList<File>();

      File dumpFile = new File(moduleDir, ".b2/file.dump");
      if (!dumpFile.exists())
      {
         return fileDump;
      }

      BufferedReader reader = null;
      try
      {
         reader = new BufferedReader(new FileReader(dumpFile));

         String path = reader.readLine();
         while (path != null)
         {
            fileDump.add(new File(path));
            path = reader.readLine();
         }
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
      finally
      {
         IOUtils.closeQuietly(reader);
      }
      return fileDump;
   }

   private Map<File, Collection<String>> computePotentialIgnores(final Collection<File> garbage)
   {
      final Map<File, Collection<String>> dirToIgnoresMap = new LinkedHashMap<File, Collection<String>>();

      for (File file : garbage)
      {
         final File dir = file.getParentFile();

         Collection<String> ignores = dirToIgnoresMap.get(dir);
         if (ignores == null)
         {
            ignores = new LinkedHashSet<String>();
            dirToIgnoresMap.put(dir, ignores);
         }
         ignores.add(file.getName());
      }
      return dirToIgnoresMap;
   }

   private static void setSvnIgnores(SVNWCClient client, File file, Collection<String> ignores) throws SVNException
   {
      final String propString;
      if (ignores == null || ignores.isEmpty())
      {
         propString = null;
      }
      else
      {
         final StringBuilder sb = new StringBuilder();
         for (String ignore : ignores)
         {
            sb.append(ignore);
            sb.append('\n');
            sb.append('\n');
         }
         sb.deleteCharAt(sb.length() - 1);

         propString = sb.toString();
      }

      final SVNPropertyValue propValue = SVNPropertyValue.create(propString);

      client.doSetProperty(file, "svn:ignore", propValue, true, SVNDepth.EMPTY, null, null);
   }

   private static List<String> getSvnIgnores(SVNWCClient client, File file) throws SVNException
   {
      final List<String> ignores = new ArrayList<String>();

      final SVNPropertyData svnProperty = client.doGetProperty(file, "svn:ignore", SVNRevision.WORKING,
         SVNRevision.WORKING);
      if (svnProperty == null)
      {
         return ignores;
      }

      final SVNPropertyValue value = svnProperty.getValue();
      if (value == null)
      {
         return ignores;
      }

      final String rawIgnores = value.getString();
      if (rawIgnores == null)
      {
         return ignores;
      }
      try
      {
         final BufferedReader rader = new BufferedReader(new StringReader(rawIgnores));

         String line = rader.readLine();
         while (line != null)
         {
            final String ignore = line.trim();
            if (ignore.length() > 0)
            {
               ignores.add(ignore);
            }
            line = rader.readLine();
         }
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
      return ignores;
   }
}
