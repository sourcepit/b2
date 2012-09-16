/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.sourcepit.common.maven.model.MavenArtifact;

import com.google.common.collect.SetMultimap;

public interface ModuleArtifactResolver
{
   SetMultimap<MavenArtifact, String> resolve(MavenSession session, MavenProject project, String scope);
}
