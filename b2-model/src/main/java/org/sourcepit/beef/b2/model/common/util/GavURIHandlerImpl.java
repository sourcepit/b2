/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;

public class GavURIHandlerImpl extends URIHandlerImpl
{
   private final ArtifactURIResolver artifactURIResolver;

   public GavURIHandlerImpl(ArtifactURIResolver artifactURIResolver)
   {
      this.artifactURIResolver = artifactURIResolver;
   }

   public boolean canHandle(URI uri)
   {
      return "gav".equals(uri.scheme());
   }

   @Override
   public boolean exists(URI gavUri, Map<?, ?> options)
   {
      final URI resolvedUri = resolve(gavUri);
      final URIHandler delegateUriHandler = getDelegate(resolvedUri, options);
      return delegateUriHandler.exists(resolvedUri, options);
   }

   @Override
   public InputStream createInputStream(URI gavUri, Map<?, ?> options) throws IOException
   {
      final URI resolvedUri = resolve(gavUri);
      final URIHandler delegateUriHandler = getDelegate(resolvedUri, options);
      return delegateUriHandler.createInputStream(resolvedUri, options);
   }

   @Override
   public OutputStream createOutputStream(URI gavUri, Map<?, ?> options) throws IOException
   {
      final URI resolvedUri = resolve(gavUri);
      final URIHandler delegateUriHandler = getDelegate(resolvedUri, options);
      return delegateUriHandler.createOutputStream(resolvedUri, options);
   }

   @Override
   public void delete(URI gavUri, Map<?, ?> options) throws IOException
   {
      final URI resolvedUri = resolve(gavUri);
      final URIHandler delegateUriHandler = getDelegate(resolvedUri, options);
      delegateUriHandler.delete(resolvedUri, options);
   }

   @Override
   public Map<String, ?> getAttributes(URI gavUri, Map<?, ?> options)
   {
      final URI resolvedUri = resolve(gavUri);
      final URIHandler delegateUriHandler = getDelegate(resolvedUri, options);
      return delegateUriHandler.getAttributes(resolvedUri, options);
   }

   @Override
   public void setAttributes(URI gavUri, Map<String, ?> attributes, Map<?, ?> options) throws IOException
   {
      final URI resolvedUri = resolve(gavUri);
      final URIHandler delegateUriHandler = getDelegate(resolvedUri, options);
      delegateUriHandler.setAttributes(resolvedUri, attributes, options);
   }

   protected URIHandler getDelegate(final URI resolvedUri, Map<?, ?> options)
   {
      return getURIConverter(options).getURIHandler(resolvedUri);
   }

   protected URI resolve(URI gavUri)
   {
      final ArtifactReference artifactReference = ArtifactReference.fromUri(gavUri);
      return resolve(artifactReference);
   }

   protected URI resolve(ArtifactReference artifactReference)
   {
      return artifactURIResolver.resolve(artifactReference);
   }
}