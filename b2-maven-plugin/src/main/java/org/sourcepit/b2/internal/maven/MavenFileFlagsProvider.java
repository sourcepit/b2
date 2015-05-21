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

package org.sourcepit.b2.internal.maven;

import static java.lang.Integer.valueOf;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_HIDDEN;
import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Named;

import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelParseException;
import org.apache.maven.model.io.ModelReader;
import org.sourcepit.b2.files.AbstractFileFlagsProvider;
import org.sourcepit.b2.files.FileFlagsProvider;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

@Named
public class MavenFileFlagsProvider extends AbstractFileFlagsProvider implements FileFlagsProvider {
   @Override
   public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties) {
      final File basePomFile = new File(moduleDir, "pom.xml");
      if (basePomFile.exists()) {
         final Map<File, Model> fileToModelMap = new HashMap<File, Model>();
         final Multimap<File, File> pomToModuleDirectoryMap = HashMultimap.create();
         collectMavenModels(basePomFile, fileToModelMap, pomToModuleDirectoryMap);

         final Map<File, Integer> result = new HashMap<File, Integer>(fileToModelMap.size() * 2);
         for (Entry<File, Model> entry : fileToModelMap.entrySet()) {
            final File pomFile = entry.getKey();
            result.put(pomFile, valueOf(FLAG_DERIVED | FLAG_HIDDEN));
            // TODO determine target properly
            result.put(new File(pomFile.getParentFile(), "target"), valueOf(FLAG_FORBIDDEN | FLAG_HIDDEN));
         }
         return result;
      }
      return null;
   }

   static void collectMavenModels(final File pomFile, final Map<File, Model> fileToModelMap,
      final Multimap<File, File> pomToModuleDirectoryMap) {
      try {
         final Map<String, String> options = new HashMap<String, String>();
         options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());
         final ModelReader modelReader = new DefaultModelReader();
         collectMavenModels(modelReader, options, pomFile, fileToModelMap, pomToModuleDirectoryMap);
      }
      catch (IOException e) {
         throw pipe(e);
      }
   }

   private static void collectMavenModels(ModelReader modelReader, Map<String, String> options, File pomFile,
      Map<File, Model> fileToModelMap, Multimap<File, File> pomToModuleDirectoryMap) throws IOException,
      ModelParseException {
      final Model model = modelReader.read(pomFile, options);
      fileToModelMap.put(pomFile, model);
      for (String module : getModules(model)) {
         File moduleFile = new File(pomFile.getParentFile(), module);
         if (moduleFile.isDirectory()) {
            moduleFile = new File(moduleFile, "pom.xml");
         }
         if (moduleFile.exists()) {
            pomToModuleDirectoryMap.put(pomFile, moduleFile);
            collectMavenModels(modelReader, options, moduleFile, fileToModelMap, pomToModuleDirectoryMap);
         }
      }
   }

   private static Collection<String> getModules(Model model) {
      final Set<String> modules = new HashSet<String>();
      modules.addAll(model.getModules());
      for (Profile profile : model.getProfiles()) {
         modules.addAll(profile.getModules());
      }
      return modules;
   }
}
