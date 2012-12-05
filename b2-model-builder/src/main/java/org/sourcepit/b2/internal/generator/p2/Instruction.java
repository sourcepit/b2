/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator.p2;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;

public class Instruction
{
   public static Instruction parse(String instructionKey, String actions)
   {
      final String phase = extractPhase(instructionKey);
      checkArgument(phase != null, "Invalid instruction key '%s'", instructionKey);

      final Instruction instruction = new Instruction();
      instruction.setPhase(phase);

      for (String string : actions.split(";"))
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

   public String toKey()
   {
      final StringBuilder sb = new StringBuilder();
      appendKey(sb);
      return sb.toString();
   }

   public String toValue()
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
