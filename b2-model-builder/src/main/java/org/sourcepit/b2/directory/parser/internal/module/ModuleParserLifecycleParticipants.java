/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.module.ModuleParserLifecycleParticipant;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;

@Named
public class ModuleParserLifecycleParticipants
{
   @Inject
   private List<ModuleParserLifecycleParticipant> lifecycleParticipants;

   public void preParse(File moduleDir)
   {
      for (ModuleParserLifecycleParticipant lifecycleParticipant : lifecycleParticipants)
      {
         lifecycleParticipant.preParse(moduleDir);
      }
   }

   public void postParse(File moduleDir, AbstractModule module, ThrowablePipe errors)
   {
      for (ModuleParserLifecycleParticipant lifecycleParticipant : lifecycleParticipants)
      {
         lifecycleParticipant.postParse(moduleDir, module, errors);
      }
   }
}
