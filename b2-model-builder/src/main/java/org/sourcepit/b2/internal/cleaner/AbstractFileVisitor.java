/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.cleaner;

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
