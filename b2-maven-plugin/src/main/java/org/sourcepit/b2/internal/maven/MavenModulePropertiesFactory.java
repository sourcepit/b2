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

package org.sourcepit.b2.internal.maven;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.interpolation.ValueSource;
import org.sourcepit.b2.internal.generator.VersionUtils;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.common.utils.props.AbstractPropertiesSource;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.tools.shared.resources.harness.ValueSourceUtils;

@Named
public class MavenModulePropertiesFactory {
   public PropertiesSource createModuleProperties(MavenSession mavenSession, final MavenProject project) {
      final PropertiesMap propertiesMap = B2ModelBuildingRequest.newDefaultProperties();

      final String mavenVersion = project.getVersion();
      final String osgiVersion = VersionUtils.toBundleVersion(mavenVersion);

      propertiesMap.put("b2.moduleVersion", osgiVersion);
      propertiesMap.put("b2.moduleNameSpace", project.getGroupId());

      final String tychoVersion = VersionUtils.toTychoVersion(osgiVersion);
      propertiesMap.put("module.version", mavenVersion);
      propertiesMap.put("module.mavenVersion", mavenVersion);
      propertiesMap.put("module.osgiVersion", osgiVersion);
      propertiesMap.put("module.tychoVersion", tychoVersion);

      propertiesMap.putMap(project.getProperties());
      propertiesMap.putMap(mavenSession.getSystemProperties());
      propertiesMap.putMap(mavenSession.getUserProperties());

      final List<ValueSource> valueSources = new ArrayList<ValueSource>();

      final List<String> prefixes = new ArrayList<String>();
      prefixes.add("pom");
      prefixes.add("project");
      valueSources.add(ValueSourceUtils.newPrefixedValueSource(prefixes, project));
      valueSources.add(ValueSourceUtils.newPrefixedValueSource("session", mavenSession));

      final Settings settings = mavenSession.getSettings();
      if (settings != null) {
         valueSources.add(ValueSourceUtils.newPrefixedValueSource("settings", settings));
         valueSources.add(ValueSourceUtils.newSingleValueSource("localRepository", settings.getLocalRepository()));
      }

      return new AbstractPropertiesSource() {
         public String get(String key) {
            if ("module.id".equals(key) && !isPropertyDefinedOnProject(key)) {
               return null;
            }
            final String value = propertiesMap.get(key);
            if (value != null) {
               return value;
            }
            for (ValueSource valueSource : valueSources) {
               final Object oValue = valueSource.getValue(key);
               if (oValue != null) {
                  return oValue.toString();
               }
            }
            return null;
         }

         private boolean isPropertyDefinedOnProject(String key) {
            return project.getOriginalModel().getProperties().containsKey(key);
         }
      };
   }
}
