/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.util.List;

import org.sourcepit.b2.directory.parser.internal.project.AbstractProjectParserRule;
import org.sourcepit.b2.directory.parser.internal.project.ProjectParser;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.ReflectionUtils;
import org.sourcepit.b2.model.builder.internal.tests.harness.TestProjectParserRule;

/**
 * @author Bernd
 * 
 */
public class ProjectParserTest extends AbstractModuleParserTest
{
   public void testRuleLookup() throws Exception
   {
      ProjectParser projectParser = lookup(ProjectParser.class);
      assertNotNull(projectParser);

      @SuppressWarnings("rawtypes")
      List<AbstractProjectParserRule> rules = ReflectionUtils.getFieldListValue(projectParser, "rules",
         AbstractProjectParserRule.class);
      assertNotNull(rules);

      TestProjectParserRule testRule = null;
      for (AbstractProjectParserRule<?> rule : rules)
      {
         if (rule instanceof TestProjectParserRule)
         {
            testRule = (TestProjectParserRule) rule;
         }
      }
      assertNotNull(testRule);
      assertEquals(0, testRule.getCalls());
      projectParser.parse(null, null);
      assertEquals(1, testRule.getCalls());
   }
}
