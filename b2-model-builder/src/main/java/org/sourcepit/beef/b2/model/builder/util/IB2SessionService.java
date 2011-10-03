/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.util;

import org.sourcepit.beef.b2.model.session.B2Session;


/**
 * IB2SessionService
 * @author Bernd
 */
public interface IB2SessionService
{

   public abstract B2Session getCurrentSession();

}