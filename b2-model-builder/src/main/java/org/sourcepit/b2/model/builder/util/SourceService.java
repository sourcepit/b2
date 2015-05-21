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

package org.sourcepit.b2.model.builder.util;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
@Singleton
public class SourceService implements ISourceService {
   public boolean isSourceBuildEnabled(PluginProject pluginProject, PropertiesSource buildProperties) {
      return isSourceBuildEnabled(buildProperties) && hasSource(pluginProject);
   }

   public boolean isSourceBuildEnabled(PropertiesSource buildProperties) {
      return Boolean.valueOf(buildProperties.get("build.sources", "true")).booleanValue();
   }

   public boolean hasSource(PluginProject pluginProject) {
      return pluginProject.getAnnotationData("java", "source.paths") != null;
   }
}
