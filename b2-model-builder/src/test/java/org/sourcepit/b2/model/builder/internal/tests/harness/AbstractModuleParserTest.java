/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelReader;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;


public abstract class AbstractModuleParserTest extends AbstractInjectedWorkspaceTest
{
   @Inject
   private B2SessionService sessionService;

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected static <F extends AbstractFacet> F findFacetByName(Collection<F> facets, String name)
   {
      for (F f : facets)
      {
         if (name.equals(f.getName()))
         {
            return f;
         }
      }
      return null;
   }


   protected B2Session initSession(File... moduleDirs)
   {
      final B2Session session = SessionModelFactory.eINSTANCE.createB2Session();
      
      for (File moduleFile : moduleDirs)
      {
         Model moduleXML = readModuleXML(moduleFile);
         
         ModuleProject project = SessionModelFactory.eINSTANCE.createModuleProject();
         project.setDirectory(moduleFile);
         project.setGroupId(moduleXML.getGroupId());
         project.setArtifactId(moduleXML.getArtifactId());
         
         session.getProjects().add(project);
         
         if (session.getCurrentProject() == null)
         {
            session.setCurrentProject(project);
         }
      }

      sessionService.setCurrentSession(session);
      sessionService.setCurrentProperties(ConverterUtils.TEST_CONVERTER.getProperties());
      
      return session;
   }

   private Model readModuleXML(File moduleDir)
   {
      final Map<String, String> options = new HashMap<String, String>();
      options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());
      try
      {
         return new DefaultModelReader().read(new File(moduleDir, "module.xml"), options);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }
}
