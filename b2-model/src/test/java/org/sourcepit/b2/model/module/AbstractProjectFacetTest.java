/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import junit.framework.TestCase;

/**
 * @author Bernd
 */
public abstract class AbstractProjectFacetTest<P extends Project, F extends ProjectFacet<P>> extends TestCase
{
   protected abstract F createFacet();

   protected abstract P createProject();

   public void testGetProjectByName() throws Exception
   {
      F facet = createFacet();
      assertNull(facet.getProjectById(null));

      P project = createProject();
      project.setId("p1");
      facet.getProjects().add(project);

      assertEquals(project, facet.getProjectById("p1"));
      assertNull(facet.getProjectById("p2"));

      P project2 = createProject();
      project2.setId("p2");
      facet.getProjects().add(project2);

      assertEquals(project, facet.getProjectById("p1"));
      assertEquals(project2, facet.getProjectById("p2"));
   }
}
