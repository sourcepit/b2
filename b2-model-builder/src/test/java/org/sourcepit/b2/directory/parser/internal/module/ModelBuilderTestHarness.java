/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelWriter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;

public final class ModelBuilderTestHarness
{
   private ModelBuilderTestHarness()
   {
      super();
   }

   public static B2Session createB2Session(File... moduleDirs)
   {
      // TODO we should get rid of these requirements... maybe the rules should only be responsible for parsing the pure
      // structure

      final B2Session session = SessionModelFactory.eINSTANCE.createB2Session();

      if (moduleDirs != null)
      {
         for (File moduleDir : moduleDirs)
         {
            final ModuleProject project = SessionModelFactory.eINSTANCE.createModuleProject();
            project.setDirectory(moduleDir);
            project.setGroupId(moduleDir.getName());
            project.setArtifactId(moduleDir.getName());
            session.getProjects().add(project);
            if (session.getCurrentProject() == null)
            {
               session.setCurrentProject(project);
            }
         }
      }

      return session;
   }

   public static ModuleParsingRequest createParsingRequest(File moduleDir)
   {
      final ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleDirectory(moduleDir);
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      return request;
   }

   public static File mkdir(File parentDir, String name)
   {
      final File dir = new File(parentDir, name);
      dir.mkdirs();
      assertTrue(dir.exists());
      assertTrue(dir.isDirectory());
      return dir;
   }

   public static void initModuleDir(File moduleDir) throws IOException
   {
      new File(moduleDir, "module.xml").createNewFile();
   }
   
   public static BasicModule initModuleDir(final File moduleDir, String groupId, String artifactId, String mavenVersion)
      throws IOException
   {
      Model model = new Model();
      model.setVersion("4.0.0");
      model.setGroupId(groupId);
      model.setArtifactId(artifactId);
      model.setVersion(mavenVersion);

      new DefaultModelWriter().write(new File(moduleDir, "module.xml"), null, model);

      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final BasicModule module = eFactory.createBasicModule();
      module.setId(artifactId);
      module.setVersion(mavenVersion.replaceAll("-SNAPSHOT", ".qualifier"));
      module.setLayoutId("structured");
      module.setDirectory(moduleDir);

      return module;
   }

   public static void initPluginDir(File pluginDir) throws IOException
   {
      final String bundleVersion = "1.0.0.qualifier";
      initPluginDir(pluginDir, bundleVersion);
   }

   public static void initPluginDir(File pluginDir, final String bundleVersion) throws IOException
   {
      final BundleManifest mf = BundleManifestFactory.eINSTANCE.createBundleManifest();
      mf.setBundleSymbolicName(pluginDir.getName());
      mf.setBundleVersion(bundleVersion);

      final URI uri = URI.createFileURI(new File(pluginDir, "META-INF/MANIFEST.MF").getAbsolutePath());
      final Resource resource = new BundleManifestResourceImpl(uri);
      resource.getContents().add(mf);
      resource.save(null);
   }
}
