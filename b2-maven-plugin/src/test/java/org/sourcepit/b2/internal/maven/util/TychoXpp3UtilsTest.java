/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.sourcepit.b2.internal.maven.util.TychoXpp3Utils.addExtraRequirements;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Test;
import org.sourcepit.common.maven.util.Xpp3Utils;

public class TychoXpp3UtilsTest
{

   @Test
   public void testAddExtraRequirements()
   {
      Plugin plugin = new Plugin();

      // test empty requirements
      List<Dependency> requirements = new ArrayList<Dependency>();
      addExtraRequirements(plugin, requirements);
      assertNull(plugin.getConfiguration());

      // test simple requirement
      requirements.add(createRequirement("org.sourcepit.foo", "1.0.0", "eclipse-plugin"));
      addExtraRequirements(plugin, requirements);

      Xpp3Dom requirementsNode = getRequirementsNode(plugin);
      assertEquals(1, requirementsNode.getChildCount());
      Xpp3Dom requirementNode = requirementsNode.getChild(0);
      assertEquals("requirement", requirementNode.getName());

      Dependency requirement = TychoXpp3Utils.toRequirement(requirementNode);
      assertEquals("org.sourcepit.foo", requirement.getArtifactId());
      assertEquals("1.0.0", requirement.getVersion());
      assertEquals("eclipse-plugin", requirement.getType());

      // test equal requirements
      requirements.add(createRequirement("org.sourcepit.foo", "1.0.0", "eclipse-plugin"));
      addExtraRequirements(plugin, requirements);

      requirementsNode = getRequirementsNode(plugin);
      assertEquals(1, requirementsNode.getChildCount());
      requirementNode = requirementsNode.getChild(0);
      assertEquals("requirement", requirementNode.getName());

      requirement = TychoXpp3Utils.toRequirement(requirementNode);
      assertEquals("org.sourcepit.foo", requirement.getArtifactId());
      assertEquals("1.0.0", requirement.getVersion());
      assertEquals("eclipse-plugin", requirement.getType());

      // test requirement with conflicting version. expected existing survives
      requirements.clear();
      requirements.add(createRequirement("org.sourcepit.foo", "2.0.0", "eclipse-plugin"));
      addExtraRequirements(plugin, requirements);

      requirementsNode = getRequirementsNode(plugin);
      assertEquals(1, requirementsNode.getChildCount());
      requirementNode = requirementsNode.getChild(0);
      assertEquals("requirement", requirementNode.getName());

      requirement = TychoXpp3Utils.toRequirement(requirementNode);
      assertEquals("org.sourcepit.foo", requirement.getArtifactId());
      assertEquals("1.0.0", requirement.getVersion());
      assertEquals("eclipse-plugin", requirement.getType());

      requirements.clear();
      requirements.add(createRequirement("org.sourcepit.foo", "1.0.0", "eclipse-feature"));
      addExtraRequirements(plugin, requirements);

      requirementsNode = getRequirementsNode(plugin);
      assertEquals(2, requirementsNode.getChildCount());

      requirementNode = requirementsNode.getChild(0);
      assertEquals("requirement", requirementNode.getName());

      requirement = TychoXpp3Utils.toRequirement(requirementNode);
      assertEquals("org.sourcepit.foo", requirement.getArtifactId());
      assertEquals("1.0.0", requirement.getVersion());
      assertEquals("eclipse-plugin", requirement.getType());

      requirementNode = requirementsNode.getChild(1);
      assertEquals("requirement", requirementNode.getName());

      requirement = TychoXpp3Utils.toRequirement(requirementNode);
      assertEquals("org.sourcepit.foo", requirement.getArtifactId());
      assertEquals("1.0.0", requirement.getVersion());
      assertEquals("eclipse-feature", requirement.getType());
   }

   private static Xpp3Dom getRequirementsNode(Plugin plugin)
   {
      Xpp3Dom configuration = (Xpp3Dom) plugin.getConfiguration();
      assertNotNull(configuration);
      assertEquals(1, configuration.getChildCount());
      Xpp3Dom resolutionNode = configuration.getChild(0);
      assertEquals("dependency-resolution", resolutionNode.getName());
      assertEquals(1, resolutionNode.getChildCount());
      Xpp3Dom requirementsNode = resolutionNode.getChild(0);
      assertEquals("extraRequirements", requirementsNode.getName());
      return requirementsNode;
   }

   private static Dependency createRequirement(String id, String version, String type)
   {
      final Dependency dependency = new Dependency();
      dependency.setArtifactId(id);
      dependency.setVersion(version);
      dependency.setType(type);
      return dependency;
   }

   @Test
   public void testToRequirement()
   {
      Xpp3Dom requirmentNode = new Xpp3Dom("requirement");
      Dependency requirement = TychoXpp3Utils.toRequirement(requirmentNode);
      assertNotNull(requirement);
      assertNull(requirement.getGroupId());
      assertNull(requirement.getArtifactId());
      assertNull(requirement.getVersion());

      Xpp3Utils.addValueNode(requirmentNode, "id", "org.sourcepit.foo");
      Xpp3Utils.addValueNode(requirmentNode, "versionRange", "1.0.0");
      Xpp3Utils.addValueNode(requirmentNode, "type", "eclipse-feature");

      requirement = TychoXpp3Utils.toRequirement(requirmentNode);
      assertNotNull(requirement);
      assertNull(requirement.getGroupId());
      assertEquals("org.sourcepit.foo", requirement.getArtifactId());
      assertEquals("1.0.0", requirement.getVersion());
      assertEquals("eclipse-feature", requirement.getType());
   }
}
