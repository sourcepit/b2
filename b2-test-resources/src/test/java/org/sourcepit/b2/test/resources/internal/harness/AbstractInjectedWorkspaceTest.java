/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.test.resources.internal.harness;

import org.eclipse.sisu.launch.InjectedTestCase;
import org.sourcepit.tools.shared.resources.internal.harness.MavenTestWorkspace;

import com.google.inject.Binder;

public abstract class AbstractInjectedWorkspaceTest extends InjectedTestCase {
   protected MavenTestWorkspace workspace = new MavenTestWorkspace(this, false);

   @Override
   public void configure(Binder binder) {
      super.configure(binder);
      // binder.bind(Logger.class).toInstance(new ConsoleLogger());
   }

   @Override
   protected void setUp() throws Exception {
      workspace.startUp();
      super.setUp();
   }

   public MavenTestWorkspace getWorkspace() {
      return workspace;
   }

   @Override
   protected void tearDown() throws Exception {
      super.tearDown();
      workspace.tearDown();
   }
}
