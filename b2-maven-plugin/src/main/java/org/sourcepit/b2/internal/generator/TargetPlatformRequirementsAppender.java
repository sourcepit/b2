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

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_GROUP_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_TPC_PLUGIN_ARTIFACT_ID;
import static org.sourcepit.b2.internal.maven.util.TychoXpp3Utils.readRequirements;
import static org.sourcepit.b2.internal.maven.util.TychoXpp3Utils.toRequirementNode;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;
import static org.sourcepit.common.maven.util.Xpp3Utils.clearChildren;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;

@Named
public class TargetPlatformRequirementsAppender
{
   public void append(Model model, List<Dependency> requirements)
   {
      checkNotNull(model);

      if (requirements.isEmpty())
      {
         return;
      }

      final Plugin plugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, true);
      Xpp3Dom configuration = (Xpp3Dom) plugin.getConfiguration();
      if (configuration == null)
      {
         configuration = new Xpp3Dom("configuration");
         plugin.setConfiguration(configuration);
      }

      Xpp3Dom dependencyResolution = configuration.getChild("dependency-resolution");
      if (dependencyResolution == null)
      {
         dependencyResolution = new Xpp3Dom("dependency-resolution");
         configuration.addChild(dependencyResolution);
      }

      Xpp3Dom extraRequirements = dependencyResolution.getChild("extraRequirements");
      if (extraRequirements == null)
      {
         extraRequirements = new Xpp3Dom("extraRequirements");
         dependencyResolution.addChild(extraRequirements);
      }

      final List<Dependency> dependencyList = readRequirements(extraRequirements);
      addAllUnique(dependencyList, requirements);

      clearChildren(extraRequirements);

      for (Dependency dependency : dependencyList)
      {
         extraRequirements.addChild(toRequirementNode(dependency));
      }
   }

   private static void addAllUnique(List<Dependency> dest, List<Dependency> src)
   {
      final Set<String> managementKeys = new HashSet<String>();
      for (Dependency dependency : dest)
      {
         managementKeys.add(dependency.getManagementKey());
      }
      for (Dependency dependency : src)
      {
         if (managementKeys.add(dependency.getManagementKey()))
         {
            dest.add(dependency);
         }
      }
   }
}
