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

package org.sourcepit.b2.directory.parser.internal.module;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.execution.LifecyclePhase;
import org.sourcepit.common.utils.lang.PipedError;
import org.sourcepit.common.utils.lang.PipedException;
import org.sourcepit.common.utils.lang.ThrowablePipe;

public class LifecyclePhaseTest {
   @Test
   public void testRunWithoutParticipants() throws Exception {
      assertNull(runner(doReturn(null)));

      final Object object = new Object();
      assertEquals(object, runner(doReturn(object)));

      try {
         runner(doThrow(new IllegalStateException("foo")));
      }
      catch (PipedException pipe) {
         final IllegalStateException e = pipe.adapt(IllegalStateException.class);
         assertThat(e.getMessage(), equalTo("foo"));
         try {
            pipe.adaptAndThrow(IllegalStateException.class);
            fail();
         }
         catch (IllegalStateException ex) {
         }
      }
   }

   @Test
   public void testParticipants() {
      List<String> calls = new ArrayList<String>();
      List<Participant<Return<Object>>> participants = new ArrayList<Participant<Return<Object>>>();
      assertNull(runnerWithParticipants(participants, doReturn(null)));
      assertTrue(calls.isEmpty());

      final Object object = new Object();
      assertEquals(object, runner(doReturn(object)));
      assertTrue(calls.isEmpty());

      participants.add(participate(calls, 1));

      assertNull(runnerWithParticipants(participants, doReturn(null)));
      assertThat(calls.size(), is(2));
      assertThat(calls.get(0), equalTo("participant[1].pre(null)"));
      assertThat(calls.get(1), equalTo("participant[1].post(null,null)"));
      calls.clear();

      participants.add(participate(calls, 2));

      assertNotNull(runnerWithParticipants(participants, doReturn(new Object())));
      assertThat(calls.size(), is(4));
      assertThat(calls.get(0), equalTo("participant[1].pre(not-null)"));
      assertThat(calls.get(1), equalTo("participant[2].pre(not-null)"));
      assertThat(calls.get(2), equalTo("participant[1].post(not-null,null)"));
      assertThat(calls.get(3), equalTo("participant[2].post(not-null,null)"));
      calls.clear();
   }

   @Test
   public void testParticipantsWithThrowables() {
      List<String> calls = new ArrayList<String>();
      List<Participant<Return<Object>>> participants = new ArrayList<Participant<Return<Object>>>();
      try {
         runnerWithParticipants(participants, doThrow(new NullPointerException()));
         fail();
      }
      catch (PipedException e) {
      }
      assertTrue(calls.isEmpty());

      final Object object = new Object();
      assertEquals(object, runnerWithParticipants(participants, doReturn(object)));
      assertTrue(calls.isEmpty());

      participants.add(participate(calls, 1));

      try {
         runnerWithParticipants(participants, doThrow(new NullPointerException()));
         fail();
      }
      catch (PipedException e) {
      }
      assertThat(calls.size(), is(2));
      assertThat(calls.get(0), equalTo("participant[1].pre(not-null)"));
      assertThat(calls.get(1), equalTo("participant[1].post(not-null,NullPointerException)"));
      calls.clear();

      participants.add(participate(calls, 2));

      try {
         runnerWithParticipants(participants, doThrow(new NullPointerException()));
         fail();
      }
      catch (PipedException e) {
      }
      assertThat(calls.size(), is(4));
      assertThat(calls.get(0), equalTo("participant[1].pre(not-null)"));
      assertThat(calls.get(1), equalTo("participant[2].pre(not-null)"));
      assertThat(calls.get(2), equalTo("participant[1].post(not-null,NullPointerException)"));
      assertThat(calls.get(3), equalTo("participant[2].post(not-null,NullPointerException)"));
      calls.clear();
   }

   @Test
   public void testPreParticipantThrowsError() throws Exception {
      List<String> calls = new ArrayList<String>();
      List<Participant<Return<Object>>> participants = new ArrayList<Participant<Return<Object>>>();

      participants.add(participate(calls, 1, new OutOfMemoryError(), null));

      try {
         runnerWithParticipants(participants, doReturn(null));
         fail();
      }
      catch (PipedError e) {
      }

      assertThat(calls.size(), is(0));
      participants.clear();

      participants.add(participate(calls, 1));
      participants.add(participate(calls, 2, new OutOfMemoryError(), null));
      participants.add(participate(calls, 3));

      try {
         runnerWithParticipants(participants, doReturn(null));
         fail();
      }
      catch (PipedError e) {
      }

      assertThat(calls.size(), is(2));
      assertThat(calls.get(0), equalTo("participant[1].pre(null)"));
      assertThat(calls.get(1), equalTo("participant[1].post(null,OutOfMemoryError)"));
      participants.clear();
      calls.clear();
   }

