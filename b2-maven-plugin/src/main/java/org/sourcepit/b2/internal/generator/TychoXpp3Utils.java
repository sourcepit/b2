/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.common.maven.util.Xpp3Utils.addValueNode;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.maven.model.Dependency;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import com.google.common.base.Strings;

public final class TychoXpp3Utils
{
   private TychoXpp3Utils()
   {
      super();
   }

   public static List<Dependency> readRequirements(Xpp3Dom requirements)
   {
      final List<Dependency> dependencyList = new ArrayList<Dependency>();
      for (Xpp3Dom requirement : requirements.getChildren("requirement"))
      {
         dependencyList.add(newRequirement(requirement));
      }
      return dependencyList;
   }

   public static Dependency newRequirement(Xpp3Dom requirement)
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
