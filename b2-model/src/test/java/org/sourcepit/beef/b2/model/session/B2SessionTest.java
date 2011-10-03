/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.session;

import junit.framework.TestCase;

public class B2SessionTest extends TestCase
{
   public void testGetProject() throws Exception
   {
      B2Session session = SessionModelFactory.eINSTANCE.createB2Session();
      assertNull(session.getProject("foo", "bar", "1"));

      ModuleProject project1 = SessionModelFactory.eINSTANCE.createModuleProject();
      project1.setGroupId("foo");
      project1.setArtifactId("bar");
      project1.setVersion("1");
      session.getProjects().add(project1);

      assertEquals(project1, session.getProject("foo", "bar", "1"));

      ModuleProject project2 = SessionModelFactory.eINSTANCE.createModuleProject();
      project2.setGroupId("murks");
      project2.setArtifactId("lulu");
      project2.setVersion("1");
      session.getProjects().add(project2);

      assertNull(session.getProject("foo", "bar", "123"));
      assertEquals(project1, session.getProject("foo", "bar", "1"));
      assertEquals(project2, session.getProject("murks", "lulu", "1"));
   }
}
