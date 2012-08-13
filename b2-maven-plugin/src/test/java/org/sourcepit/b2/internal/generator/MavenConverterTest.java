/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

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
      when(session.getSystemProperties()).thenReturn(System.getProperties());
      when(session.getUserProperties()).thenReturn(new Properties());

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
      when(session.getSystemProperties()).thenReturn(System.getProperties());
      when(session.getUserProperties()).thenReturn(new Properties());

      MavenConverter converter = new MavenConverter(session, project);
      assertFalse(converter.isSkipGenerator());

      properties.setProperty("b2.skipGenerator", "true");
      converter = new MavenConverter(session, project);
      assertTrue(converter.isSkipGenerator());
   }
}
