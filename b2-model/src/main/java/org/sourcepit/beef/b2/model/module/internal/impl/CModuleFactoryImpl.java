/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.CompositeModule;
import org.sourcepit.beef.b2.model.module.FeatureInclude;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.PluginInclude;
import org.sourcepit.beef.b2.model.module.PluginProject;
import org.sourcepit.beef.b2.model.module.PluginsFacet;
import org.sourcepit.beef.b2.model.module.ProductDefinition;
import org.sourcepit.beef.b2.model.module.ProductsFacet;
import org.sourcepit.beef.b2.model.module.Reference;
import org.sourcepit.beef.b2.model.module.SiteProject;
import org.sourcepit.beef.b2.model.module.SitesFacet;

public class CModuleFactoryImpl extends ModuleFactoryImpl
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
