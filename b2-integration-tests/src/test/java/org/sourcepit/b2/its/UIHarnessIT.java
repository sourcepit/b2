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

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.sourcepit.common.utils.xml.XmlUtils;
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
