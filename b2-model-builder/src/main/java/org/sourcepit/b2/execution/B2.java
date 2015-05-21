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

import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.cleaner.IFileService;
import org.sourcepit.b2.internal.generator.B2GenerationRequest;
import org.sourcepit.b2.internal.generator.B2Generator;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.module.AbstractModule;

@Named
public class B2 {
   @Inject
   private IFileService fileService;

   @Inject
   private IB2ModelBuilder modelBuilder;

   @Inject
   private B2Generator generator;

   @Inject
   private List<IB2Listener> listeners;

   @Inject
   private BasicConverter converter;

   public AbstractModule generate(B2Request request) {
      final ModuleDirectory moduleDir = request.getModuleDirectory();
      try {
         fileService.clean(moduleDir);
      }
      catch (IOException e) {
         throw pipe(e);
      }

      final AbstractModule module = modelBuilder.build(request);
      if (!converter.isSkipGenerator(request.getModuleProperties())) {
         for (IB2Listener listener : listeners) {
            listener.startGeneration(moduleDir, module);
         }

         final B2GenerationRequest genRequest = new B2GenerationRequest();
         genRequest.setModule(module);
         genRequest.setModuleDirectory(request.getModuleDirectory());
         genRequest.setModuleProperties(request.getModuleProperties());
         genRequest.setTemplates(request.getTemplates());

         generator.generate(genRequest);
      }
      return module;
   }
}