   @Test
   public void testPostParticipantThrowsError() throws Exception {
      List<String> calls = new ArrayList<String>();
      List<Participant<Return<Object>>> participants = new ArrayList<Participant<Return<Object>>>();
      participants.add(participate(calls, 1, null, new OutOfMemoryError()));
      try {
         runnerWithParticipants(participants, doReturn(null));
         fail();
      }
      catch (PipedError e) {
      }
      assertThat(calls.size(), is(2));
      assertThat(calls.get(0), equalTo("participant[1].pre(null)"));
      assertThat(calls.get(1), equalTo("participant[1].post(null,null)"));
      participants.clear();
      calls.clear();

      participants.add(participate(calls, 1, null, new OutOfMemoryError()));
      participants.add(participate(calls, 2));
      try {
         runnerWithParticipants(participants, doReturn(null));
         fail();
      }
      catch (PipedError e) {
      }
      assertThat(calls.size(), is(4));
      assertThat(calls.get(0), equalTo("participant[1].pre(null)"));
      assertThat(calls.get(1), equalTo("participant[2].pre(null)"));
      assertThat(calls.get(2), equalTo("participant[1].post(null,null)"));
      assertThat(calls.get(3), equalTo("participant[2].post(null,OutOfMemoryError)"));
      participants.clear();
      calls.clear();


      participants.add(participate(calls, 1, null, new OutOfMemoryError()));
      participants.add(participate(calls, 2));
      try {
         runnerWithParticipants(participants, doThrow(new NullPointerException()));
         fail();
      }
      catch (PipedException e) {
         assertTrue(e.getCause() instanceof NullPointerException);
         assertTrue(e.getFollowers().get(0) instanceof OutOfMemoryError);
      }
      assertThat(calls.size(), is(4));
      assertThat(calls.get(0), equalTo("participant[1].pre(not-null)"));
      assertThat(calls.get(1), equalTo("participant[2].pre(not-null)"));
      assertThat(calls.get(2), equalTo("participant[1].post(not-null,NullPointerException)"));
      assertThat(calls.get(3), equalTo("participant[2].post(not-null,NullPointerException)"));
      participants.clear();
      calls.clear();
   }

   private static <T> Participant<Return<T>> participate(final List<String> messages, final int idx,
      final Error throwOnPre, final Error throwOnPost) {
      return new Participant<Return<T>>() {
         public void pre(Return<T> input) {
            if (throwOnPre != null) {
               throw throwOnPre;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("participant[");
            sb.append(idx);
            sb.append("].pre(");
            if (input == null) {
               sb.append("null");
            }
            else {
               sb.append("not-null");
            }
            sb.append(")");
            messages.add(sb.toString());
         }

         public void post(Return<T> input, ThrowablePipe errors) {
            StringBuilder sb = new StringBuilder();
            sb.append("participant[");
            sb.append(idx);
            sb.append("].post(");
            if (input == null) {
               sb.append("null");
            }
            else {
               sb.append("not-null");
            }
            sb.append(",");
            if (errors.isEmpty()) {
               sb.append("null");
            }
            else {
               sb.append(errors.getCause().getClass().getSimpleName());
            }
            sb.append(")");
            messages.add(sb.toString());

            if (throwOnPost != null) {
               throw throwOnPost;
            }
         }
      };
   }

   private static <T> Participant<Return<T>> participate(final List<String> messages, final int idx) {
      return participate(messages, idx, null, null);
   }

   private static <T> Return<T> doReturn(T object) {
      if (object == null) {
         return null;
      }
      return new Return<T>(object);
   }

   private static <T, E extends RuntimeException> Return<T> doThrow(final E exception) {
      return new Return<T>(null) {
         @Override
         public T run() {
            throw exception;
         }
      };
   }

   private static <T> T runner(Return<T> result) {
      return LifecyclePhaseTest.<T> runner().execute(result);
   }

   private static <T> Runner<T> runner() {
      return new Runner<T>(null);
   }

   private static <T> T runnerWithParticipants(List<Participant<Return<T>>> participants, Return<T> result) {
      return LifecyclePhaseTest.<T> runnerWithParticipants(participants).execute(result);
   }

   private static <T> Runner<T> runnerWithParticipants(List<Participant<Return<T>>> participants) {
      return new Runner<T>(participants);
   }

   private static class Return<RESULT> {
      protected final RESULT result;

      public Return(RESULT result) {
         this.result = result;
      }

      public RESULT run() {
         return result;
      }
   }

   private interface Participant<INPUT> {
      void pre(INPUT input);

      void post(INPUT input, ThrowablePipe errors);
   }

   private static class Runner<RESULT> extends LifecyclePhase<RESULT, Return<RESULT>, Participant<Return<RESULT>>> {

      public Runner(List<Participant<Return<RESULT>>> participants) {
         super(participants);
      }

      @Override
      protected void pre(Participant<Return<RESULT>> participant, Return<RESULT> input) {
         participant.pre(input);
      }

      @Override
      protected RESULT doExecute(Return<RESULT> input) {
         return input == null ? null : input.run();
      }

      @Override
      protected void post(Participant<Return<RESULT>> participant, Return<RESULT> input, RESULT result,
         ThrowablePipe errors) {
         participant.post(input, errors);
      }
   }
}
