/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public class TargetPlatformInhertianceAssembler
{
   private final InheritanceAssembler modelMerger;

   @Inject
   public TargetPlatformInhertianceAssembler(InheritanceAssembler modelMerger)
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
