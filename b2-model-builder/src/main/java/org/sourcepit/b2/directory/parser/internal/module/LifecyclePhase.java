/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.util.List;

import org.sourcepit.common.utils.lang.Exceptions;
import org.sourcepit.common.utils.lang.ThrowablePipe;

public abstract class LifecyclePhase<RESULT, INPUT, PARTICIPANT>
{
   private final List<PARTICIPANT> participants;

   private int invoked = -1;

   public LifecyclePhase(List<PARTICIPANT> participants)
   {
      this.participants = participants;
   }

   protected void pre(INPUT input)
   {
      if (invoked != -1)
      {
         throw new IllegalStateException();
      }
      invoked = 0;
      if (participants != null)
      {
         for (PARTICIPANT participant : participants)
         {
            pre(participant, input);
            invoked++;
         }
      }
   }

   public RESULT execute(INPUT input)
   {
      ThrowablePipe errors = null;
      try
      {
         pre(input);
      }
      catch (Throwable e)
      {
         errors = Exceptions.pipe(errors, e);
      }
      RESULT result = null;
      if (errors == null)
      {
         try
         {
            result = doExecute(input);
         }
         catch (Throwable e)
         {
            errors = Exceptions.pipe(errors, e);
         }
      }
      post(input, result, errors);
      return result;
   }

   protected void post(INPUT input, RESULT result, ThrowablePipe errors)
   {
      if (invoked < 0)
      {
         throw new IllegalStateException();
      }
      for (int i = 0; i < invoked; i++)
      {
         try
         {
            post(participants.get(i), input, result, errors);
         }
         catch (Throwable e)
         {
            errors = Exceptions.pipe(errors, e);
         }
      }
      Exceptions.throwPipe(errors);
   }

   protected abstract void pre(PARTICIPANT participant, INPUT input);

   protected abstract RESULT doExecute(INPUT input);

   protected abstract void post(PARTICIPANT participant, INPUT input, RESULT result, ThrowablePipe errors);
}
