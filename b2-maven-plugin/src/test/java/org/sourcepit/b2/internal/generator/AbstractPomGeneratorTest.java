/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelReader;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness;
import org.sourcepit.b2.files.ModuleFiles;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.IB2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.internal.util.EWalkerImpl;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.inject.Binder;

public abstract class AbstractPomGeneratorTest extends AbstractB2SessionWorkspaceTest
{
   @Inject
   protected IB2ModelBuilder modelBuilder;

   @Inject
   protected PomGenerator pomGenerator;

   @Inject
   protected Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private LegacySupport buildContext;

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();

      MavenSession session = mock(MavenSession.class);
      when(session.getProjects()).thenReturn(new ArrayList<MavenProject>());

      for (File projectDir : getModuleDirs())
      {
         PropertiesMap properties = ModelBuilderTestHarness.newProperties(projectDir);

         MavenProject project = new MavenProject();
         project.setGroupId(properties.get("project.groupId"));
         project.setArtifactId(properties.get("project.artifactId"));
         project.setVersion(properties.get("project.version"));
         project.setFile(new File(projectDir, "module.xml"));

         session.getProjects().add(project);
      }

      List<MavenProject> projects = session.getProjects();
      if (!projects.isEmpty())
      {
         when(session.getCurrentProject()).thenReturn(projects.get(0));
      }

      buildContext.setSession(session);
   }

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);
   }

   protected IInterpolationLayout getLayout(BasicModule module)
   {
      return layoutMap.get(module.getLayoutId());
   }

   @SuppressWarnings("unchecked")
   protected <T extends AbstractModule> T buildModel(File moduleDir) throws Exception
   {
      B2ModelBuildingRequest request = newModelBuildingRequest(moduleDir);
      return (T) buildModel(request);
   }

   @SuppressWarnings("unchecked")
   protected <T extends AbstractModule> T buildModel(IB2ModelBuildingRequest request)
   {
      return (T) modelBuilder.build(request);
   }

   protected static B2ModelBuildingRequest newModelBuildingRequest(File moduleDir)
   {
      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setModuleFiles(new ModuleFiles(moduleDir, null));
      request.setInterpolate(false);
      request.setModuleProperties(ModelBuilderTestHarness.newProperties(moduleDir));
      return request;
   }

   protected void assertIsGeneratorInput(EObject eObject)
   {
      assertTrue(pomGenerator.isGeneratorInput(eObject));
   }

   protected void generatePom(File moduleDir, EObject eObject, PropertiesMap properties)
   {
      pomGenerator.generate(eObject, ConverterUtils.newDefaultTestConverter(properties), new DefaultTemplateCopier(),
         new ModuleFiles(moduleDir, null));
   }

   protected void generateAllPoms(final AbstractModule module, PropertiesMap properties)
   {
      final PropertiesSource source = ConverterUtils.newDefaultTestConverter(properties);
      final DefaultTemplateCopier templateCopier = new DefaultTemplateCopier();
      new EWalkerImpl(pomGenerator.isReverse(), true)
      {
         @Override
         protected boolean visit(EObject eObject)
         {
            if (pomGenerator.isGeneratorInput(eObject))
            {
               pomGenerator.generate(eObject, source, templateCopier, new ModuleFiles(module.getDirectory(), null));
            }
            return true;
         }
      }.walk(module);
   }

   protected static void assertNoPomFiles(File file)
   {
      if (file.isFile())
      {
         assertFalse("pom.xml".equals(file.getName()));
      }
      else
      {
         File[] members = file.listFiles();
         if (members != null)
         {
            for (File member : members)
            {
               assertNoPomFiles(member);
            }
         }
      }
   }

   protected static Model readMavenModel(File pomFile)
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
}
