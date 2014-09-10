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

package org.sourcepit.b2.its.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Scm;
import org.sourcepit.common.utils.lang.Exceptions;
import org.sourcepit.common.utils.path.PathUtils;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SvnSCM implements SCM
{
   private final SVN svn;

   private final SVNURL svnRoot;

   private File rootDir;

   public SvnSCM(File repoDir, File rootDir)
   {
      this.rootDir = rootDir;
      svn = createSvnFacade();
      try
      {
         svnRoot = svn.doCreateRepository(repoDir);
      }
      catch (SVNException e)
      {
         throw Exceptions.pipe(e);
      }
   }

   private static SVN createSvnFacade()
   {
      FSRepositoryFactory.setup();
      SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true),
         (ISVNAuthenticationManager) null);
      return new SVN(clientManager);
   }

   public Scm createMavenScmModel(File projectDir, String version)
   {
      final Scm scm = new Scm();
      scm.setConnection("scm:svn:" + createSvnURL(projectDir, version));
      return scm;
   }

   private String createSvnURL(File projectDir, String version)
   {
      File rootProjectDir = rootDir;

      final StringBuilder sb = new StringBuilder();
      sb.append(svnRoot);
      sb.append("/");

      final boolean isSnapshot = version == null || version.endsWith("-SNAPSHOT");

      if (isSnapshot)
      {
         sb.append("trunk");
      }
      else
      {
         sb.append("tags");
      }

      final boolean isRoot = rootProjectDir == null || projectDir.equals(rootProjectDir);
      if (isRoot)
      {
         if (!isSnapshot)
         {
            sb.append("/");
            sb.append(projectDir.getName());
            sb.append("-");
            sb.append(version);
         }
      }
      else
      {
         if (!isSnapshot)
         {
            sb.append("/");
            sb.append(rootProjectDir.getName());
            sb.append("-");
            sb.append(version);
         }
         sb.append("/");
         sb.append(PathUtils.getRelativePath(projectDir, rootProjectDir, "/"));
      }

      return sb.toString();
   }

   /**
    * {@inheritDoc}
    */
   public void create()
   {
      try
      {
         SVNURL svnTrunk = svnRoot.appendPath("trunk", false);
         svn.doImport(rootDir, svnTrunk, "");
         FileUtils.deleteDirectory(rootDir);
         svn.doCheckout(svnTrunk, rootDir);
      }
      catch (SVNException e)
      {
         throw Exceptions.pipe(e);
      }
      catch (IOException e)
      {
         throw Exceptions.pipe(e);
      }
   }

   /**
    * {@inheritDoc}
    */
   public void switchVersion(String version)
   {
      try
      {
         SVNURL svnURL = SVNURL.parseURIEncoded(createSvnURL(rootDir, version));
         FileUtils.deleteDirectory(rootDir);
         svn.doCheckout(svnURL, rootDir);
      }
      catch (SVNException e)
      {
         throw Exceptions.pipe(e);
      }
      catch (IOException e)
      {
         throw Exceptions.pipe(e);
      }
   }
}
