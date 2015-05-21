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

package org.sourcepit.b2.execution;

import java.io.File;
import java.util.List;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;

public interface B2SessionLifecycleParticipant {
   void prePrepareProjects(List<File> projectDirs);

   void prePrepareProject(File projectDir, B2Request request);

   void postPrepareProject(File projectDir, B2Request request, AbstractModule module, ThrowablePipe errors);

   void postPrepareProjects(List<File> projectDirs, ThrowablePipe errors);

   void preFinalizeProjects(List<File> projectDirs);

   void preFinalizeProject(File projectDir);

   void postFinalizeProject(File projectDir, ThrowablePipe errors);

   void postFinalizeProjects(List<File> projectDirs, ThrowablePipe errors);
}
