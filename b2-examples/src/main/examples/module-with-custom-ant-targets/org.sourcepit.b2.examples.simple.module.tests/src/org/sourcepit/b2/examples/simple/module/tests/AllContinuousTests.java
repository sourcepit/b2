
package org.sourcepit.b2.examples.simple.module.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllContinuousTests
{
   public static Test suite()
   {
      final TestSuite suite = new TestSuite();
      // $JUnit-BEGIN$

      suite.addTestSuite(SimpleTest.class);

      // $JUnit-END$
      return suite;
   }
}
