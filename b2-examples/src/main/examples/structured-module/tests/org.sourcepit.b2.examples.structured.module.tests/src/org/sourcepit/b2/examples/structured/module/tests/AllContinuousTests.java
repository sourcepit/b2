
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
