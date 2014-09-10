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
