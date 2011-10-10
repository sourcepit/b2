/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.module;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class WhitelistModuleFilter implements IModuleFilter
{
   private final Set<File> whitelist;

   public WhitelistModuleFilter(File... whitelist)
   {
      this(Arrays.asList(whitelist));
   }

   public WhitelistModuleFilter(Collection<File> whitelist)
   {
      this.whitelist = new HashSet<File>(whitelist);
   }

   public boolean accept(File moduleDir)
   {
      return whitelist.contains(moduleDir);
   }
}
