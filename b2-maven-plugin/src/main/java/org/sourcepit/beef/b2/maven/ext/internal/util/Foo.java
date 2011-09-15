/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.ext.internal.util;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.component.annotations.Component;

@Component(role = AbstractMavenLifecycleParticipant.class)
public class Foo extends AbstractMavenLifecycleParticipant
{
   @Override
   public void afterProjectsRead(MavenSession session) throws MavenExecutionException
   {
      // TODO: imm0136 Auto-generated method stub
      super.afterProjectsRead(session);
   }

   @Override
   public void afterSessionStart(MavenSession session) throws MavenExecutionException
   {
      // TODO: imm0136 Auto-generated method stub
      super.afterSessionStart(session);
   }
}
