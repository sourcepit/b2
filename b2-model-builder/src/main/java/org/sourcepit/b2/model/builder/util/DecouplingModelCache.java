/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;

/**
 * @author Bernd
 */
public class DecouplingModelCache implements IModelCache
{
   private final LayoutManager layoutManager;

   private final Map<File, String> dirToUriMap = new HashMap<File, String>();

   private final Map<File, AbstractModule> dirToModelMap = new HashMap<File, AbstractModule>();

   private final ResourceSet resourceSet;

   public DecouplingModelCache(ResourceSet resourceSet, LayoutManager layoutManager)
   {
      this.resourceSet = resourceSet;
      this.layoutManager = layoutManager;
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
      Resource resource;

      if (module.eResource() != null)
      {
         resource = module.eResource();
      }
      else
      {
         final String layoutId = module.getLayoutId();
         final IInterpolationLayout layout = layoutManager.getLayout(layoutId);
         final URI uri = URI.createFileURI(layout.pathOfMetaDataFile(module, "b2.module"));
         resource = resourceSet.createResource(uri);
         resource.getContents().add(module);
      }

      try
      {
         resource.save(null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      dirToUriMap.put(module.getDirectory(), resource.getURI().toString());
      dirToModelMap.put(module.getDirectory(), module);
   }
}
