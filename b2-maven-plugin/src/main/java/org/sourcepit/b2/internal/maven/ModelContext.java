/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
   private final URI moduleUri;
   private final SetMultimap<AbstractModule, FeatureProject> scopeMain;
   private final SetMultimap<AbstractModule, FeatureProject> scopeTest;

   public ModelContext(ResourceSet resourceSet, URI moduleUri, SetMultimap<AbstractModule, FeatureProject> scopeMain,
      SetMultimap<AbstractModule, FeatureProject> scopeTest)
   {
      this.resourceSet = resourceSet;
      this.moduleUri = moduleUri;
      this.scopeMain = ImmutableSetMultimap.copyOf(scopeMain);
      this.scopeTest = ImmutableSetMultimap.copyOf(scopeTest);
   }

   public ResourceSet getResourceSet()
   {
      return resourceSet;
   }

   public URI getModuleUri()
   {
      return moduleUri;
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
