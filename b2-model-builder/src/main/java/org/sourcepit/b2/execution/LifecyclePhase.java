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

package org.sourcepit.b2.execution;

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
      final ThrowablePipe errors = Exceptions.newThrowablePipe();
      try
      {
         pre(input);
      }
      catch (Throwable e)
      {
         errors.add(e);
      }
      RESULT result = null;
      if (errors.getCause() == null)
      {
         try
         {
            result = doExecute(input);
         }
         catch (Throwable e)
         {
            errors.add(e);
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
            errors.add(e);
         }
      }
      Exceptions.throwPipe(errors);
   }

   protected abstract void pre(PARTICIPANT participant, INPUT input);

   protected abstract RESULT doExecute(INPUT input);

   protected abstract void post(PARTICIPANT participant, INPUT input, RESULT result, ThrowablePipe errors);
}
