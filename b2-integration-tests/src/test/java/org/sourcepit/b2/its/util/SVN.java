/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its.util;

import java.io.File;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

public class SVN
{
   private final SVNClientManager clientManager;

   public SVN(SVNClientManager clientManager)
   {
      this.clientManager = clientManager;
   }

   public SVNURL doCreateRepository(File repositoryDirectory) throws SVNException
   {
      return clientManager.getAdminClient().doCreateRepository(repositoryDirectory, null, true, false);
   }


   public SVNCommitInfo doMkDir(SVNURL url, String commitMessage) throws SVNException
   {
      return clientManager.getCommitClient().doMkDir(new SVNURL[] { url }, commitMessage);
   }

   public SVNCommitInfo doImport(File localPath, SVNURL dstURL, String commitMessage) throws SVNException
   {
      return doImport(localPath, dstURL, commitMessage, true);
   }

   public SVNCommitInfo doImport(File localPath, SVNURL dstURL, String commitMessage, boolean recursive)
      throws SVNException
   {
      return clientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, null, false, false,
         SVNDepth.fromRecurse(recursive));
   }

   public long doCheckout(SVNURL url, File destPath) throws SVNException
   {
      return doCheckout(url, SVNRevision.HEAD, destPath, true);
   }

   public long doCheckout(SVNURL url, SVNRevision revision, File destPath, boolean recursive) throws SVNException
   {
      SVNUpdateClient updateClient = clientManager.getUpdateClient();
      /*
       * sets externals not to be ignored during the checkout
       */
      updateClient.setIgnoreExternals(false);
      /*
       * returns the number of the revision at which the working copy is
       */
      return updateClient.doCheckout(url, destPath, revision, revision, SVNDepth.fromRecurse(recursive), false);
   }

   public SVNCommitInfo doCommit(File wcPath, String commitMessage) throws SVNException
   {
      return doCommit(wcPath, false, commitMessage);
   }

   public SVNCommitInfo doCommit(File wcPath, boolean keepLocks, String commitMessage) throws SVNException
   {
      return clientManager.getCommitClient().doCommit(new File[] { wcPath }, keepLocks, commitMessage, null, null,
         false, false, SVNDepth.INFINITY);
   }
}
