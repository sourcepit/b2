/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.cleaner;

import java.io.File;

public interface IFileVisitor
{
   void visitGarbage(File file);

   boolean visit(File file);
}