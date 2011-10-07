/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.generator;

import org.sourcepit.b2.internal.generator.ProductProjectGenerator;

import junit.framework.TestCase;

public class ProductProjectGeneratorTest extends TestCase
{
   public void testGetClassifier() throws Exception
   {
    assertEquals("bar", ProductProjectGenerator.getClassifier("foo-bar.txt"));
    assertEquals("public", ProductProjectGenerator.getClassifier("foo.txt"));
    assertEquals("public", ProductProjectGenerator.getClassifier("foo-.txt"));
    assertEquals("bar", ProductProjectGenerator.getClassifier("foo-bar"));
    assertEquals("public", ProductProjectGenerator.getClassifier("foo")); 
   }
}
