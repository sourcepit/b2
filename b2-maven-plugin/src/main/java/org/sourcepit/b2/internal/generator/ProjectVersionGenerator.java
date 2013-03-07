/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import javax.inject.Named;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.module.Project;
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
      inputTypes.add(Project.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates)
   {
      generate((Project) inputElement, skipFacets, properties, templates);
   }

   private void generate(Project project, boolean skipFacets, PropertiesSource properties, ITemplates templates)
   {
      final File pomFile = resolvePomFile(project);

      final Model model = readMavenModel(pomFile);

      final StringWriter out = new StringWriter();
      try
      {
         new DefaultModelWriter().write(out, null, model);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      final Model convertedModel;
      try
      {
         convertedModel = new DefaultModelReader().read(
            new StringReader(out.toString().replace("${project.version}", "${project.mavenVersion}")), null);

         final String osgiVersion = project.getVersion();
         final String tychoVersion = VersionUtils.toTychoVersion(osgiVersion);
         final String mavenVersion = VersionUtils.toMavenVersion(osgiVersion);

         convertedModel.getProperties().setProperty("project.tychoVersion", tychoVersion);
         convertedModel.getProperties().setProperty("project.mavenVersion", mavenVersion);
         convertedModel.getProperties().setProperty("project.osgiVersion", osgiVersion);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
      writeMavenModel(pomFile, convertedModel);
   }

}
