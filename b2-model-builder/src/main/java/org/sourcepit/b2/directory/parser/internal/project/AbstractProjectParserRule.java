/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.Project;

/**
 * @author Bernd
 */
public abstract class AbstractProjectParserRule<P extends Project>
{
   public abstract P parse(File directory, IConverter converter);
}
