/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.module.internal.util;

import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;
import org.sourcepit.b2.model.module.Reference;
import org.sourcepit.b2.model.module.util.Identifier;

public class ReferenceUtils
{
   public static boolean isSatisfiableBy(Reference reference, Identifier identifier)
   {
      return isSatisfiableBy(reference.getId(), reference.getVersionRange(), identifier);
   }

   public static boolean isSatisfiableBy(String id, String versionRange, Identifier identifier)
   {
      if (id == null)
      {
         throw new IllegalArgumentException("Id must not be null.");
      }

      if (id.equals(identifier.getId()))
      {
         if (isDefaultVersion(versionRange))
         {
            return true;
         }
         else
         {
            return new VersionRange(versionRange).isIncluded(Version.parseVersion(identifier.getVersion()));
         }
      }
      return false;
   }

   private static boolean isDefaultVersion(String versionRange)
   {
      return versionRange == null || "0.0.0".equals(versionRange) || versionRange.startsWith("0.0.0.");
   }

   public static void setStrictVersion(Reference reference, String version)
   {
      if (version.startsWith("[") || version.startsWith("("))
      {
         throw new IllegalArgumentException("Version format belongs to a version range: " + version);
      }
      final StringBuilder sb = new StringBuilder();
      sb.append('[');
      sb.append(version);
      sb.append(',');
      sb.append(version);
      sb.append(']');
      reference.setVersionRange(sb.toString());
   }
}
