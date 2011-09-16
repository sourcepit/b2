/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.beef.b2.directory.parser.internal.facets;

import java.io.File;

import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.module.AbstractFacet;

/**
 * @author Bernd
 */
public abstract class AbstractFacetsParserRule<F extends AbstractFacet>
{
   public abstract FacetsParseResult<F> parse(File directory, IConverter converter);
}
