/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.util.Collection;

import javax.inject.Named;

import org.apache.maven.model.Model;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ProjectVersionGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.MODULE_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(AbstractModule.class);
      inputTypes.add(Project.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates)
   {
      final File pomFile = resolvePomFile(inputElement);
      final Model model = readMavenModel(pomFile);
      if (inputElement instanceof AbstractModule)
      {
         final String osgiVersion = ((AbstractModule) inputElement).getVersion();
         appendModuleVersionProperties(model, osgiVersion);
      }
      else
      {
         final String osgiVersion = ((Project) inputElement).getVersion();
         appendProjectVersionProperties(model, osgiVersion);
      }
      writeMavenModel(pomFile, model);
   }

   private static void appendModuleVersionProperties(Model model, String osgiVersion)
   {
      final String tychoVersion = VersionUtils.toTychoVersion(osgiVersion);
      final String mavenVersion = VersionUtils.toMavenVersion(osgiVersion);
      model.getProperties().setProperty("module.version", mavenVersion);
      model.getProperties().setProperty("module.mavenVersion", mavenVersion);
      model.getProperties().setProperty("module.osgiVersion", osgiVersion);
      model.getProperties().setProperty("module.tychoVersion", tychoVersion);
      model.getProperties().setProperty("project.tychoVersion", tychoVersion);
      model.getProperties().setProperty("project.mavenVersion", mavenVersion);
      model.getProperties().setProperty("project.osgiVersion", osgiVersion);
   }

   private static void appendProjectVersionProperties(Model model, String osgiVersion)
   {
      final String tychoVersion = VersionUtils.toTychoVersion(osgiVersion);
      final String mavenVersion = VersionUtils.toMavenVersion(osgiVersion);

      model.getProperties().setProperty("project.tychoVersion", tychoVersion);
      model.getProperties().setProperty("project.mavenVersion", mavenVersion);
      model.getProperties().setProperty("project.osgiVersion", osgiVersion);
   }
}
