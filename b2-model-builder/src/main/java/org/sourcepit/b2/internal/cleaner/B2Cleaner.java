/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.cleaner;

import java.io.File;

import javax.inject.Named;

@Named
public class B2Cleaner implements IModuleGarbageCollector
{
   public boolean isGarbage(File file)
   {
      return file.isDirectory() && ".b2".equals(file.getName());
   }
}
