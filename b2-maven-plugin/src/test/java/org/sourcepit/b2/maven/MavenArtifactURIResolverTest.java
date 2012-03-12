/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.component.annotations.Requirement;
import org.eclipse.emf.common.util.URI;
import org.sourcepit.b2.internal.maven.MavenProjectURIResolver;
import org.sourcepit.b2.internal.maven.MavenURIResolver;
import org.sourcepit.b2.model.common.util.ArtifactIdentifier;
import org.sourcepit.b2.model.common.util.ArtifactURIResolver;

/**
 * MavenArtifactURIResolverTest
 * 
 * @author Bernd
 */
public class MavenArtifactURIResolverTest extends AbstractMavenSessionWorkspaceTest
{
   private ArtifactURIResolver resolver;

   @Requirement
   private MavenProjectHelper projectHelper;

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();
      projectHelper = lookup(MavenProjectHelper.class);
      resolver = new MavenURIResolver(mavenSession.getProjects(), mavenSession.getCurrentProject());
   }

   @Override
   protected String setUpModulePath()
   {
      return "composed-component";
   }

   public void testNull() throws Exception
   {
      MavenProject project = mavenSession.getCurrentProject();
      URI uri = resolver.resolve(MavenURIResolver.toArtifactReference(project, "foo", "bar"));
      assertNull(uri);
   }

   public void testResolvePomURI() throws Exception
   {
      MavenProject currentProject = mavenSession.getCurrentProject();

      URI uri = resolver.resolve(MavenURIResolver.toArtifactReference(currentProject, null, "pom"));
      assertNotNull(uri);
      assertEquals(currentProject.getFile(), new File(uri.toFileString()));

      MavenProject otherProject = mavenSession.getProjects().get(2);
      assertNotSame(currentProject, otherProject);

      uri = resolver.resolve(MavenURIResolver.toArtifactReference(otherProject, null, "pom"));
      assertNotNull(uri);
      assertEquals(otherProject.getFile(), new File(uri.toFileString()));
   }

   public void testAttachedArtifact() throws Exception
   {
      MavenProject currentProject = mavenSession.getCurrentProject();

      File attachment = new File(currentProject.getBasedir(), "target/attachedFile.txt");
      attachment.getParentFile().mkdirs();
      attachment.createNewFile();

      projectHelper.attachArtifact(currentProject, "txt", "internal", attachment);

      URI uri = resolver.resolve(MavenURIResolver.toArtifactReference(currentProject, "internal", "txt"));
      assertNotNull(uri);
      assertEquals(attachment, new File(uri.toFileString()));
   }

   public void testProjectURIResolver() throws Exception
   {
      MavenProject project = mavenSession.getCurrentProject();

      ArtifactIdentifier id1 = MavenURIResolver.toArtifactIdentifier(project, "foo", "bar");

      MavenProjectURIResolver projectResolver = mock(MavenProjectURIResolver.class);
      when(projectResolver.resolveProjectUri(project, "foo", "bar")).thenReturn(id1.toUri());

      ((MavenURIResolver) resolver).getProjectURIResolvers().add(projectResolver);

      URI uri = projectResolver.resolveProjectUri(project, "foo", "bar");
      assertNotNull(uri);
      assertEquals(id1.toUri(), uri);

      uri = projectResolver.resolveProjectUri(project, null, "bar");
      assertNull(uri);
   }
}
