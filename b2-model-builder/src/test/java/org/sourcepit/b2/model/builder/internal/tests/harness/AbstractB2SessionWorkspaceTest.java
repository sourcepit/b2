/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sourcepit.b2.common.internal.utils.DescriptorUtils;
import org.sourcepit.b2.common.internal.utils.XmlUtils;
import org.sourcepit.b2.common.internal.utils.DescriptorUtils.AbstractDescriptorResolutionStrategy;
import org.sourcepit.b2.common.internal.utils.DescriptorUtils.IDescriptorResolutionStrategy;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.builder.util.IB2SessionService;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.inject.Binder;

public abstract class AbstractB2SessionWorkspaceTest extends AbstractInjectedWorkspaceTest
{
   @Inject
   protected IB2SessionService sessionService;

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);

      final File moduleDir = setUpModuleDir();
      assertTrue(moduleDir.canRead());

      final B2Session session = createSession(moduleDir.getAbsoluteFile());
      assertNotNull(session);

      B2SessionService sessionService = new B2SessionService();
      sessionService.setCurrentSession(session);
      sessionService.setCurrentResourceSet(new ResourceSetImpl());

      binder.bind(IB2SessionService.class).toInstance(sessionService);
   }

   protected B2Session getCurrentSession()
   {
      return sessionService.getCurrentSession();
   }

   protected ModuleProject getModuleProjectByArtifactId(String artifactId)
   {
      for (ModuleProject project : getCurrentSession().getProjects())
      {
         if (artifactId.equals(project.getArtifactId()))
         {
            return project;
         }
      }
      return null;
   }

   protected File getModuleDirByArtifactId(String artifactId)
   {
      ModuleProject project = getModuleProjectByArtifactId(artifactId);
      if (project != null)
      {
         return project.getDirectory();
      }
      return null;
   }

   protected File getCurrentModuleDir()
   {
      return getCurrentSession().getCurrentProject().getDirectory();
   }

   protected File setUpModuleDir()
   {
      return workspace.importResources(setUpModulePath());
   }

   protected abstract String setUpModulePath();

   protected B2Session createSession(File moduleDir)
   {
      final List<File> descriptors = new ArrayList<File>();
      final List<File> skippedDescriptors = new ArrayList<File>();

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

      DescriptorUtils.findModuleDescriptors(moduleDir, descriptors, skippedDescriptors, resolver);

      final B2Session session = SessionModelFactory.eINSTANCE.createB2Session();
      for (File descriptor : descriptors)
      {
         if (!skippedDescriptors.contains(descriptor))
         {
            final ModuleProject project = createProject(descriptor);
            session.getProjects().add(project);
         }
      }

      if (!session.getProjects().isEmpty())
      {
         computeBuildOrder(session);
         session.setCurrentProject(session.getProjects().get(0));
      }

      return session;
   }

   protected void computeBuildOrder(B2Session session)
   {
      List<ModuleProject> ordered = new ArrayList<ModuleProject>();
      EList<ModuleProject> projects = session.getProjects();
      for (ModuleProject project : projects)
      {
         addDependencyProjects(session, ordered, project);
      }
      projects.clear();
      projects.addAll(ordered);
   }

   protected void addDependencyProjects(B2Session session, List<ModuleProject> ordered, ModuleProject project)
   {
      if (ordered.contains(project))
      {
         return;
      }
      for (ModuleDependency dependency : project.getDependencies())
      {
         for (ModuleProject p : session.getProjects())
         {
            if (dependency.isSatisfiableBy(p))
            {
               addDependencyProjects(session, ordered, p);
               break;
            }
         }
      }
      ordered.add(project);
   }

   protected ModuleProject createProject(File descriptor)
   {
      final Document moduleXml = XmlUtils.readXml(descriptor);

      final ModuleProject project = SessionModelFactory.eINSTANCE.createModuleProject();
      project.setDirectory(descriptor.getParentFile());
      project.setGroupId(XmlUtils.queryText(moduleXml, "/project/groupId"));
      project.setArtifactId(XmlUtils.queryText(moduleXml, "/project/artifactId"));
      project.setVersion(XmlUtils.queryText(moduleXml, "/project/version"));
      // TODO resolve transitive dependencies
      for (Node depNode : XmlUtils.queryNodes(moduleXml, "/project/dependencies/dependency"))
      {
         project.getDependencies().add(createDependency(depNode));
      }
      return project;
   }

   protected ModuleDependency createDependency(Node depNode)
   {
      final ModuleDependency dependency = SessionModelFactory.eINSTANCE.createModuleDependency();
      for (Node gavNode : XmlUtils.toIterable(depNode.getChildNodes()))
      {
         if (gavNode instanceof Element)
         {
            Element gavElem = (Element) gavNode;
            String tagName = gavElem.getTagName();
            String value = gavElem.getTextContent();
            if ("groupId".equals(tagName))
            {
               dependency.setGroupId(value);
            }
            else if ("artifactId".equals(tagName))
            {
               dependency.setArtifactId(value);
            }
            else if ("version".equals(tagName))
            {
               dependency.setVersionRange(value);
            }
         }
      }
      return dependency;
   }
}
