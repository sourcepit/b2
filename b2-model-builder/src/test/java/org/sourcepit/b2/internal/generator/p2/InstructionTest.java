/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator.p2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.sourcepit.common.utils.props.PropertiesMap;

public class InstructionTest
{

   @Test
   public void testParse()
   {
      Instruction instruction = Instruction
         .parse(
            "instructions.configure",
            "addRepository(type:0,location:http${#58}//download.eclipse.org/releases/juno/,enabled:true); addRepository(type:0,location:http${#58}//download.eclipse.org/eclipse/updates/3.8);");
      assertNotNull(instruction);
      assertEquals("configure", instruction.getPhase());

      List<Action> actions = instruction.getActions();
      assertEquals(2, actions.size());

      Action action = actions.get(0);
      assertEquals("addRepository", action.getName());

      PropertiesMap parameters = action.getParameters();
      assertEquals(3, parameters.size());

      Iterator<String> keys = parameters.keySet().iterator();
      assertEquals("type", keys.next());
      assertEquals("location", keys.next());
      assertEquals("enabled", keys.next());

      assertEquals("0", parameters.get("type"));
      assertEquals("http://download.eclipse.org/releases/juno/", parameters.get("location"));
      assertEquals("true", parameters.get("enabled"));
   }

   @Test
   public void testToSring()
   {
      Instruction instruction = Instruction
         .parse(
            "instructions.configure",
            "addRepository(type:0,location:http${#58}//download.eclipse.org/releases/juno/,enabled:true); addRepository(type:0,location:http${#58}//download.eclipse.org/eclipse/updates/3.8);");

      assertEquals(
         "instructions.configure=addRepository(type:0,location:http${#58}//download.eclipse.org/releases/juno/,enabled:true);addRepository(type:0,location:http${#58}//download.eclipse.org/eclipse/updates/3.8)",
         instruction.toString());
   }
}
