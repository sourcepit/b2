/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;

@Named
public class B2SessionRunner
{
   private static abstract class LifecyclePhases<RESULT, PARTICIPANT>
      extends
         LifecyclePhase<RESULT, List<File>, PARTICIPANT>
   {
      private final B2RequestFactory requestFactory;
      private int index;

      public LifecyclePhases(int currentIdx, List<PARTICIPANT> participants, B2RequestFactory requestFactory)
      {
         super(participants);
         this.index = currentIdx;
         this.requestFactory = requestFactory;
      }

      @Override
      protected void pre(List<File> projectDirs)
      {
         if (index < 0)
         {
            throw new IllegalStateException();
         }
         super.pre(projectDirs);
      }

      @Override
      protected void pre(PARTICIPANT participant, List<File> projectDirs)
      {
         if (index == 0)
         {
            prePhases(participant, projectDirs);
         }
      }

      protected abstract void prePhases(PARTICIPANT participant, List<File> projectDirs);

      @Override
      protected RESULT doExecute(List<File> projectDirs)
      {
         return doExecutePhases(requestFactory, projectDirs);
      }

      protected abstract RESULT doExecutePhases(final B2RequestFactory requestFactory, List<File> projectDirs);

      @Override
      protected void post(List<File> projectDirs, RESULT result, ThrowablePipe errors)
      {
         try
         {
            super.post(projectDirs, result, errors);
         }
         finally
         {
            index++;
         }
      }

      @Override
      protected void post(PARTICIPANT participant, List<File> projectDirs, RESULT result, ThrowablePipe errors)
      {
         if (index == projectDirs.size() - 1)
         {
            postPhases(participant, projectDirs, errors);
         }
      }

      protected abstract void postPhases(PARTICIPANT participant, List<File> projectDirs, ThrowablePipe errors);
   }

   @Inject
   private B2 b2;

   @Inject
   private List<B2SessionLifecycleParticipant> lifecycleParticipants;

   public boolean prepareNext(final List<File> projectDirs, final int currentIdx, final B2RequestFactory requestFactory)
   {
      final File projectDir = projectDirs.get(currentIdx);

      new LifecyclePhases<AbstractModule, B2SessionLifecycleParticipant>(currentIdx, lifecycleParticipants,
         requestFactory)
      {
         @Override
         protected void prePhases(B2SessionLifecycleParticipant participant, List<File> projectDirs)
         {
            participant.prePrepareProjects(projectDirs);
         }

         @Override
         protected AbstractModule doExecutePhases(B2RequestFactory requestFactory, List<File> projectDirs)
         {
            return newPrepareProjectPhase(projectDir).execute(requestFactory.newRequest(projectDirs, currentIdx));
         }

         @Override
         protected void postPhases(B2SessionLifecycleParticipant participant, List<File> projectDirs,
            ThrowablePipe errors)
         {
            participant.postPrepareProjects(projectDirs, errors);
         }
      }.execute(projectDirs);
      return projectDirs.size() > currentIdx + 1;
   }

   public boolean finalizeNext(final List<File> projectDirs, int currentIdx)
   {
      final File projectDir = projectDirs.get(currentIdx);

      new LifecyclePhases<Void, B2SessionLifecycleParticipant>(currentIdx, lifecycleParticipants, null)
      {
         @Override
         protected void prePhases(B2SessionLifecycleParticipant participant, List<File> projectDirs)
         {
            participant.preFinalizeProjects(projectDirs);
         }

         @Override
         protected Void doExecutePhases(B2RequestFactory requestFactory, final List<File> projectDirs)
         {
            new LifecyclePhase<Void, Void, B2SessionLifecycleParticipant>(lifecycleParticipants)
            {
               @Override
               protected void pre(B2SessionLifecycleParticipant participant, Void input)
               {
                  participant.preFinalizeProject(projectDir);
               }

               @Override
               protected Void doExecute(Void input)
               {
                  return null;
               }

               @Override
               protected void post(B2SessionLifecycleParticipant participant, Void input, Void result,
                  ThrowablePipe errors)
               {
                  participant.postFinalizeProject(projectDir, errors);
               }
            }.execute(null);
            return null;
         }

         @Override
         protected void postPhases(B2SessionLifecycleParticipant participant, List<File> projectDirs,
            ThrowablePipe errors)
         {
            participant.postFinalizeProjects(projectDirs, errors);
         }
      }.execute(projectDirs);
      return projectDirs.size() > currentIdx + 1;
   }

   private LifecyclePhase<AbstractModule, B2Request, B2SessionLifecycleParticipant> newPrepareProjectPhase(
      final File projectDir)
   {
      return new LifecyclePhase<AbstractModule, B2Request, B2SessionLifecycleParticipant>(lifecycleParticipants)
      {
         @Override
         protected void pre(B2SessionLifecycleParticipant participant, B2Request request)
         {
            participant.prePrepareProject(projectDir, request);
         }

         @Override
         protected AbstractModule doExecute(B2Request request)
         {
            return b2.generate(request);
         }

         @Override
         protected void post(B2SessionLifecycleParticipant participant, B2Request request, AbstractModule result,
            ThrowablePipe errors)
         {
            participant.postPrepareProject(projectDir, request, result, errors);
         }
      };
   }
}
