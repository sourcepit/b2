/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_GROUP_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_TPC_PLUGIN_ARTIFACT_ID;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getBuild;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.inheritance.InheritanceAssembler;

@Named
public class TargetPlatformConfigurationInhertianceAssembler
{
   private final InheritanceAssembler modelMerger;

   @Inject
   public TargetPlatformConfigurationInhertianceAssembler(InheritanceAssembler modelMerger)
   {
      this.modelMerger = modelMerger;
   }

   public void assembleTPCInheritance(List<Model> hierarchy)
   {
      if (hierarchy.isEmpty())
      {
         throw new IllegalArgumentException("At least one model has to be contained in hierarchy.");
      }

      final Model targetDummy = hierarchy.get(0).clone();
      for (int i = 1; i < hierarchy.size(); i++)
      {
         modelMerger.assembleModelInheritance(targetDummy, hierarchy.get(i), null, null);
      }

      final Plugin tpc = getPlugin(targetDummy, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      if (tpc != null)
      {
         final Build build = getBuild(hierarchy.get(0), true);

         final Plugin staleTPC = getPlugin(build, tpc.getGroupId(), tpc.getArtifactId(), false);
         if (staleTPC != null)
         {
            build.getPlugins().remove(staleTPC);
         }

         build.addPlugin(tpc.clone());
         build.flushPluginMap();
      }
   }
}
