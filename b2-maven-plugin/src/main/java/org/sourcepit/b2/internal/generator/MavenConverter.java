/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.interpolation.ValueSource;
import org.sourcepit.b2.model.builder.util.AbstractConverter;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.tools.shared.resources.harness.ValueSourceUtils;

public class MavenConverter extends AbstractConverter
{
   private final PropertiesMap properties;
   private final MavenSession mavenSession;
   private final MavenProject project;

   public MavenConverter(MavenSession mavenSession, MavenProject project)
   {
      // see org.eclipse.tycho.core.resolver.DefaultTychoDependencyResolver.setupProject(MavenSession, MavenProject,
      // ReactorProject)
      // Properties properties = new Properties();
      // properties.putAll(project.getProperties());
      // properties.putAll(session.getSystemProperties()); // session wins
      // properties.putAll(session.getUserProperties());

      final LinkedPropertiesMap propertiesMap = new LinkedPropertiesMap();
      propertiesMap.setDefaultProperties(loadConverterProperties());
      propertiesMap.putMap(project.getProperties());
      propertiesMap.putMap(mavenSession.getSystemProperties());
      propertiesMap.putMap(mavenSession.getUserProperties());
      this.properties = propertiesMap;
      this.mavenSession = mavenSession;
      this.project = project;
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
