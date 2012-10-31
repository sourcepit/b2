/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_GROUP_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_TPC_PLUGIN_ARTIFACT_ID;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;
import static org.sourcepit.common.maven.util.Xpp3Utils.addValueNode;
import static org.sourcepit.common.maven.util.Xpp3Utils.clearChildren;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import com.google.common.base.Strings;

@Named
public class TargetPlatformRequirementsAppender
{
   public void append(Model model, List<Dependency> requirements)
   {
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

   public static List<Dependency> readRequirements(Xpp3Dom requirements)
   {
      final List<Dependency> dependencyList = new ArrayList<Dependency>();
      for (Xpp3Dom requirement : requirements.getChildren("requirement"))
      {
         dependencyList.add(toRequirement(requirement));
      }
      return dependencyList;
   }

   public static Dependency toRequirement(Xpp3Dom requirement)
   {
      final Dependency result = new Dependency();
      result.setArtifactId(extractNonEmptyValue(requirement.getChild("id")));
      result.setVersion(extractNonEmptyValue(requirement.getChild("versionRange")));
      final String type = extractNonEmptyValue(requirement.getChild("type"));
      if (type != null)
      {
         result.setType(type);
      }
      return result;
   }

   public static Xpp3Dom toRequirementNode(@NotNull Dependency dependency)
   {
      final Xpp3Dom requirementNode = new Xpp3Dom("requirement");
      addRequirementValues(requirementNode, dependency);
      return requirementNode;
   }

   public static void addRequirementValues(@NotNull final Xpp3Dom dependencyNode, @NotNull Dependency dependency)
   {
      String value = dependency.getArtifactId();
      if (!Strings.isNullOrEmpty(value))
      {
         addValueNode(dependencyNode, "id", value);
      }

      value = dependency.getVersion();
      if (!Strings.isNullOrEmpty(value))
      {
         addValueNode(dependencyNode, "versionRange", value);
      }

      value = dependency.getType();
      if (!Strings.isNullOrEmpty(value) && !"jar".equals(value))
      {
         addValueNode(dependencyNode, "type", value);
      }
   }

   private static String extractNonEmptyValue(Xpp3Dom node)
   {
      String value = node == null ? null : node.getValue();
      if (value != null)
      {
         value.trim();
         if (value.length() == 0)
         {
            value = null;
         }
      }
      return value;
   }
}
