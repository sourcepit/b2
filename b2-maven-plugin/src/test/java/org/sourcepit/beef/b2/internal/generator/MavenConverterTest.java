/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import junit.framework.TestCase;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;

public class MavenConverterTest extends TestCase
{
   public void testSkipInterpolator() throws Exception
   {
      MavenSession session = mock(MavenSession.class);
      MavenProject project = mock(MavenProject.class);

      Properties properties = new Properties();
      when(project.getProperties()).thenReturn(properties);

      MavenConverter converter = new MavenConverter(session, project);
      assertFalse(converter.isSkipInterpolator());

      properties.setProperty("b2.skipInterpolator", "true");
      converter = new MavenConverter(session, project);
      assertTrue(converter.isSkipInterpolator());
   }

   public void testSkipGenerator() throws Exception
   {
      MavenSession session = mock(MavenSession.class);
      MavenProject project = mock(MavenProject.class);

      Properties properties = new Properties();
      when(project.getProperties()).thenReturn(properties);

      MavenConverter converter = new MavenConverter(session, project);
      assertFalse(converter.isSkipGenerator());

      properties.setProperty("b2.skipGenerator", "true");
      converter = new MavenConverter(session, project);
      assertTrue(converter.isSkipGenerator());
   }
}
