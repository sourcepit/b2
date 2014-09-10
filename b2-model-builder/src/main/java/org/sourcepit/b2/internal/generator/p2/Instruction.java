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

package org.sourcepit.b2.internal.generator.p2;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;

public class Instruction
{
   public static Instruction parse(String header, String body)
   {
      final String phase = extractPhase(header);
      checkArgument(phase != null, "Invalid instruction header '%s'", header);

      final Instruction instruction = new Instruction();
      instruction.setPhase(phase);

      for (String string : body.split(";"))
      {
         final Action action = Action.parse(string.trim());
         instruction.getActions().add(action);
      }

      return instruction;
   }

   private static String extractPhase(String instructionKey)
   {
      if (!instructionKey.startsWith("instructions."))
      {
         return null;
      }
      final String phase = instructionKey.substring("instructions.".length());
      if (phase.indexOf('.') > -1)
      {
         return null;
      }
      return phase;
   }

   private String phase;

   private List<Action> actions = new ArrayList<Action>();

   public String getPhase()
   {
      return phase;
   }

   public void setPhase(String phase)
   {
      this.phase = phase;
   }

   public List<Action> getActions()
   {
      return actions;
   }

   public String getHeader()
   {
      final StringBuilder sb = new StringBuilder();
      appendKey(sb);
      return sb.toString();
   }

   public String getBody()
   {
      final StringBuilder sb = new StringBuilder();
      appendValue(sb);
      return sb.toString();
   }

   @Override
   public String toString()
   {
      final StringBuilder sb = new StringBuilder();
      appendKey(sb);
      sb.append('=');
      appendValue(sb);
      return sb.toString();
   }

   private void appendKey(final StringBuilder sb)
   {
      sb.append("instructions.");
      sb.append(phase);
   }

   private void appendValue(final StringBuilder sb)
   {
      for (Action action : actions)
      {
         sb.append(action.toString());
         sb.append(';');
      }
      if (!actions.isEmpty())
      {
         sb.deleteCharAt(sb.length() - 1);
      }
   }
}
