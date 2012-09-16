/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;

public class ModelContext
{
   private final ResourceSet resourceSet;
   private final URI uri;
   private final SetMultimap<AbstractModule, FeatureProject> scopeMain;
   private final SetMultimap<AbstractModule, FeatureProject> scopeTest;

   public ModelContext(ResourceSet resourceSet, URI uri, SetMultimap<AbstractModule, FeatureProject> scopeMain,
      SetMultimap<AbstractModule, FeatureProject> scopeTest)
   {
      this.resourceSet = resourceSet;
      this.uri = uri;
      this.scopeMain = ImmutableSetMultimap.copyOf(scopeMain);
      this.scopeTest = ImmutableSetMultimap.copyOf(scopeTest);
   }

   public ResourceSet getResourceSet()
   {
      return resourceSet;
   }

   public URI getUri()
   {
      return uri;
   }

   // TODO its false to use every classifier attached at the feature project. we have to introduce a dedicated
   // "Assembly" model object or sth. similar
   public SetMultimap<AbstractModule, FeatureProject> getMainScope()
   {
      return scopeMain;
   }

   // TODO its false to use every classifier attached at the feature project. we have to introduce a dedicated
   // "Assembly" model object or sth. similar
   public SetMultimap<AbstractModule, FeatureProject> getTestScope()
   {
      return scopeTest;
   }
}
