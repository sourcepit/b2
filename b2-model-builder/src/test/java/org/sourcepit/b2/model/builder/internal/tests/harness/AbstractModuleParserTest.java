/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;


public abstract class AbstractModuleParserTest extends AbstractInjectedWorkspaceTest
{
   @Inject
   protected B2SessionService sessionService;

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

   // TODO remove
   protected List<File> initSession(File... moduleDirs)
   {
      final List<File> result = new ArrayList<File>();
      if (moduleDirs != null)
      {
         Collections.addAll(result, moduleDirs);
      }
      sessionService.setCurrentProjectDirs(result);
      sessionService.setCurrentModules(new ArrayList<AbstractModule>());
      return result;
   }
}
