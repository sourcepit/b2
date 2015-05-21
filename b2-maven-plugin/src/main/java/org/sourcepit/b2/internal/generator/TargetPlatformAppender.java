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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.PluginProject;

@Named
public class TargetPlatformAppender {
   private final TargetPlatformInhertianceAssembler inhertianceAssembler;
   private final TargetPlatformRequirementsCollector requirementsCollector;
   private final TargetPlatformRequirementsAppender requirementsAppender;

   @Inject
   public TargetPlatformAppender(TargetPlatformInhertianceAssembler inhertianceAssembler,
      TargetPlatformRequirementsCollector requirementsCollector, TargetPlatformRequirementsAppender requirementsAppender) {
      this.inhertianceAssembler = inhertianceAssembler;
      this.requirementsCollector = requirementsCollector;
      this.requirementsAppender = requirementsAppender;
   }

   public void append(List<Model> modelHierarchy, AbstractModule module) {
      final List<Dependency> requirements = requirementsCollector.collectRequirements(module, false);
      append(modelHierarchy, requirements);
   }

   public void append(List<Model> modelHierarchy, PluginProject pluginProject) {
      final List<Dependency> requirements = requirementsCollector.collectRequirements(pluginProject);
      append(modelHierarchy, requirements);
   }

   private void append(List<Model> modelHierarchy, final List<Dependency> requirements) {
      if (!requirements.isEmpty()) {
         inhertianceAssembler.assembleTPCInheritance(modelHierarchy);
         final Model model = modelHierarchy.get(0);
         requirementsAppender.append(model, requirements);
      }
   }
}
