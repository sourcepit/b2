/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.examples.structured.module.tests;

import org.sourcepit.b2.examples.structured.module.ExampleUtilsTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllContinuousTests
{
   public static Test suite()
   {
      final TestSuite suite = new TestSuite();
      // $JUnit-BEGIN$

      suite.addTestSuite(SimpleTest.class);
      suite.addTestSuite(ExampleUtilsTest.class);

      // $JUnit-END$
      return suite;
   }
}
