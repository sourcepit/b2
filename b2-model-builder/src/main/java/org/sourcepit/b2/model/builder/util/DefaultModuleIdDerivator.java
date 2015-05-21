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

import static com.google.common.base.Strings.isNullOrEmpty;

import javax.inject.Named;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class DefaultModuleIdDerivator implements ModuleIdDerivator {
   public String deriveModuleId(AbstractModule module, PropertiesSource properties) {
      final String moduleId = properties.get("module.id");
      if (!isNullOrEmpty(moduleId)) {
         return moduleId;
      }

      final String groupId = properties.get("project.groupId");
      if (!isNullOrEmpty(groupId)) {
         final String artifactId = properties.get("project.artifactId");
         if (!isNullOrEmpty(artifactId)) {
            return beautify(resolveSymbolicName(groupId, artifactId));
         }
      }
      return null;
   }

   private String beautify(String symbolicName) {
      if (symbolicName == null) {
         return null;
      }
      final StringBuilder sb = new StringBuilder();
      for (char c : symbolicName.toCharArray()) {
         switch (c) {
            case '-' :
            case '_' :
               sb.append('.');
               break;
            default :
               sb.append(c);
               break;
         }
      }
      return sb.toString();
   }

   private String resolveSymbolicName(String groupId, String artifactId) {
      final String[] segments = groupId.split("\\.");
      final StringBuilder sb = new StringBuilder();
      sb.append(groupId);

      String idPrefix = groupId;
      if (!artifactId.startsWith(idPrefix)) {
         idPrefix = segments[segments.length - 1];
      }

      if (artifactId.startsWith(idPrefix)) {
         final String appendix = artifactId.substring(idPrefix.length());
         boolean trim = true;
         for (char c : appendix.toCharArray()) {
            switch (c) {
               case '-' :
               case '_' :
               case '.' :
                  if (trim) {
                     break;
                  }
               default :
                  if (trim) {
                     sb.append('.');
                  }
                  trim = false;
                  sb.append(c);
                  break;
            }
         }
      }
      else {
         sb.append('.');
         sb.append(artifactId);
      }
      return sb.toString();
   }
}
