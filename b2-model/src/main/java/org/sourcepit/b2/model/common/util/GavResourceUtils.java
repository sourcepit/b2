/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.model.common.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class GavResourceUtils
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
