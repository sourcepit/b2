/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
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
