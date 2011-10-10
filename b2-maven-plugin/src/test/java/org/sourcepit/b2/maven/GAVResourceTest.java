/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sourcepit.b2.model.common.util.ArtifactReference;
import org.sourcepit.b2.model.common.util.ArtifactURIResolver;
import org.sourcepit.b2.model.common.util.GavResourceUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.tools.shared.resources.harness.AbstractWorkspaceTest;

public class GAVResourceTest extends AbstractWorkspaceTest
{
   public void testGAV() throws Exception
   {
      ResourceSet resourceSet = createResourceSet();

      BasicModule m1 = ModuleModelFactory.eINSTANCE.createBasicModule();
      m1.setId("foo");

      ModuleProject p1 = SessionModelFactory.eINSTANCE.createModuleProject();
      p1.setGroupId("org.sourcepit");
      p1.setArtifactId("p1");
      p1.setVersion("1.0");

      p1.setModuleModel(m1);

      final URI moduleUri = new ArtifactReference(p1.getGroupId(), p1.getArtifactId(), p1.getVersion(), "module")
         .toUri();
      Resource resource = resourceSet.createResource(moduleUri);
      resource.getContents().add(m1);
      resource.save(null);

      final ArtifactReference projectRef = new ArtifactReference(p1.getGroupId(), p1.getArtifactId(), p1.getVersion(),
         "session");
      final URI projectUri = projectRef.toUri();

      File projectFile = toFile(projectRef);
      assertFalse(projectFile.exists());

      resource = resourceSet.createResource(projectUri);
      resource.getContents().add(p1);
      resource.save(null);

      assertTrue(projectFile.exists());

      ResourceSet resourceSet2 = createResourceSet();
      Resource resource2 = resourceSet2.getResource(projectUri, true);
      ModuleProject p2 = (ModuleProject) resource2.getContents().get(0);
      assertEquals(p1.getGroupId(), p2.getGroupId());
      assertEquals(p1.getArtifactId(), p2.getArtifactId());
      assertEquals(p1.getVersion(), p2.getVersion());

      AbstractModule m2 = p2.getModuleModel();
      assertEquals(m1.getId(), m2.getId());
   }

   private ResourceSet createResourceSet()
   {
      ArtifactURIResolver artifactResolver = new ArtifactURIResolver()
      {
         public URI resolve(ArtifactReference artifactReference)
         {
            final File file = toFile(artifactReference);
            return URI.createFileURI(file.getAbsolutePath());
         }
      };

      ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());

      GavResourceUtils.configureResourceSet(resourceSet, artifactResolver);

      return resourceSet;
   }

   private File toFile(ArtifactReference artifactReference)
   {
      return new File(getWorkspace().getDir(), artifactReference.getArtifactId() + "." + artifactReference.getType());
   }
}
