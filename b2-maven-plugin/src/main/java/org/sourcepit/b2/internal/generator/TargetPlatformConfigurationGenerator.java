/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_GROUP_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_TPC_PLUGIN_ARTIFACT_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_VERSION_PROPERTY;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.modeling.common.Annotatable;

@Named
public class TargetPlatformConfigurationGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   private final TargetPlatformAppender targetPlatformAppender;

   private final BasicConverter converter;

   @Inject
   public TargetPlatformConfigurationGenerator(TargetPlatformAppender targetPlatformAppender, BasicConverter converter)
   {
      this.targetPlatformAppender = targetPlatformAppender;
      this.converter = converter;
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
      if (converter.isSkipInterpolator(properties))
      {
         return;
      }

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
      final List<Model> modelHierarchy = new ArrayList<Model>();
      if (pluginProject != null)
      {
         modelHierarchy.add(readMavenModel(resolvePomFile(pluginProject)));
      }
      modelHierarchy.add(readMavenModel(resolvePomFile(module)));

      if (pluginProject == null)
      {
         targetPlatformAppender.append(modelHierarchy, module);
      }
      else
      {
         targetPlatformAppender.append(modelHierarchy, pluginProject);
      }

      final Model model = modelHierarchy.get(0);

      adjustTychoVersion(model);

      final File modelFile = pluginProject == null ? resolvePomFile(module) : resolvePomFile(pluginProject);
      writeMavenModel(modelFile, model);
   }

   private void adjustTychoVersion(final Model model)
   {
      final Plugin tpcPlugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      if (tpcPlugin != null && tpcPlugin.getVersion() == null)
      {
         tpcPlugin.setVersion(TYCHO_VERSION_PROPERTY);
      }
   }

}
