/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;

public final class GavResourceUtils
{
   private GavResourceUtils()
   {
      super();
   }

   public static void configureResourceSet(ResourceSet resourceSet, ArtifactURIResolver resolver)
   {
      // add GAV resource handler
      resourceSet.getURIConverter().getURIHandlers().add(0, new GavURIHandlerImpl(resolver));

      // configure resource factory reg with GAV URI resolver
      final ExtensibleResourceFactoryRegistryImpl resourceFactoryRegistry = new ExtensibleResourceFactoryRegistryImpl(
         resourceSet.getResourceFactoryRegistry());
      resourceFactoryRegistry.getURIResolvers().add(new GavURIResolver(resolver));
      resourceSet.setResourceFactoryRegistry(resourceFactoryRegistry);
   }

   public static URI resolveGavURI(ResourceSet resourceSet, URI gavURI)
   {
      return null;
   }
}
