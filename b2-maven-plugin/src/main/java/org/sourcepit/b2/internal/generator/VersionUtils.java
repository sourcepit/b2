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

package org.sourcepit.b2.internal.generator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sourcepit.common.manifest.osgi.Version;

import com.google.common.base.Strings;

public final class VersionUtils
{
   private static final String SUFFIX_QUALIFIER = ".qualifier";

   private static final String SUFFIX_SNAPSHOT = "-SNAPSHOT";

   private static final String SNAPSHOT_VERSION = "SNAPSHOT";

   private VersionUtils()
   {
      super();
   }

   public static String toBundleVersion(String version)
   {
      if (version == null)
      {
         return null;
      }
      return cleanupVersion(replaceSnapshotQualifier(version));
   }

   private static String replaceSnapshotQualifier(String version)
   {
      if (version.regionMatches(true, version.length() - SNAPSHOT_VERSION.length(), SNAPSHOT_VERSION, 0,
         SNAPSHOT_VERSION.length()))
      {
         return version.substring(0, version.length() - SNAPSHOT_VERSION.length() - 1) + SUFFIX_QUALIFIER;
      }
      return version;
   }

   public static String toTychoVersion(String version)
   {
      if (version == null)
      {
         return null;
      }

      if (version.endsWith(SUFFIX_QUALIFIER))
      {
         return version.substring(0, version.length() - SUFFIX_QUALIFIER.length()) + SUFFIX_SNAPSHOT;
      }

      return version;
   }

   public static String toMavenVersion(String version)
   {
      if (version == null)
      {
         return null;
      }

      if (version.endsWith(SUFFIX_QUALIFIER))
      {
         return version.substring(0, version.length() - SUFFIX_QUALIFIER.length()) + SUFFIX_SNAPSHOT;
      }

      final Version v = Version.parse(version);
      if (!Strings.isNullOrEmpty(v.getQualifier()))
      {
         StringBuilder sb = new StringBuilder();
         sb.append(v.getMajor());
         sb.append('.');
         sb.append(v.getMinor());
         sb.append('.');
         sb.append(v.getMicro());
         sb.append('-');
         sb.append(v.getQualifier());
         return sb.toString();
      }

      return version;
   }

   /**
    * Clean up version parameters. Other builders use more fuzzy definitions of the version syntax. This method cleans
    * up such a version to match an OSGi version.
    * 
    * @param VERSION_STRING
    * @return
    */
   private static final Pattern FUZZY_VERSION = Pattern.compile("(\\d+)(\\.(\\d+)(\\.(\\d+))?)?([^a-zA-Z0-9](.*))?",
      Pattern.DOTALL);

   private static String cleanupVersion(String version)
   {
      StringBuffer result = new StringBuffer();
      Matcher m = FUZZY_VERSION.matcher(version);
      if (m.matches())
      {
         String major = m.group(1);
         String minor = m.group(3);
         String micro = m.group(5);
         String qualifier = m.group(7);

         if (major != null)
         {
            result.append(major);
            if (minor != null)
            {
               result.append(".");
               result.append(minor);
               if (micro != null)
               {
                  result.append(".");
                  result.append(micro);
                  if (qualifier != null)
                  {
                     result.append(".");
                     cleanupModifier(result, qualifier);
                  }
               }
               else if (qualifier != null)
               {
                  result.append(".0.");
                  cleanupModifier(result, qualifier);
               }
               else
               {
                  result.append(".0");
               }
            }
            else if (qualifier != null)
            {
               result.append(".0.0.");
               cleanupModifier(result, qualifier);
            }
            else
            {
               result.append(".0.0");
            }
         }
      }
      else
      {
         result.append("0.0.0.");
         cleanupModifier(result, version);
      }
      return result.toString();
   }

   private static void cleanupModifier(StringBuffer result, String modifier)
   {
      for (int i = 0; i < modifier.length(); i++)
      {
         char c = modifier.charAt(i);
         if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '-')
         {
            result.append(c);
         }
         else
         {
            result.append('_');
         }
      }
   }
}
