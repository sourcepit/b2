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
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class TargetPlatformConfigurationGenerator extends AbstractPomGenerator implements IB2GenerationParticipant {
   private final TargetPlatformAppender targetPlatformAppender;

   private final BasicConverter converter;

   @Inject
   public TargetPlatformConfigurationGenerator(TargetPlatformAppender targetPlatformAppender, BasicConverter converter) {
      this.targetPlatformAppender = targetPlatformAppender;
      this.converter = converter;
   }

   @Override
   public GeneratorType getGeneratorType() {
      return GeneratorType.PROJECT_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes) {
      inputTypes.add(AbstractModule.class);
      inputTypes.add(PluginProject.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates, ModuleDirectory moduleDirectory) {
      if (converter.isSkipInterpolator(properties)) {
         return;
      }

      final AbstractModule module;
      final PluginProject pluginProject;
      if (inputElement instanceof AbstractModule) {
         module = (AbstractModule) inputElement;
         pluginProject = null;
      }
      else {
         pluginProject = (PluginProject) inputElement;
         module = pluginProject.getParent().getParent();
      }

      generate(module, pluginProject, properties);
   }

   private void generate(AbstractModule module, PluginProject pluginProject, PropertiesSource properties) {
      final List<Model> modelHierarchy = new ArrayList<Model>();
      if (pluginProject != null) {
         modelHierarchy.add(readMavenModel(resolvePomFile(pluginProject)));
      }
      modelHierarchy.add(readMavenModel(resolvePomFile(module)));

      if (pluginProject == null) {
         targetPlatformAppender.append(modelHierarchy, module);
      }
      else {
         targetPlatformAppender.append(modelHierarchy, pluginProject);
      }

      final Model model = modelHierarchy.get(0);

      adjustTychoVersion(model);

      final File modelFile = pluginProject == null ? resolvePomFile(module) : resolvePomFile(pluginProject);
      writeMavenModel(modelFile, model);
   }

   private void adjustTychoVersion(final Model model) {
      final Plugin tpcPlugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      if (tpcPlugin != null && tpcPlugin.getVersion() == null) {
         tpcPlugin.setVersion(TYCHO_VERSION_PROPERTY);
      }
   }

}
