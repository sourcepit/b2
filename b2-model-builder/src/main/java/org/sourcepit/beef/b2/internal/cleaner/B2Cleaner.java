/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.cleaner;

import java.io.File;

import javax.inject.Named;

@Named
public class B2Cleaner implements IModuleCleanerParticipant
{
   public boolean isGarbage(File file)
   {
      return file.isDirectory() && ".b2".equals(file.getName());
   }
}
