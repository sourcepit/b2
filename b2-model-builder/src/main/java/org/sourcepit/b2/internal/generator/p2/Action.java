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

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map.Entry;

import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class Action {
   public static Action parse(String action) {
      final Action result = new Action();

      final int idx = action.indexOf('(');
      if (idx > -1) {
         result.setName(action.substring(0, idx).trim());

         final String params = action.substring(idx + 1, action.length() - 1);
         for (String param : params.split(",")) {
            final String[] split = param.split(":");
            checkArgument(split.length == 2, "Invalid action parameter '%s'", param);
            result.getParameters().put(split[0].trim(), decode(split[1].trim()));
         }
      }
      else {
         result.setName(action);
      }

      return result;
   }

   private static String decode(String string) {
      return string.replace("${#36}", "$")
         .replace("${#44}", ",")
         .replace("${#58}", ":")
         .replace("${#59}", ";")
         .replace("${#123}", "{")
         .replace("${#125}", "$");
   }

   private static String encode(String string) {
      // $ = ${#36}
      // , = ${#44}
      // : = ${#58}
      // ; = ${#59}
      // { = ${#123}
      // } = ${#125}

      final StringBuilder sb = new StringBuilder();
      for (char c : string.toCharArray()) {
         switch (c) {
            case '$' :
               sb.append("${#36}");
               break;
            case ',' :
               sb.append("${#44}");
               break;
            case ':' :
               sb.append("${#58}");
               break;
            case ';' :
               sb.append("${#59}");
               break;
            case '{' :
               sb.append("${#123}");
               break;
            case '}' :
               sb.append("${#125}");
               break;
            default :
               sb.append(c);
               break;
         }
      }
      return sb.toString();
   }

   private String name;

   private PropertiesMap parameters = new LinkedPropertiesMap();

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public PropertiesMap getParameters() {
      return parameters;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append(name);
      sb.append('(');
      for (Entry<String, String> entry : parameters.entrySet()) {
         sb.append(entry.getKey());
         sb.append(':');
         sb.append(encode(entry.getValue()));
         sb.append(',');
      }
      if (!parameters.isEmpty()) {
         sb.deleteCharAt(sb.length() - 1);
      }
      sb.append(')');
      return sb.toString();
   }
}
