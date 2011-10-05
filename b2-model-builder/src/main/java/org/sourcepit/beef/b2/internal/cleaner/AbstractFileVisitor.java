/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.cleaner;

import java.io.File;

public abstract class AbstractFileVisitor implements IFileVisitor
{
   public void visitGarbage(File file)
   {
   }

   public boolean visit(File file)
   {
      return true;
   }
}