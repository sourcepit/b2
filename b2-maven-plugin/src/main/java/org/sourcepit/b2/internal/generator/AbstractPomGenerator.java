/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.model.io.ModelReader;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.common.internal.utils.PathUtils;
import org.sourcepit.b2.generator.AbstractGenerator;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.common.Annotatable;

public abstract class AbstractPomGenerator extends AbstractGenerator
{
   public static final String SOURCE_MAVEN = "maven";
   public static final String KEY_POM_FILE = "pomFile";

   @Override
   public final void generate(EObject inputElement, IConverter converter, ITemplates templates)
   {
      final boolean skipFacets = converter.getProperties().getBoolean("b2.pomGenerator.skipFacets", true);
      generate((Annotatable) inputElement, skipFacets, converter, templates);
   }

   protected abstract void generate(Annotatable inputElement, boolean skipFacets, IConverter converter,
      ITemplates templates);

   protected File getPomFile(Annotatable annotateable)
   {
      final String path = annotateable.getAnnotationEntry(SOURCE_MAVEN, KEY_POM_FILE);
      if (path != null)
      {
         return new File(path);
      }
      return null;
   }

   protected File resolvePomFile(Annotatable annotateable)
   {
      final File pomFile = new File(annotateable.getAnnotationEntry(SOURCE_MAVEN, KEY_POM_FILE));
      if (!pomFile.exists())
      {
         throw new IllegalStateException("Pom file was not generated for " + annotateable);
      }
      return pomFile;
   }

   protected void setMavenParent(File mavenParentFile, Model mavenParent, File mavenModuleFile, Model mavenModule)
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

   protected void setMavenModule(File mavenParentFile, Model mavenParent, File mavenModuleFile)
   {
      final String parentToModulePath = PathUtils.getRelativePath(mavenModuleFile, mavenParentFile, "/");
      mavenParent.getModules().add(parentToModulePath);
      writeMavenModel(mavenParentFile, mavenParent);
   }

   protected Model readMavenModel(File pomFile)
   {
      final Map<String, String> options = new HashMap<String, String>();
      options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());
      try
      {
         return new DefaultModelReader().read(pomFile, options);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   protected void writeMavenModel(File file, Model model)
   {
      try
      {
         new DefaultModelWriter().write(file, null, model);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }
}
