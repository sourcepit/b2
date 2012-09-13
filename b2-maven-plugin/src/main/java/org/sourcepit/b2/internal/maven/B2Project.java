/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class B2Project
{
   private final File directory;
   private final URI uri;
   private final ResourceSet resourceSet;
   private final Map<URI, Collection<String>> scopeMain;
   private final Map<URI, Collection<String>> scopeTest;

   public B2Project(URI uri, File dir, ResourceSet resourceSet, Map<URI, Collection<String>> scopeMain,
      Map<URI, Collection<String>> scopeTest)
   {
      this.directory = dir;
      this.uri = uri;
      this.resourceSet = resourceSet;
      this.scopeMain = Collections.unmodifiableMap(scopeMain);
      this.scopeTest = Collections.unmodifiableMap(scopeTest);
   }

   public File getDirectory()
   {
      return directory;
   }

   public URI getUri()
   {
      return uri;
   }

   public ResourceSet getResourceSet()
   {
      return resourceSet;
   }

   public Map<URI, Collection<String>> getMainDependencies()
   {
      return scopeMain;
   }

   public Map<URI, Collection<String>> getTestDependencies()
   {
      return scopeTest;
   }
}
