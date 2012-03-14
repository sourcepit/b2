/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.sourcepit.b2.common.internal.utils.XmlUtils;
import org.w3c.dom.Element;

public class UIHarnessIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "clean", "verify");
      assertThat(err, is(0));

      final File testReport = new File(moduleDir,
         "org.sourcepit.it.ui.tests/target/surefire-reports/TEST-org.sourcepit.it.ui.tests.AllContinuousTests.xml");
      assertTrue(testReport.exists());

      final Element testSummary = (Element) XmlUtils.queryNode(XmlUtils.readXml(testReport), "/testsuite");

      assertThat(testSummary.getAttribute("failures"), IsEqual.equalTo("0"));
      assertThat(testSummary.getAttribute("errors"), IsEqual.equalTo("0"));
      assertThat(testSummary.getAttribute("tests"), IsEqual.equalTo("1"));
   }

}
