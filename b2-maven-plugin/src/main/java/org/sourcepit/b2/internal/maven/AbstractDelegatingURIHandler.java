/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIHandler;

public abstract class AbstractDelegatingURIHandler implements URIHandler
{
   public boolean canHandle(URI uri)
   {
      final URI targetUri = toTargetURI(uri);
      return targetUri == null ? false : getTargetURIHandler(targetUri).canHandle(targetUri);
   }

   public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException
   {
      final URI targetUri = toTargetURI(uri);
      return getTargetURIHandler(targetUri).contentDescription(uri, options);
   }

   public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException
   {
      final URI targetUri = toTargetURI(uri);
      return getTargetURIHandler(targetUri).createInputStream(targetUri, options);
   }

   public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException
   {
      final URI targetUri = toTargetURI(uri);
      return getTargetURIHandler(targetUri).createOutputStream(targetUri, options);
   }

   public void delete(URI uri, Map<?, ?> options) throws IOException
   {
      final URI targetUri = toTargetURI(uri);
      getTargetURIHandler(targetUri).delete(targetUri, options);
   }

   public boolean exists(URI uri, Map<?, ?> options)
   {
      final URI targetUri = toTargetURI(uri);
      return getTargetURIHandler(targetUri).exists(targetUri, options);
   }

   public Map<String, ?> getAttributes(URI uri, Map<?, ?> options)
   {
      final URI targetUri = toTargetURI(uri);
      return getTargetURIHandler(targetUri).getAttributes(targetUri, options);
   }

   public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException
   {
      final URI targetUri = toTargetURI(uri);
      getTargetURIHandler(targetUri).setAttributes(uri, attributes, options);
   }

   protected abstract URI toTargetURI(URI uri);

   protected final URIHandler getTargetURIHandler(URI targetURI)
   {
      if (targetURI == null)
      {
         throw new IllegalArgumentException("Target URI may not be null");
      }

      final URIHandler uriHandler = determineTargetURIHandler(targetURI);

      if (uriHandler == null)
      {
         throw new IllegalStateException("Target URI handler may not be null");
      }

      return uriHandler;
   }

   protected abstract URIHandler determineTargetURIHandler(URI targetURI);
}
