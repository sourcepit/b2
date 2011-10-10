/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
