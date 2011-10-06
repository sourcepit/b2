/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.common.util;

import org.eclipse.emf.common.util.URI;

public interface ArtifactURIResolver
{
   URI resolve(ArtifactReference artifactReference);
}