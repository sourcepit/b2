/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
public class TargetPlatformAppender
{
   private final TargetPlatformInhertianceAssembler inhertianceAssembler;
   private final TargetPlatformRequirementsCollector requirementsCollector;
   private final TargetPlatformRequirementsAppender requirementsAppender;

   @Inject
   public TargetPlatformAppender(TargetPlatformInhertianceAssembler inhertianceAssembler,
      TargetPlatformRequirementsCollector requirementsCollector, TargetPlatformRequirementsAppender requirementsAppender)
   {
      this.inhertianceAssembler = inhertianceAssembler;
      this.requirementsCollector = requirementsCollector;
      this.requirementsAppender = requirementsAppender;
   }

   public void append(List<Model> modelHierarchy, AbstractModule module)
   {
      final List<Dependency> requirements = requirementsCollector.collectRequirements(module, false);
      append(modelHierarchy, requirements);
   }

   public void append(List<Model> modelHierarchy, PluginProject pluginProject)
   {
      final List<Dependency> requirements = requirementsCollector.collectRequirements(pluginProject);
      append(modelHierarchy, requirements);
   }

   private void append(List<Model> modelHierarchy, final List<Dependency> requirements)
   {
      if (!requirements.isEmpty())
      {
         inhertianceAssembler.assembleTPCInheritance(modelHierarchy);
         final Model model = modelHierarchy.get(0);
         requirementsAppender.append(model, requirements);
      }
   }
}
