/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.common.internal.utils.PathUtils;
import org.sourcepit.beef.b2.generator.GeneratorType;
import org.sourcepit.beef.b2.generator.IB2GenerationParticipant;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.common.Annotateable;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.CompositeModule;
import org.sourcepit.beef.b2.model.module.ProductsFacet;
import org.sourcepit.beef.b2.model.module.ProjectFacet;

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
   protected void generate(Annotateable inputElement, boolean skipFacets, IConverter converter, ITemplates templates)
   {
      if (skipFacets && inputElement instanceof AbstractFacet)
      {
         return;
      }
      setMavenModules(inputElement, skipFacets);
   }

   private File getPomFile(Annotateable annotateable)
   {
      final File pomFile = new File(annotateable.getAnnotationEntry(SOURCE_MAVEN, KEY_POM_FILE));
      if (!pomFile.exists())
      {
         throw new IllegalStateException("Pom file was not generated for " + annotateable);
      }
      return pomFile;
   }

   private void setMavenModules(Annotateable inputElement, boolean skipFacets)
   {
      final File mavenParentFile = getPomFile(inputElement);
      final Model mavenParent = readMavenModel(mavenParentFile);
      for (Annotateable moduleElement : getModules(inputElement, skipFacets))
      {
         final File mavenModuleFile = getPomFile(moduleElement);
         final Model mavenModule = readMavenModel(mavenModuleFile);
         setMavenModule(mavenParentFile, mavenParent, mavenModuleFile);
         if (!(moduleElement instanceof AbstractModule))
         {
            setMavenParent(mavenParentFile, mavenParent, mavenModuleFile, mavenModule);
         }
      }
   }

   private void setMavenParent(File mavenParentFile, Model mavenParent, File mavenModuleFile, Model mavenModule)
   {
      final String moduleToParentPath = PathUtils.getRelativePath(mavenParentFile, mavenModuleFile, "/");
      final Parent parent = new Parent();
      parent.setGroupId(mavenParent.getGroupId());
      parent.setArtifactId(mavenParent.getArtifactId());
      parent.setVersion(mavenParent.getVersion());
      parent.setRelativePath(moduleToParentPath);
      mavenModule.setParent(parent);

      writeMavenModel(mavenModuleFile, mavenModule);
   }

   private void setMavenModule(File mavenParentFile, Model mavenParent, File mavenModuleFile)
   {
      final String parentToModulePath = PathUtils.getRelativePath(mavenModuleFile, mavenParentFile, "/");
      mavenParent.getModules().add(parentToModulePath);
      writeMavenModel(mavenParentFile, mavenParent);
   }

   private List<? extends Annotateable> getModules(Annotateable annotateable, boolean skipFacets)
   {
      if (annotateable instanceof ProjectFacet)
      {
         return ((ProjectFacet<?>) annotateable).getProjects();
      }
      if (annotateable instanceof ProductsFacet)
      {
         return ((ProductsFacet) annotateable).getProductDefinitions();
      }
      if (annotateable instanceof AbstractModule)
      {
         final AbstractModule module = (AbstractModule) annotateable;

         final List<Annotateable> modules = new ArrayList<Annotateable>();
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
      throw new IllegalStateException();
   }
}
