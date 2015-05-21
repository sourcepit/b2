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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.apache.maven.model.Model;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class PomHierarchyGenerator extends AbstractPomGenerator implements IB2GenerationParticipant {
   @Override
   public GeneratorType getGeneratorType() {
      return GeneratorType.MODULE_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes) {
      inputTypes.add(AbstractFacet.class);
      inputTypes.add(AbstractModule.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates, ModuleDirectory moduleDirectory) {
      if (skipFacets && inputElement instanceof AbstractFacet) {
         return;
      }
      setMavenModules(inputElement, skipFacets);
   }

   private void setMavenModules(Annotatable inputElement, boolean skipFacets) {
      final File mavenParentFile = resolvePomFile(inputElement);
      final Model mavenParent = readMavenModel(mavenParentFile);
      for (Annotatable moduleElement : getModules(inputElement, skipFacets)) {
         final File mavenModuleFile = resolvePomFile(moduleElement);
         final Model mavenModule = readMavenModel(mavenModuleFile);
         setMavenModule(mavenParentFile, mavenParent, mavenModuleFile);
         if (!(moduleElement instanceof AbstractModule)) {
            setMavenParent(mavenParentFile, mavenParent, mavenModuleFile, mavenModule);
         }
      }
   }

   private List<? extends Annotatable> getModules(Annotatable annotateable, boolean skipFacets) {
      if (annotateable instanceof ProjectFacet) {
         return ((ProjectFacet<?>) annotateable).getProjects();
      }
      if (annotateable instanceof AbstractModule) {
         final AbstractModule module = (AbstractModule) annotateable;

         final List<Annotatable> modules = new ArrayList<Annotatable>();
         if (module instanceof CompositeModule) {
            modules.addAll(((CompositeModule) module).getModules());
         }

         final EList<AbstractFacet> facets = module.getFacets();
         if (skipFacets) {
            for (AbstractFacet facet : facets) {
               modules.addAll(getModules(facet, skipFacets));
            }
         }
         else {
            modules.addAll(facets);
         }

         return modules;
      }
      return Collections.<Annotatable> emptyList();
   }
}
