/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.util.Collection;

import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;


public abstract class AbstractModuleParserTest extends AbstractInjectedWorkspaceTest
{
   @Override
   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected static <F extends AbstractFacet> F findFacetByName(Collection<F> facets, String name)
   {
      for (F f : facets)
      {
         if (name.equals(f.getName()))
         {
            return f;
         }
      }
      return null;
   }
}
