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

package org.sourcepit.b2.directory.parser.internal.module;

import static java.lang.Integer.valueOf;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.createModuleDirectory;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.newProperties;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_HIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_MODULE_DIR;
import static org.sourcepit.common.utils.file.FileUtils.deleteFileOrDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.directory.parser.internal.facets.SimpleLayoutFacetsParserRuleTest;
import org.sourcepit.b2.directory.parser.internal.facets.StructuredLayoutFacetsParserRuleTest;
import org.sourcepit.b2.directory.parser.module.IModuleParser;
import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.nls.NlsUtils;

public class ModuleParserTest extends AbstractModuleParserTest {
   @Inject
   private BasicConverter converter;

   public void testBasic() throws Exception {
      IModuleParser parser = lookup();
      assertNotNull(parser);
   }

   public void testNull() throws Exception {
      IModuleParser parser = lookup();
      try {
         parser.parse(null);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());

      try {
         parser.parse(request);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      request.setModuleDirectory(new ModuleDirectory(new File(""), null));
      request.setModuleProperties(null);

      try {
         parser.parse(request);
         fail();
      }
      catch (IllegalArgumentException e) {
      }
   }

   public void testSimpleComponent_WithoutNlsProperties() throws Exception {
      File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.canRead());

      deleteFileOrDirectory(new File(moduleDir, "module.properties"));
      deleteFileOrDirectory(new File(moduleDir, "module_de.properties"));

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
      request.setModuleProperties(newProperties(moduleDir));

      ModuleParser modelParser = lookup();
      BasicModule module = (BasicModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals("org.sourcepit.b2.test.resources.simple.layout", module.getId());
      assertEquals(module.getVersion(), converter.getModuleVersion(request.getModuleProperties()));

      EList<Locale> locales = module.getLocales();
      assertEquals(1, locales.size());
      assertEquals(NlsUtils.DEFAULT_LOCALE, locales.get(0));
   }

   public void testSimpleComponent() throws Exception {
      File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.canRead());

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
      request.setModuleProperties(newProperties(moduleDir));

      ModuleParser modelParser = lookup();
      BasicModule module = (BasicModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals("org.sourcepit.b2.test.resources.simple.layout", module.getId());
      assertEquals(module.getVersion(), converter.getModuleVersion(request.getModuleProperties()));

      EList<Locale> locales = module.getLocales();
      assertFalse(locales.isEmpty());
      assertEquals(new Locale(""), locales.get(0)); // default locale
      assertEquals(new Locale("de"), locales.get(1));

      assertEquals(4, module.getFacets().size());

      PluginsFacet pluginsFacet = module.getFacetByName("plugins");
      assertNotNull(pluginsFacet);

      EList<PluginProject> plugins = pluginsFacet.getProjects();
      assertEquals(2, plugins.size());

      PluginsFacet testsFacet = module.getFacetByName("tests");
      assertNotNull(testsFacet);

      EList<PluginProject> tests = pluginsFacet.getProjects();
      assertEquals(2, tests.size());
   }

   public void testComposedComposite() throws Exception {
      final File moduleDir = workspace.importResources("composed-component");

      final File simpleDir = new File(moduleDir, "simple-layout");
      final File structuredDir = new File(moduleDir, "structured-layout");

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleProperties(newProperties(moduleDir));
      request.setModuleDirectory(createModuleDirectory(moduleDir, simpleDir, structuredDir));

      ModuleParser modelParser = lookup();

      request.setModuleDirectory(new ModuleDirectory(simpleDir, null));
      request.setModuleDirectory(createModuleDirectory(simpleDir));

      final List<AbstractModule> currentModules = new ArrayList<AbstractModule>();
      currentModules.add(modelParser.parse(request));

      request.setModuleDirectory(new ModuleDirectory(structuredDir, null));
      request.setModuleDirectory(createModuleDirectory(structuredDir));

      currentModules.add(modelParser.parse(request));
      for (AbstractModule module : currentModules) {
         request.getModulesCache().put(module.getDirectory(), module);
      }

      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
      request.setModuleDirectory(createModuleDirectory(moduleDir, simpleDir, structuredDir));

      CompositeModule module = (CompositeModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals("org.sourcepit.b2.test.resources.composite.layout", module.getId());
      assertEquals(module.getVersion(), converter.getModuleVersion(request.getModuleProperties()));

      assertEquals(2, module.getModules().size());

      SimpleLayoutFacetsParserRuleTest.assertSimpleLayout((BasicModule) findModuleByDir(module.getModules(), simpleDir));
      StructuredLayoutFacetsParserRuleTest.assertStructuredLayout((BasicModule) findModuleByDir(module.getModules(),
         structuredDir));
   }

   public void testComposedCompositeWithExclude() throws Exception {
      final File moduleDir = workspace.importResources("composed-component");

      final File simpleDir = new File(moduleDir, "simple-layout");
      final File structuredDir = new File(moduleDir, "structured-layout");

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleProperties(newProperties(moduleDir));

      ModuleParser modelParser = lookup();

      request.setModuleDirectory(new ModuleDirectory(simpleDir, null));
      List<AbstractModule> currentModules = new ArrayList<AbstractModule>();
      currentModules.add(modelParser.parse(request));

      request.setModuleDirectory(new ModuleDirectory(structuredDir, null));
      currentModules.add(modelParser.parse(request));

      for (AbstractModule module : currentModules) {
         request.getModulesCache().put(module.getDirectory(), module);
      }

      Map<File, Integer> fileFlags = new HashMap<File, Integer>();
      fileFlags.put(simpleDir, valueOf(FLAG_HIDDEN | FLAG_FORBIDDEN | FLAG_MODULE_DIR));
      request.setModuleDirectory(new ModuleDirectory(moduleDir, fileFlags));

      CompositeModule module = (CompositeModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals("org.sourcepit.b2.test.resources.composite.layout", module.getId());
      assertEquals(module.getVersion(), converter.getModuleVersion(request.getModuleProperties()));

      assertEquals(1, module.getModules().size());

      SimpleLayoutFacetsParserRuleTest.assertSimpleLayout((BasicModule) findModuleByDir(module.getModules(), simpleDir));

      assertNull(findModuleByDir(module.getModules(), structuredDir));
   }

   private AbstractModule findModuleByDir(Collection<AbstractModule> modules, File moduleDir) {
      for (AbstractModule module : modules) {
         if (moduleDir.equals(module.getDirectory())) {
            return module;
         }
      }
      return null;
   }

   private ModuleParser lookup() throws Exception {
      return lookup(ModuleParser.class);
   }
}
