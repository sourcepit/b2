/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.model.module;

import junit.framework.TestCase;

/**
 * @author Bernd
 */
public abstract class AbstractProjectFacetTest<P extends Project, F extends ProjectFacet<P>> extends TestCase {
   protected abstract F createFacet();

   protected abstract P createProject();

   public void testGetProjectByName() throws Exception {
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
