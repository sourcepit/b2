/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.apache.maven.model.Model;
import org.junit.Test;

public class P2RepositoryDependencyIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      final int err = build(moduleDir, "-e", "-B", "clean");
      assertThat(err, is(0));
      
      final Model pom = loadMavenModel(moduleDir);
      assertThat(0, is(pom.getDependencies().size()));
      assertThat(2, is(pom.getRepositories().size()));
   }

}
