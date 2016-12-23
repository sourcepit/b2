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

import static org.sourcepit.b2.internal.maven.MavenModulePropertiesFactory.putModuleVersions;
import static org.sourcepit.b2.internal.maven.MavenModulePropertiesFactory.putProjectVersions;

import java.io.File;
import java.util.Collection;

import javax.inject.Named;

import org.apache.maven.model.Model;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ProjectVersionGenerator extends AbstractPomGenerator implements IB2GenerationParticipant {
   @Override
   public GeneratorType getGeneratorType() {
      return GeneratorType.MODULE_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes) {
      inputTypes.add(AbstractModule.class);
      inputTypes.add(Project.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates, ModuleDirectory moduleDirectory) {
      final File pomFile = resolvePomFile(inputElement);
      final Model model = readMavenModel(pomFile);
      if (inputElement instanceof AbstractModule) {
         final String osgiVersion = ((AbstractModule) inputElement).getVersion();
         putModuleVersions(model.getProperties(), osgiVersion);
      }
      else {
         final String osgiVersion = ((Project) inputElement).getVersion();
         putProjectVersions(model.getProperties(), osgiVersion);
      }
      writeMavenModel(pomFile, model);
   }
}
