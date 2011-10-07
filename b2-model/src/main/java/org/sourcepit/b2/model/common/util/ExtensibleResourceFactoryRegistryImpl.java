/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.model.common.util;

import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;

/**
 * GavResourceFactoryRegistryImpl
 * 
 * @author Bernd
 */
public class ExtensibleResourceFactoryRegistryImpl implements Registry
{
   protected static class URIResolverList extends BasicEList<URIResolver>
   {
      private static final long serialVersionUID = 1L;

      public URIResolverList()
      {
         super();
      }

      @Override
      protected boolean canContainNull()
      {
         return false;
      }

      @Override
      protected Object[] newData(int capacity)
      {
         return new URIResolver[capacity];
      }

      @Override
      public URIResolver[] data()
      {
         return (URIResolver[]) data;
      }
   }

   protected final Registry delegate;
   protected URIResolverList uriResolvers;

   public ExtensibleResourceFactoryRegistryImpl(Registry delegate)
   {
      this.delegate = delegate;
   }

   public EList<URIResolver> getURIResolvers()
   {
      if (uriResolvers == null)
      {
         uriResolvers = new URIResolverList();
      }
      return uriResolvers;
   }

   public URIResolver getURIResolver(URI uri)
   {
      int size = uriResolvers.size();
      if (size > 0)
      {
         URIResolver[] data = uriResolvers.data();
         for (int i = 0; i < size; ++i)
         {
            URIResolver logicalURIResolver = data[i];
            if (logicalURIResolver.canResolve(uri))
            {
               return logicalURIResolver;
            }
         }
      }
      return null;
   }

   public Factory getFactory(URI uri)
   {
      return getFactory(uri, ContentHandler.UNSPECIFIED_CONTENT_TYPE);
   }

   public Factory getFactory(URI uri, String contentType)
   {
      URI resolved = uri;

      final URIResolver resolver = getURIResolver(uri);
      if (resolver != null)
      {
         resolved = resolver.resolve(resolved);
      }

      if (resolved == null)
      {
         resolved = uri;
      }

      return delegate.getFactory(resolved, contentType);
   }

   public Map<String, Object> getProtocolToFactoryMap()
   {
      return delegate.getProtocolToFactoryMap();
   }

   public Map<String, Object> getExtensionToFactoryMap()
   {
      return delegate.getExtensionToFactoryMap();
   }

   public Map<String, Object> getContentTypeToFactoryMap()
   {
      return delegate.getContentTypeToFactoryMap();
   }

}
