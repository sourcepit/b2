/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_GROUP_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_TPC_PLUGIN_ARTIFACT_ID;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.maven.model.util.MavenModelUtils;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class TargetPlatformConfigurationGenerator2 extends AbstractPomGenerator implements IB2GenerationParticipant
{
   private final TargetPlatformConfigurationInhertianceAssembler inhertianceAssembler;
   private final TargetPlatformConfigurationAssembler assembler;
   private final TargetPlatformRequirementsCollector requirementsCollector;
   private TargetPlatformRequirementsAppender requirementsAppender;

   @Inject
   public TargetPlatformConfigurationGenerator2(TargetPlatformConfigurationInhertianceAssembler inhertianceAssembler,
      TargetPlatformConfigurationAssembler assembler, TargetPlatformRequirementsCollector requirementsCollector)
   {
      this.inhertianceAssembler = inhertianceAssembler;
      this.assembler = assembler;
      this.requirementsCollector = requirementsCollector;
   }

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(AbstractModule.class);
      inputTypes.add(PluginProject.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates)
   {
      final AbstractModule module;
      final PluginProject pluginProject;
      if (inputElement instanceof AbstractModule)
      {
         module = (AbstractModule) inputElement;
         pluginProject = null;
      }
      else
      {
         pluginProject = (PluginProject) inputElement;
         module = pluginProject.getParent().getParent();
      }

      generate(module, pluginProject, properties);
   }

   private void generate(AbstractModule module, PluginProject pluginProject, PropertiesSource properties)
   {
      final List<Dependency> requirements;
      if (pluginProject == null)
      {
         requirements = requirementsCollector.collectRequirements(module, false);
      }
      else
      {
         requirements = requirementsCollector.collectRequirements(pluginProject);
      }

      final List<Model> hierarchy = new ArrayList<Model>();
      if (pluginProject != null)
      {
         hierarchy.add(readMavenModel(resolvePomFile(pluginProject)));
      }
      hierarchy.add(readMavenModel(resolvePomFile(module)));

      inhertianceAssembler.assembleTPCInheritance(hierarchy);

      final Model model = hierarchy.get(0);

      requirementsAppender.append(model, requirements);

      final Plugin tpcPlugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      if (tpcPlugin != null)
      {
         tpcPlugin.setVersion(TychoConstants.TYCHO_VERSION_PROPERTY);
      }
   }

}
