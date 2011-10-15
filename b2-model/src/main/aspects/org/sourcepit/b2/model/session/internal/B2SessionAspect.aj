/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal;

import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.internal.util.B2SessionUtils;

public aspect B2SessionAspect
{
   pointcut getProject(B2Session session, String groupId, String artifactId, String version) : target(session) && args(groupId, artifactId, version) && execution(ModuleProject getProject(String, String, String));

   ModuleProject around(B2Session session, String groupId, String artifactId, String version) : getProject(session, groupId, artifactId, version)
   {
      return B2SessionUtils.getProject(session, groupId, artifactId, version);
   }
}
