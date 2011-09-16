/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.AbstractModule;

/**
 * @author Bernd
 */
public class DecouplingModelCache implements IModelCache
{
   private final Map<String, IInterpolationLayout> idToLayoutMap = new HashMap<String, IInterpolationLayout>();

   private final Map<File, String> dirToUriMap = new HashMap<File, String>();

   private final Map<File, AbstractModule> dirToModelMap = new HashMap<File, AbstractModule>();

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
      final Resource resource = new XMIResourceImpl(URI.createURI(uri));
      try
      {
         resource.load(null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
      return (AbstractModule) resource.getContents().get(0);
   }

   public void put(AbstractModule module)
   {
      final String layoutId = module.getLayoutId();
      final IInterpolationLayout interpolationLayout = idToLayoutMap.get(layoutId);
      if (interpolationLayout == null)
      {
         throw new UnsupportedOperationException("Layout " + layoutId + " is not supported.");
      }
      final URI uri = URI.createFileURI(interpolationLayout.pathOfMetaDataFile(module, "module.b2"));

      final Resource resource = new XMIResourceImpl(uri);
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
