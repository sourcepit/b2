/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.maven;

import org.apache.maven.project.MavenProject;
import org.eclipse.emf.common.util.URI;

public interface MavenProjectURIResolver
{
   URI resolveProjectUri(MavenProject project, String classifier, String type);
}