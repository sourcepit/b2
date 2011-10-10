/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.Reference;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;

public class CModuleModelFactoryImpl extends ModuleModelFactoryImpl
{
   @Override
   public CompositeModule createCompositeModule()
   {
      return new CCompositeModuleImpl();
   }

   @Override
   public BasicModule createBasicModule()
   {
      return new CBasicModuleImpl();
   }

   @Override
   public FeaturesFacet createFeaturesFacet()
   {
      return new CFeaturesFacetImpl();
   }

   @Override
   public SitesFacet createSitesFacet()
   {
      return new CSitesFacetImpl();
   }

   @Override
   public PluginsFacet createPluginsFacet()
   {
      return new CPluginsFacetImpl();
   }

   @Override
   public ProductsFacet createProductsFacet()
   {
      return new CProductsFacetImpl();
   }

   @Override
   public FeatureProject createFeatureProject()
   {
      return new CFeatureProjectImpl();
   }

   @Override
   public SiteProject createSiteProject()
   {
      return new CSiteProjectImpl();
   }

   @Override
   public PluginProject createPluginProject()
   {
      return new CPluginProjectImpl();
   }

   @Override
   public ProductDefinition createProductDefinition()
   {
      return new CProductDefinitionImpl();
   }

   @Override
   public PluginInclude createPluginInclude()
   {
      return new CPluginIncludeImpl();
   }

   @Override
   public Reference createReference()
   {
      return new CReferenceImpl();
   }

   @Override
   public FeatureInclude createFeatureInclude()
   {
      return new CFeatureIncludeImpl();
   }
}
