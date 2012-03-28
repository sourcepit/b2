/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.lang.ThrowablePipe;

@Named
public class B2SessionRunner
{
   private static abstract class LifecyclePhases<RESULT, PARTICIPANT>
      extends
         LifecyclePhase<RESULT, B2Session, PARTICIPANT>
   {
      private final B2RequestFactory requestFactory;
      private int index;

      public LifecyclePhases(List<PARTICIPANT> participants, B2RequestFactory requestFactory)
      {
         super(participants);
         this.requestFactory = requestFactory;
      }

      @Override
      protected void pre(B2Session session)
      {
         index = getCurrentProjectIndex(session);
         if (index < 0)
         {
            throw new IllegalStateException();
         }
         super.pre(session);
      }

      @Override
      protected void pre(PARTICIPANT participant, B2Session session)
      {
         if (index == 0)
         {
            prePhases(participant, session);
         }
      }

      protected abstract void prePhases(PARTICIPANT participant, B2Session session);

      @Override
      protected RESULT doExecute(B2Session session)
      {
         return doExecutePhases(requestFactory, session);
      }

      protected abstract RESULT doExecutePhases(final B2RequestFactory requestFactory, B2Session session);

      @Override
      protected void post(B2Session session, RESULT result, ThrowablePipe errors)
      {
         try
         {
            super.post(session, result, errors);
         }
         finally
         {
            index++;
            final EList<ModuleProject> projects = session.getProjects();
            if (index < projects.size())
            {
               session.setCurrentProject(projects.get(index));
            }
            else
            {
               session.setCurrentProject(null);
            }
         }
      }

      @Override
      protected void post(PARTICIPANT participant, B2Session session, RESULT result, ThrowablePipe errors)
      {
         if (index == session.getProjects().size() - 1)
         {
            postPhases(participant, session, errors);
         }
      }

      protected abstract void postPhases(PARTICIPANT participant, B2Session session, ThrowablePipe errors);

      int getCurrentProjectIndex(B2Session session)
      {
         final EList<ModuleProject> projects = session.getProjects();
         if (!projects.isEmpty())
         {
            final ModuleProject current = session.getCurrentProject();
            return current == null ? -1 : projects.indexOf(current);
         }
         return -1;
      }
   }

   @Inject
   private B2 b2;

   @Inject
   private List<B2SessionLifecycleParticipant> lifecycleParticipants;

   public boolean prepareNext(final B2Session session, final B2RequestFactory requestFactory)
   {
      new LifecyclePhases<AbstractModule, B2SessionLifecycleParticipant>(lifecycleParticipants, requestFactory)
      {
         @Override
         protected void prePhases(B2SessionLifecycleParticipant participant, B2Session session)
         {
            participant.prePrepareProjects(session);
         }

         @Override
         protected AbstractModule doExecutePhases(B2RequestFactory requestFactory, B2Session session)
         {
            return newPrepareProjectPhase(session).execute(requestFactory.newRequest(session));
         }

         @Override
         protected void postPhases(B2SessionLifecycleParticipant participant, B2Session session, ThrowablePipe errors)
         {
            participant.postPrepareProjects(session, errors);
         }
      }.execute(session);
      return session.getCurrentProject() != null;
   }

   public boolean finalizeNext(final B2Session session)
   {
      new LifecyclePhases<Void, B2SessionLifecycleParticipant>(lifecycleParticipants, null)
      {
         @Override
         protected void prePhases(B2SessionLifecycleParticipant participant, B2Session session)
         {
            participant.preFinalizeProjects(session);
         }

         @Override
         protected Void doExecutePhases(B2RequestFactory requestFactory, final B2Session session)
         {
            new LifecyclePhase<Void, Void, B2SessionLifecycleParticipant>(lifecycleParticipants)
            {
               @Override
               protected void pre(B2SessionLifecycleParticipant participant, Void input)
               {
                  participant.preFinalizeProject(session, session.getCurrentProject());
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
                  participant.postFinalizeProject(session, session.getCurrentProject(), errors);
               }
            }.execute(null);
            return null;
         }

         @Override
         protected void postPhases(B2SessionLifecycleParticipant participant, B2Session session, ThrowablePipe errors)
         {
            participant.postFinalizeProjects(session, errors);
         }
      }.execute(session);
      return session.getCurrentProject() != null;
   }

   private LifecyclePhase<AbstractModule, B2Request, B2SessionLifecycleParticipant> newPrepareProjectPhase(
      final B2Session session)
   {
      return new LifecyclePhase<AbstractModule, B2Request, B2SessionLifecycleParticipant>(lifecycleParticipants)
      {
         @Override
         protected void pre(B2SessionLifecycleParticipant participant, B2Request request)
         {
            participant.prePrepareProject(session, session.getCurrentProject(), request);
         }

         @Override
         protected AbstractModule doExecute(B2Request request)
         {
            final AbstractModule module = b2.generate(request);
            session.getCurrentProject().setModuleModel(module);
            return module;
         }

         @Override
         protected void post(B2SessionLifecycleParticipant participant, B2Request request, AbstractModule result,
            ThrowablePipe errors)
         {
            participant.postPrepareProject(session, session.getCurrentProject(), request, result, errors);
         }
      };
   }
}
