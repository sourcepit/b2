/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sourcepit.maven.bootstrap.participation.BootstrapSession;

/**
 * @author Bernd Vogt <bernd.vogt@sourcepit.org>
 */
@Named
@Singleton
public class BootstrapSessionService
{
   private BootstrapSession bootSession;

   public void setBootstrapSession(BootstrapSession bootSession)
   {
      this.bootSession = bootSession;
   }

   public BootstrapSession getBootstrapSession()
   {
      return bootSession;
   }
}
