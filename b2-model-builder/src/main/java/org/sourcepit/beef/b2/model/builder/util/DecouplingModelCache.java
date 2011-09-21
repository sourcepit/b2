/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.sourcepit.beef.b2.model.common.CommonModelPackage;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.ModuleModelPackage;
import org.sourcepit.beef.b2.model.session.SessionModelPackage;

/**
 * @author Bernd
 */
public class DecouplingModelCache implements IModelCache
{
   private final Map<String, IInterpolationLayout> idToLayoutMap = new HashMap<String, IInterpolationLayout>();

   private final Map<File, String> dirToUriMap = new HashMap<File, String>();

   private final Map<File, AbstractModule> dirToModelMap = new HashMap<File, AbstractModule>();

   private ResourceSet resourceSet = new ResourceSetImpl();

   public DecouplingModelCache()
   {
      CommonModelPackage.eINSTANCE.getClass();
      ModuleModelPackage.eINSTANCE.getClass();
      SessionModelPackage.eINSTANCE.getClass();
      
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());
   }

   public Map<String, IInterpolationLayout> getIdToLayoutMap()
   {
      return idToLayoutMap;
   }

   public Map<File, String> getDirToUriMap()
   {
      return dirToUriMap;
   }

   public AbstractModule get(File moduleDir)
   {
      AbstractModule model = dirToModelMap.get(moduleDir);
      if (model == null)
      {
         final String uri = dirToUriMap.get(moduleDir);
         if (uri != null)
         {
            model = load(uri);
            if (model != null)
            {
               dirToModelMap.put(moduleDir, model);
            }
         }
      }
      return model;
   }

   private AbstractModule load(String uri)
   {
      try
      {
         Resource resource = resourceSet.getResource(URI.createURI(uri), true);
         if (!resource.getErrors().isEmpty())
         {
            throw new IllegalStateException(resource.getErrors().get(0).getMessage());
         }
         return (AbstractModule) resource.getContents().get(0);
      }
      catch (WrappedException e)
      {
         throw new IllegalStateException(e.getCause());
      }
   }

   public void put(AbstractModule module)
   {
      final String layoutId = module.getLayoutId();
      final IInterpolationLayout interpolationLayout = idToLayoutMap.get(layoutId);
      if (interpolationLayout == null)
      {
         throw new UnsupportedOperationException("Layout " + layoutId + " is not supported.");
      }
      final URI uri = URI.createFileURI(interpolationLayout.pathOfMetaDataFile(module, "b2.module"));

      final Resource resource = resourceSet.createResource(uri);
      resource.getContents().add(module);
      try
      {
         resource.save(null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      dirToUriMap.put(module.getDirectory(), uri.toString());
      dirToModelMap.put(module.getDirectory(), module);
   }
}
