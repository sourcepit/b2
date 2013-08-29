/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class PomHierarchyGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.MODULE_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(AbstractFacet.class);
      inputTypes.add(AbstractModule.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates)
   {
      if (skipFacets && inputElement instanceof AbstractFacet)
      {
         return;
      }
      setMavenModules(inputElement, skipFacets);
   }

   private void setMavenModules(Annotatable inputElement, boolean skipFacets)
   {
      final File mavenParentFile = resolvePomFile(inputElement);
      final Model mavenParent = readMavenModel(mavenParentFile);
      for (Annotatable moduleElement : getModules(inputElement, skipFacets))
      {
         final File mavenModuleFile = resolvePomFile(moduleElement);
         final Model mavenModule = readMavenModel(mavenModuleFile);
         setMavenModule(mavenParentFile, mavenParent, mavenModuleFile);
         if (!(moduleElement instanceof AbstractModule))
         {
            setMavenParent(mavenParentFile, mavenParent, mavenModuleFile, mavenModule);
         }
      }
   }

   private List<? extends Annotatable> getModules(Annotatable annotateable, boolean skipFacets)
   {
      if (annotateable instanceof ProjectFacet)
      {
         return ((ProjectFacet<?>) annotateable).getProjects();
      }
      if (annotateable instanceof AbstractModule)
      {
         final AbstractModule module = (AbstractModule) annotateable;

         final List<Annotatable> modules = new ArrayList<Annotatable>();
         if (module instanceof CompositeModule)
         {
            modules.addAll(((CompositeModule) module).getModules());
         }

         final EList<AbstractFacet> facets = module.getFacets();
         if (skipFacets)
         {
            for (AbstractFacet facet : facets)
            {
               modules.addAll(getModules(facet, skipFacets));
            }
         }
         else
         {
            modules.addAll(facets);
         }

         return modules;
      }
      return Collections.<Annotatable> emptyList();
   }
}
