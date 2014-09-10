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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.model.io.ModelReader;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.generator.AbstractGenerator;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.path.PathUtils;
import org.sourcepit.common.utils.props.PropertiesSource;

public abstract class AbstractPomGenerator extends AbstractGenerator
{
   public static final String SOURCE_MAVEN = "maven";
   public static final String KEY_POM_FILE = "pomFile";

   @Override
   public final void generate(EObject inputElement, PropertiesSource propertie, ITemplates templates,
      ModuleDirectory moduleDirectory)
   {
      final boolean skipFacets = propertie.getBoolean("b2.pomGenerator.skipFacets", true);
      generate((Annotatable) inputElement, skipFacets, propertie, templates, moduleDirectory);
   }

   protected abstract void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates, ModuleDirectory moduleDirectory);

   protected File getPomFile(Annotatable annotateable)
   {
      final String path = annotateable.getAnnotationData(SOURCE_MAVEN, KEY_POM_FILE);
      if (path != null)
      {
         return new File(path);
      }
      return null;
   }

   protected File resolvePomFile(Annotatable annotateable)
   {
      final File pomFile = new File(annotateable.getAnnotationData(SOURCE_MAVEN, KEY_POM_FILE));
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
