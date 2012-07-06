/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sourcepit.b2.maven.core.B2MavenBridge;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.common.utils.adapt.Adapters;

@Component(role = AbstractMavenLifecycleParticipant.class)
public class B2MavenLifecycleParticipant extends AbstractMavenLifecycleParticipant
{
   @Requirement
   private B2MavenBridge b2Bridge;

   @Override
   public void afterProjectsRead(MavenSession mavenSession) throws MavenExecutionException
   {
      super.afterProjectsRead(mavenSession);

      final B2Session b2Session = Adapters.getAdapter(mavenSession, B2Session.class);
      if (b2Session == null)
      {
         return;
      }

      b2Bridge.connect(mavenSession, b2Session);
   }
}
