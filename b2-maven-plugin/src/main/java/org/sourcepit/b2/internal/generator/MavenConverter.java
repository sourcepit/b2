/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.interpolation.ValueSource;
import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.model.builder.util.AbstractConverter;
import org.sourcepit.tools.shared.resources.harness.ValueSourceUtils;

public class MavenConverter extends AbstractConverter
{
   private final PropertiesMap properties;
   private final MavenSession mavenSession;
   private final MavenProject project;

   public MavenConverter(MavenSession mavenSession, MavenProject project)
   {
      final LinkedPropertiesMap propertiesMap = new LinkedPropertiesMap();
      propertiesMap.setDefaultProperties(loadConverterProperties());
      propertiesMap.putMap(project.getProperties());
      this.properties = propertiesMap;
      this.mavenSession = mavenSession;
      this.project = project;
   }

   @Override
   public String getModuleId(File moduleDir)
   {
      return project.getGroupId() + "." + project.getArtifactId();
   }

   public String getModuleVersion()
   {
      return VersionUtils.toBundleVersion(project.getVersion());
   }

   @Override
   protected PropertiesMap getPropertiesMap()
   {
      return properties;
   }

   public String getNameSpace()
   {
      return project.getGroupId();
   }

   @Override
   protected void addValueSources(List<ValueSource> valueSources)
   {
      super.addValueSources(valueSources);
      valueSources.add(ValueSourceUtils.newPropertyValueSource(mavenSession.getUserProperties()));
      valueSources.add(ValueSourceUtils.newPropertyValueSource(mavenSession.getSystemProperties()));

      final List<String> prefixes = new ArrayList<String>();
      prefixes.add("pom");
      prefixes.add("project");
      valueSources.add(ValueSourceUtils.newPrefixedValueSource(prefixes, project));
      valueSources.add(ValueSourceUtils.newPrefixedValueSource("session", mavenSession));

      final Settings settings = mavenSession.getSettings();
      if (settings != null)
      {
         valueSources.add(ValueSourceUtils.newPrefixedValueSource("settings", settings));
         valueSources.add(ValueSourceUtils.newSingleValueSource("localRepository", settings.getLocalRepository()));
      }
   }
}
