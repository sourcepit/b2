/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.model.Dependency;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import com.google.common.base.Strings;

public final class MavenModelUtils
{
   private MavenModelUtils()
   {
      super();
   }
   
   public static void addAllUnique(List<Dependency> dest, List<Dependency> src)
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

   public static void clearChildren(Xpp3Dom parent)
   {
      for (int i = parent.getChildCount() - 1; i >= 0; i--)
      {
         parent.removeChild(i);
      }
   }

   public static Xpp3Dom newDependencyNode(Dependency dependency)
   {
      final Xpp3Dom dependencyNode = new Xpp3Dom("dependency");

      String value = dependency.getGroupId();
      if (!Strings.isNullOrEmpty(value))
      {
         appendNode(dependencyNode, "groupId", value);
      }

      value = dependency.getArtifactId();
      if (!Strings.isNullOrEmpty(value))
      {
         appendNode(dependencyNode, "artifactId", value);
      }

      value = dependency.getVersion();
      if (!Strings.isNullOrEmpty(value))
      {
         appendNode(dependencyNode, "version", value);
      }

      value = dependency.getType();
      if (!Strings.isNullOrEmpty(value) && !"jar".equals(value))
      {
         appendNode(dependencyNode, "type", value);
      }

      value = dependency.getClassifier();
      if (!Strings.isNullOrEmpty(value))
      {
         appendNode(dependencyNode, "classifier", value);
      }

      value = dependency.getScope();
      if (!Strings.isNullOrEmpty(value))
      {
         appendNode(dependencyNode, "scope", value);
      }

      value = dependency.getSystemPath();
      if (!Strings.isNullOrEmpty(value))
      {
         appendNode(dependencyNode, "systemPath", value);
      }

      value = dependency.getOptional();
      if (!Strings.isNullOrEmpty(value))
      {
         appendNode(dependencyNode, "optional", value);
      }

      return dependencyNode;
   }

   public static void appendNode(Xpp3Dom parent, String name, String value)
   {
      final Xpp3Dom node = new Xpp3Dom(name);
      node.setValue(value);
      parent.addChild(node);
   }
}
