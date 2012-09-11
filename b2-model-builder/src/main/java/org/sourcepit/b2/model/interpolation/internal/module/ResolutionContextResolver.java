/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.common.utils.collections.MultiValueMap;

public interface ResolutionContextResolver
{
   public static class ContextEntry
   {
      private final AbstractModule module;

      private final Collection<String> assemblyNames;

      public ContextEntry(AbstractModule module, Collection<String> assemblyNames)
      {
         this.module = module;
         this.assemblyNames = Collections.unmodifiableCollection(assemblyNames);
      }

      public AbstractModule getModule()
      {
         return module;
      }

      public Collection<String> getAssemblyNames()
      {
         return assemblyNames;
      }
   }

//   List<ContextEntry> resolveResolutionContext(AbstractModule module, FeatureProject resolutionTarget);

   void determineForeignResolutionContext(MultiValueMap<AbstractModule, String> moduleToAssemblies,
      AbstractModule module, FeatureProject resolutionTarget);
}