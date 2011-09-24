/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.internal.tests.harness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.sourcepit.beef.b2.common.internal.utils.DescriptorUtils;
import org.sourcepit.beef.b2.common.internal.utils.DescriptorUtils.AbstractDescriptorResolutionStrategy;
import org.sourcepit.beef.b2.common.internal.utils.DescriptorUtils.IDescriptorResolutionStrategy;
import org.sourcepit.beef.b2.common.internal.utils.XmlUtils;
import org.sourcepit.beef.b2.model.session.B2Session;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.SessionModelFactory;
import org.sourcepit.beef.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;
import org.w3c.dom.Document;

import com.google.inject.Binder;

public abstract class AbstractB2SessionWorkspaceTest extends AbstractInjectedWorkspaceTest
{
   @Inject
   protected B2Session b2Session;

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);

      final File moduleDir = getModuleDir();
      assertTrue(moduleDir.canRead());

      final B2Session session = createSession(moduleDir.getAbsoluteFile());
      assertNotNull(session);

      binder.bind(B2Session.class).toInstance(session);
   }

   protected File getModuleDir()
   {
      return workspace.importResources(getModulePath());
   }

   protected abstract String getModulePath();

   protected B2Session createSession(File moduleDir)
   {
      final List<File> descriptors = new ArrayList<File>();
      final List<File> skippedSescriptors = new ArrayList<File>();

      final IDescriptorResolutionStrategy resolver = new AbstractDescriptorResolutionStrategy(moduleDir, null)
      {
         public File getDescriptor(File directory)
         {
            final File descriptor = new File(directory, "module.xml");
            if (descriptor.exists() && descriptor.isFile())
            {
               return descriptor;
            }
            return null;
         }
      };

      DescriptorUtils.findModuleDescriptors(moduleDir, descriptors, skippedSescriptors, resolver);

      final B2Session session = SessionModelFactory.eINSTANCE.createB2Session();
      for (File descriptor : descriptors)
      {
         if (!skippedSescriptors.contains(descriptor))
         {
            final Document moduleXml = XmlUtils.readXml(descriptor);

            final ModuleProject project = SessionModelFactory.eINSTANCE.createModuleProject();
            project.setDirectory(moduleDir);
            project.setGroupId(XmlUtils.queryText(moduleXml, "/project/groupId"));
            project.setArtifactId(XmlUtils.queryText(moduleXml, "/project/artifactId"));
            project.setVersion(XmlUtils.queryText(moduleXml, "/project/version"));

            session.getProjects().add(project);
         }
      }

      if (!session.getProjects().isEmpty())
      {
         session.setCurrentProject(session.getProjects().get(0));
      }

      return session;
   }
}
