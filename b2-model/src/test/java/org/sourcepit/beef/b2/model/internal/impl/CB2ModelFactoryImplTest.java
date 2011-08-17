/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.internal.impl;

import junit.framework.TestCase;

import org.sourcepit.beef.b2.internal.model.B2ModelFactory;

public class CB2ModelFactoryImplTest extends TestCase
{
   public void testIsDefault() throws Exception
   {
      // bernd: if this test fails edit B2ModelFactoryImpl.init() in a way that is will instantiate the
      // CB2ModelFactoryImpl factory instead of B2ModelFactoryImpl
      assertTrue(B2ModelFactory.eINSTANCE instanceof CB2ModelFactoryImpl);
   }
}
