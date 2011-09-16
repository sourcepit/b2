/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.project;

import java.io.File;

import org.sourcepit.beef.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.beef.b2.model.module.SiteProject;

public class SiteProjectParserRuleTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      final SiteProjectParserRule parserRule = lookupSiteProjectParserRule();
      assertNotNull(parserRule);
   }

   public void testNull() throws Exception
   {
      final SiteProjectParserRule parserRule = lookupSiteProjectParserRule();
      assertNull(parserRule.parse(null, null));
   }

   public void testNonPluginDir() throws Exception
   {
      final File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.exists());

      final SiteProjectParserRule parserRule = lookupSiteProjectParserRule();
      assertNull(parserRule.parse(moduleDir, ConverterUtils.TEST_CONVERTER));
   }

   public void testParseSiteDirectory() throws Exception
   {
      final File siteDir = workspace.importResources("composed-component/simple-layout/example.site", "example.site");
      assertTrue(siteDir.exists());

      final SiteProjectParserRule parserRule = lookupSiteProjectParserRule();
      SiteProject project = parserRule.parse(siteDir, ConverterUtils.TEST_CONVERTER);
      assertNotNull(project);
      assertEquals(siteDir, project.getDirectory());
      assertEquals("example.site", project.getId());
      assertEquals(ConverterUtils.TEST_CONVERTER.getModuleVersion(), project.getVersion());
   }

   private SiteProjectParserRule lookupSiteProjectParserRule() throws Exception
   {
      return (SiteProjectParserRule) lookup(AbstractProjectParserRule.class, "site");
   }
}
