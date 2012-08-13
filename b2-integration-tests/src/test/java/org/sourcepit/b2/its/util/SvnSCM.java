/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
         SVNURL svnURL = SVNURL.parseURIDecoded(createSvnURL(rootDir, version));
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
