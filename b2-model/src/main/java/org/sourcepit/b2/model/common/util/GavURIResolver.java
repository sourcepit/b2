/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common.util;

import org.eclipse.emf.common.util.URI;

public class GavURIResolver implements URIResolver
{
   private final ArtifactURIResolver artifactURIResolver;

   public GavURIResolver(ArtifactURIResolver artifactURIResolver)
   {
      this.artifactURIResolver = artifactURIResolver;
   }

   public boolean canResolve(URI uri)
   {
      return "gav".equals(uri.scheme());
   }

   public URI resolve(URI gavUri)
   {
      final ArtifactReference artifactReference = ArtifactReference.fromUri(gavUri);
      return resolve(artifactReference);
   }

   protected URI resolve(ArtifactReference artifactReference)
   {
      return artifactURIResolver.resolve(artifactReference);
   }
}
