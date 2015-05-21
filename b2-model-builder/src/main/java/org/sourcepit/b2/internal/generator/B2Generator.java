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

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.execution.LifecyclePhase;
import org.sourcepit.b2.files.FileVisitor;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.ModuleWalker;
import org.sourcepit.common.utils.lang.ThrowablePipe;

@Named
public class B2Generator {
   private final List<? extends IB2GenerationParticipant> generators;

   private final List<B2GeneratorLifecycleParticipant> lifecycleParticipants;

   @Inject
   public B2Generator(List<? extends IB2GenerationParticipant> generators,
      List<B2GeneratorLifecycleParticipant> lifecycleParticipants) {
      this.generators = generators;
      this.lifecycleParticipants = lifecycleParticipants;
   }

   public void generate(final IB2GenerationRequest request) {
      newLifecyclePhase().execute(request);
   }

   private LifecyclePhase<Void, IB2GenerationRequest, B2GeneratorLifecycleParticipant> newLifecyclePhase() {
      return new LifecyclePhase<Void, IB2GenerationRequest, B2GeneratorLifecycleParticipant>(lifecycleParticipants) {
         @Override
         protected void pre(B2GeneratorLifecycleParticipant participant, IB2GenerationRequest request) {
            participant.preGenerate(request.getModule());
         }

         @Override
         protected Void doExecute(IB2GenerationRequest request) {
            doGenerate(request);
            return null;
         }

         @Override
         protected void post(B2GeneratorLifecycleParticipant participant, IB2GenerationRequest request, Void result,
            ThrowablePipe errors) {
            participant.postGenerate(request.getModule(), errors);
         }
      };
   }

   private void doGenerate(final IB2GenerationRequest request) {
      final ModuleDirectory moduleDirectory = request.getModuleDirectory();

      final Set<File> filesBeforeGeneration = collectNotDerivedFiles(moduleDirectory);

      final List<IB2GenerationParticipant> copy = new ArrayList<IB2GenerationParticipant>(generators);
      Collections.sort(copy);

      for (final IB2GenerationParticipant generator : copy) {
         final ModuleWalker walker = new ModuleWalker(generator.isReverse(), true) {
            @Override
            protected boolean doVisit(EObject eObject) {
               if (generator.isGeneratorInput(eObject)) {
                  generator.generate(eObject, request.getModuleProperties(), request.getTemplates(), moduleDirectory);
               }
               return true;
            }
         };
         walker.walk(request.getModule());
      }

      markNewFilesAsDerivedByGenerator(moduleDirectory, filesBeforeGeneration);
   }

   private void markNewFilesAsDerivedByGenerator(final ModuleDirectory moduleDirectory,
      final Set<File> filesBeforeGeneration) {
      final Set<File> filesAfterGeneration = collectNotDerivedFiles(moduleDirectory);
      filesAfterGeneration.removeAll(filesBeforeGeneration);

      for (File file : filesAfterGeneration) {
         moduleDirectory.addFlags(file, FLAG_DERIVED);
      }
   }

   private static Set<File> collectNotDerivedFiles(final ModuleDirectory moduleDirectory) {
      final Set<File> files = new HashSet<File>();
      moduleDirectory.accept(new FileVisitor<RuntimeException>() {
         @Override
         public boolean visit(File file, int flags) {
            files.add(file);
            return true;
         }
      }, true, false);
      return files;
   }
}
