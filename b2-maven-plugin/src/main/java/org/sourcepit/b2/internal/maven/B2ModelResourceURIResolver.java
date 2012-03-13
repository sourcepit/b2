/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;

import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.eclipse.emf.common.util.URI;

public final class B2ModelResourceURIResolver implements MavenProjectURIResolver
{
   private final MavenProjectHelper projectHelper;

   public B2ModelResourceURIResolver(MavenProjectHelper projectHelper)
   {
      this.projectHelper = projectHelper;
   }

   public URI resolveProjectUri(MavenProject project, String classifier, String type)
   {
      if ("module".equals(type))
      {
         final StringBuilder sb = new StringBuilder();
         sb.append("b2");
         if (classifier != null)
         {
            sb.append('-');
            sb.append(classifier);
         }
         sb.append('.');
         sb.append(type);

         final File file = new File(project.getBasedir(), ".b2/" + sb.toString());
         projectHelper.attachArtifact(project, type, classifier, file);
         return URI.createFileURI(file.getAbsolutePath());
      }
      return null;
   }
}