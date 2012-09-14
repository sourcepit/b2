/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.module.AbstractModule;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

public class ModuleModelContext
{
   private final ResourceSet resourceSet;
   private final URI uri;
   private final SetMultimap<URI, String> scopeMain;
   private final SetMultimap<URI, String> scopeTest;

   private SetMultimap<AbstractModule, String> scopeMainResolved;
   private SetMultimap<AbstractModule, String> scopeTestResolved;

   public ModuleModelContext(ResourceSet resourceSet, URI uri, SetMultimap<URI, String> scopeMain,
      SetMultimap<URI, String> scopeTest)
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

   public SetMultimap<URI, String> getMainScope()
   {
      return scopeMain;
   }

   public SetMultimap<AbstractModule, String> getResolvedMainScope()
   {
      if (scopeMainResolved == null)
      {
         scopeMainResolved = ImmutableSetMultimap.copyOf(resolve(getResourceSet(), getMainScope()));
      }
      return scopeMainResolved;
   }

   public SetMultimap<URI, String> getTestScope()
   {
      return scopeTest;
   }

   public SetMultimap<AbstractModule, String> getResolvedTestScope()
   {
      if (scopeTestResolved == null)
      {
         scopeTestResolved = ImmutableSetMultimap.copyOf(resolve(getResourceSet(), getTestScope()));
      }
      return scopeTestResolved;
   }

   private static SetMultimap<AbstractModule, String> resolve(ResourceSet resourceSet,
      SetMultimap<URI, String> unresolved)
   {
      final SetMultimap<AbstractModule, String> resolved = LinkedHashMultimap.create(unresolved.size(), 4);
      for (Entry<URI, Collection<String>> entry : unresolved.asMap().entrySet())
      {
         final URI uri = entry.getKey();
         final AbstractModule module = (AbstractModule) resourceSet.getResource(uri, true).getContents().get(0);
         resolved.get(module).addAll(entry.getValue());
      }
      return resolved;
   }
}
