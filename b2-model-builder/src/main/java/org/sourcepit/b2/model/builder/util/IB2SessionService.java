/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.session.B2Session;

public interface IB2SessionService
{
   ResourceSet getCurrentResourceSet();

   B2Session getCurrentSession();

}