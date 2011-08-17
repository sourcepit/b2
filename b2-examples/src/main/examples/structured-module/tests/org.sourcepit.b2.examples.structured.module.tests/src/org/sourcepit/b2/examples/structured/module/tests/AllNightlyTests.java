
package org.sourcepit.b2.examples.structured.module.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllNightlyTests
{
   public static Test suite()
   {
      final TestSuite suite = new TestSuite();
      // $JUnit-BEGIN$

      suite.addTest(AllContinuousTests.suite());

      // $JUnit-END$
      return suite;
   }
}
