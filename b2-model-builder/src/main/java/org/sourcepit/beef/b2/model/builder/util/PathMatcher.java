/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.util;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public final class PathMatcher
{
   private final Set<String> includes;
   private final Set<String> excludes;

   public PathMatcher(Set<String> includes, Set<String> excludes)
   {
      this.includes = includes;
      this.excludes = excludes;
   }

   public static PathMatcher parsePackagePatterns(String patterns)
   {
      return parse(patterns, ".", ",");
   }

   public static PathMatcher parse(String patterns, String separator, String pathSeparator)
   {
      final Set<String> includes = new HashSet<String>();
      final Set<String> excludes = new HashSet<String>();
      if (patterns.length() != 0)
      {
         final String[] rawPatterns = patterns.split(pathSeparator);
         for (String rawPattern : rawPatterns)
         {
            rawPattern = rawPattern.trim();
            final boolean isExclude = rawPattern.startsWith("!");
            if (isExclude)
            {
               rawPattern = rawPattern.length() > 1 ? rawPattern.substring(1) : "";
            }
            final Set<String> target = isExclude ? excludes : includes;
            target.add(toPathRegex(rawPattern, separator));
         }
      }
      return new PathMatcher(includes, excludes);
   }

   public Set<String> getExcludes()
   {
      return excludes;
   }

   public Set<String> getIncludes()
   {
      return includes;
   }

   public boolean isInclude(String path)
   {
      return isPatternMatch(includes, path, true);
   }

   public boolean isExclude(String path)
   {
      return isPatternMatch(excludes, path, false);
   }

   public boolean isMatch(String path)
   {
      return isInclude(path) && !isExclude(path);
   }

   private boolean isPatternMatch(Set<String> patterns, String path, boolean defaultValue)
   {
      if (patterns != null && !patterns.isEmpty())
      {
         for (String exclude : patterns)
         {
            if (path.matches(exclude))
            {
               return true;
            }
         }
         return false;
      }
      return defaultValue;
   }

   public static String toPathRegex(String pattern, String separator)
   {
      final String segmentPattern = "[^" + escRegEx(separator) + "]*";
      final int segmentPatternLength = segmentPattern.length();
      final StringTokenizer t = new StringTokenizer(pattern, "\\*", true);
      final StringBuilder sb = new StringBuilder();
      boolean skip = false;
      while (t.hasMoreTokens())
      {
         String token = t.nextToken();
         if ("*".equals(token))
         {
            if (skip)
            {
               sb.delete(sb.length() - segmentPatternLength, sb.length());
               sb.append(".*");
            }
            else
            {
               sb.append(segmentPattern);
            }
            skip = true;
         }
         else
         {
            sb.append(escRegEx(token));
            skip = false;
         }
      }
      return sb.toString();
   }

   public static String escRegEx(String inStr)
   {
      // will replace the char with \char
      return inStr.replaceAll("([\\\\*+\\[\\](){}\\$.?\\^|])", "\\\\$1");
   }
}