/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.module;

import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.module.Annotateable;

public interface IModuleParserExtender
{
   void extend(Annotateable modelElement, IConverter converter);
}
