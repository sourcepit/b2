/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
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

      BasicModule module = ModuleModelFactory.eINSTANCE.createBasicModule();

      ModuleProject p1 = SessionModelFactory.eINSTANCE.createModuleProject();
      p1.setGroupId("org.sourcepit");
      p1.setArtifactId("p1");
      p1.setVersion("1.0");

      p1.setModuleModel(module);

      final URI moduleUri = new ArtifactReference(p1.getGroupId(), p1.getArtifactId(), p1.getVersion(), "module")
         .toUri();
      Resource resource = resourceSet.createResource(moduleUri);
      resource.getContents().add(module);
      resource.save(null);

      final URI projectUri = new ArtifactReference(p1.getGroupId(), p1.getArtifactId(), p1.getVersion(), "session")
         .toUri();
      resource = resourceSet.createResource(projectUri);
      resource.getContents().add(p1);
      resource.save(null);

      // TODO assert

      ResourceSet resourceSet2 = createResourceSet();
      Resource resource2 = resourceSet2.getResource(projectUri, true);
      ModuleProject project = (ModuleProject) resource2.getContents().get(0);
      AbstractModule model = project.getModuleModel();

      // TODO assert
   }

   private ResourceSet createResourceSet()
   {
      ArtifactURIResolver artifactResolver = new ArtifactURIResolver()
      {
         public URI resolve(ArtifactReference artifactReference)
         {
            final File file = new File(getWorkspace().getDir(), artifactReference.getArtifactId() + "."
               + artifactReference.getType());
            return URI.createFileURI(file.getAbsolutePath());
         }
      };

      ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());

      GavResourceUtils.configureResourceSet(resourceSet, artifactResolver);

      return resourceSet;
   }
}
