/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.cleaner;

import java.io.File;

import javax.inject.Named;

@Named
public class PomCleaner implements IModuleCleanerParticipant
{
   public boolean isGarbage(File file)
   {
      return file.isFile() && "pom.xml".equals(file.getName()) && new File(file.getParentFile(), "module.xml").exists();
   }
}
