/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.internal.impl;

import java.util.Locale;

import org.eclipse.emf.ecore.EDataType;
import org.sourcepit.beef.b2.internal.model.BasicModule;
import org.sourcepit.beef.b2.internal.model.CompositeModule;
import org.sourcepit.beef.b2.internal.model.FeatureInclude;
import org.sourcepit.beef.b2.internal.model.FeatureProject;
import org.sourcepit.beef.b2.internal.model.FeaturesFacet;
import org.sourcepit.beef.b2.internal.model.PluginInclude;
import org.sourcepit.beef.b2.internal.model.PluginProject;
import org.sourcepit.beef.b2.internal.model.PluginsFacet;
import org.sourcepit.beef.b2.internal.model.ProductDefinition;
import org.sourcepit.beef.b2.internal.model.ProductsFacet;
import org.sourcepit.beef.b2.internal.model.Reference;
import org.sourcepit.beef.b2.internal.model.SiteProject;
import org.sourcepit.beef.b2.internal.model.SitesFacet;

public class CB2ModelFactoryImpl extends B2ModelFactoryImpl
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

   @Override
   public Locale createELocaleFromString(EDataType eDataType, String initialValue)
   {
      if (initialValue == null)
      {
         return null;
      }
      String[] groups = initialValue.split("_");
      final String[] segments = new String[3];
      for (int i = 0; i < segments.length; i++)
      {
         String group = groups.length > i ? groups[i] : null;
         segments[i] = group == null ? "" : group.startsWith("_") ? group.substring(1) : group;
      }
      return new Locale(segments[0], segments[1], segments[2]);
   }
}
