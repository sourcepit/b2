/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.module;

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
