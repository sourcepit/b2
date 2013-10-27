/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.sourcepit.b2.internal.generator.TargetPlatformRequirementsCollectorTest.assertRequirement;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_GROUP_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_TPC_PLUGIN_ARTIFACT_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYPE_ECLIPSE_FEATURE;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYPE_ECLIPSE_PLUGIN;
import static org.sourcepit.b2.internal.maven.util.TychoXpp3Utils.readRequirements;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getConfiguration;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Test;

public class TargetPlatformRequirementsAppenderTest
{
   private TargetPlatformRequirementsAppender requirementsAppender;

   @Before
   public void setUp()
   {
      requirementsAppender = new TargetPlatformRequirementsAppender();
   }

   @Test
   public void testNullAndEmpty()
   {
      try
      {
         requirementsAppender.append(null, null);
         fail();
      }
      catch (NullPointerException e)
      { // expected
      }

      try
      {
         requirementsAppender.append(new Model(), null);
         fail();
      }
      catch (NullPointerException e)
      { // expected
      }

      try
      {
         requirementsAppender.append(null, new ArrayList<Dependency>());
         fail();
      }
      catch (NullPointerException e)
      { // expected
      }


      Model model = new Model();
      List<Dependency> requirements = new ArrayList<Dependency>();

      requirementsAppender.append(model, requirements);

      Plugin plugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      assertNull(plugin);

      plugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, true);
      assertNotNull(plugin);

      requirementsAppender.append(model, requirements);

      assertSame(plugin, getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false));
   }

   @Test
   public void testAppendToEmpty() throws Exception
   {
      Model model = new Model();
      List<Dependency> requirements = new ArrayList<Dependency>();

      Dependency requirement;
      requirement = new Dependency();
      requirement.setArtifactId("foo.plugin");
      requirement.setVersion("1.0.0");
      requirement.setType(TYPE_ECLIPSE_PLUGIN);
      requirements.add(requirement);

      requirement = new Dependency();
      requirement.setArtifactId("foo.feature");
      requirement.setVersion("1.0.0");
      requirement.setType(TYPE_ECLIPSE_FEATURE);
      requirements.add(requirement);

      requirementsAppender.append(model, requirements);

      Plugin plugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      assertNotNull(plugin);

      Xpp3Dom configuration = getConfiguration(plugin, false);
      assertNotNull(configuration);

      List<Dependency> actualRequirements = readRequirements(configuration.getChild("dependency-resolution").getChild(
         "extraRequirements"));
      assertEquals(2, actualRequirements.size());

      assertRequirement("foo.plugin", "1.0.0", TYPE_ECLIPSE_PLUGIN, actualRequirements.get(0));
      assertRequirement("foo.feature", "1.0.0", TYPE_ECLIPSE_FEATURE, actualRequirements.get(1));
   }

   @Test
   public void testAppendMerge() throws Exception
   {
      Model model = new Model();
      List<Dependency> requirements = new ArrayList<Dependency>();

      Dependency requirement;
      requirement = new Dependency();
      requirement.setArtifactId("foo.plugin");
      requirement.setVersion("1.0.0");
      requirement.setType(TYPE_ECLIPSE_PLUGIN);
      requirements.add(requirement);

      requirement = new Dependency();
      requirement.setArtifactId("foo.feature");
      requirement.setVersion("1.0.0");
      requirement.setType(TYPE_ECLIPSE_FEATURE);
      requirements.add(requirement);

      requirementsAppender.append(model, requirements);

      Plugin plugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      assertNotNull(plugin);

      Xpp3Dom configuration = getConfiguration(plugin, false);
      assertNotNull(configuration);

      Xpp3Dom requirementsNode = configuration.getChild("dependency-resolution").getChild("extraRequirements");

      List<Dependency> actualRequirements = readRequirements(requirementsNode);
      assertEquals(2, actualRequirements.size());

      requirements = new ArrayList<Dependency>();

      requirement = new Dependency();
      requirement.setArtifactId("foo.plugin");
      requirement.setVersion("2.0.0");
      requirement.setType(TYPE_ECLIPSE_PLUGIN);
      requirements.add(requirement);

      requirement = new Dependency();
      requirement.setArtifactId("foo.sources.feature");
      requirement.setVersion("1.0.0");
      requirement.setType(TYPE_ECLIPSE_FEATURE);
      requirements.add(requirement);

      requirementsAppender.append(model, requirements);

      actualRequirements = readRequirements(requirementsNode);
      assertEquals(3, actualRequirements.size());

      assertRequirement("foo.plugin", "1.0.0", TYPE_ECLIPSE_PLUGIN, actualRequirements.get(0)); // existing wins
      assertRequirement("foo.feature", "1.0.0", TYPE_ECLIPSE_FEATURE, actualRequirements.get(1)); // keep existing
      assertRequirement("foo.sources.feature", "1.0.0", TYPE_ECLIPSE_FEATURE, actualRequirements.get(2)); // add new
   }

}
