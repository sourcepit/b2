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

package org.sourcepit.b2.internal.generator.p2;

import java.util.Map;

public class Require {
   // requires.{#}.namespace = {namespace}
   // requires.{#}.name = {name}
   // requires.{#}.range = {range} (optional / default: 0.0.0)]
   // requires.{#}.greedy = {true|false} (optional / default: true)
   // requires.{#}.optional = {true|false} (optional / default: false)
   // requires.{#}.multiple = {true|false} (optional / default: false)

   private String namespace;
   private String name;
   private String range;
   private boolean greedy = true;
   private boolean optional;
   private boolean multiple;

   public String getNamespace() {
      return namespace;
   }

   public void setNamespace(String namespace) {
      this.namespace = namespace;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getRange() {
      return range;
   }

   public void setRange(String range) {
      this.range = range;
   }

   public boolean isGreedy() {
      return greedy;
   }

   public void setGreedy(boolean greedy) {
      this.greedy = greedy;
   }

   public boolean isOptional() {
      return optional;
   }

   public void setOptional(boolean optional) {
      this.optional = optional;
   }

   public boolean isMultiple() {
      return multiple;
   }

   public void setMultiple(boolean multiple) {
      this.multiple = multiple;
   }

   public void put(Map<String, String> map, int index) {
      final String prefix = "requires." + index;

      map.put(prefix + ".namespace", getNamespace());
      map.put(prefix + ".name", getName());

      final String range = getRange();
      if (range != null) {
         map.put(prefix + ".range", range);
      }

      if (!isGreedy()) {
         map.put(prefix + ".greedy", "false");
      }

      if (isMultiple()) {
         map.put(prefix + ".multiple", "true");
      }

      if (isOptional()) {
         map.put(prefix + ".optional", "true");
      }
   }
}
