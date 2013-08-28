/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.util;

import java.nio.channels.IllegalSelectorException;

import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.util.Identifiable;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;

public final class ReferenceUtils
{
   private ReferenceUtils()
   {
      super();
   }

   public static boolean isSatisfiableBy(AbstractReference reference, Identifiable identifiable)
   {
      if (identifiable == null)
      {
         throw new IllegalArgumentException("identifiable may not be null");
      }
      if (!isIdMatch(reference, identifiable))
      {
         return false;
      }
      if (!isVersionMatch(reference, identifiable))
      {
         return false;
      }
      return true;
   }

   private static boolean isVersionMatch(AbstractReference reference, Identifiable identifiable)
   {
      final VersionMatchRule rule;
      final boolean isStrict;

      if (reference instanceof AbstractStrictReference)
      {
         isStrict = true;
         rule = null;
      }
      else if (reference instanceof RuledReference)
      {
         isStrict = false;
         rule = ((RuledReference) reference).getVersionMatchRule();
      }
      else
      {
         throw new IllegalStateException("Cannot determine version match mode for reference " + reference);
      }

      if (isStrict)
      {
         return isStrictVersionMatch(reference.getVersion(), identifiable.getVersion());
      }
      else if (rule != null)
      {
         return isRuledVersionMatch(rule, reference.getVersion(), identifiable.getVersion());
      }

      throw new IllegalStateException();
   }

   private static boolean isIdMatch(AbstractReference reference, Identifiable identifiable)
   {
      return reference.getId().equals(identifiable.getId());
   }

   private static boolean isRuledVersionMatch(VersionMatchRule rule, String refVersion, String idenVersion)
   {
      if (refVersion == null)
      {
         return true;
      }
      return toVersionRange(refVersion, rule).isIncluded(new Version(idenVersion));
   }

   public static VersionRange toVersionRange(String version, VersionMatchRule rule)
   {
      // perfect : [1.0.0,1.0.0]
      // equivalent : [1.0.0, 1.1.0)
      // compatible : [1.0.0, 2.0.0)
      // greaterOrEqual : 1.0.0

      final Version v = Version.parseVersion(version);
      final int major = v.getMajor();
      final int minor = v.getMinor();
      final int micro = v.getMicro();

      final String versionRange;
      switch (rule)
      {
         case PERFECT :
            versionRange = "[" + v + "," + v + "]";
            break;
         case EQUIVALENT :
            versionRange = "[" + major + "." + minor + "." + micro + "," + major + "." + (minor + 1) + "." + 0 + ")";
            break;
         case COMPATIBLE :
            versionRange = "[" + major + "." + minor + "." + micro + "," + (major + 1) + "." + 0 + "." + 0 + ")";
            break;
         case GREATER_OR_EQUAL :
            versionRange = major + "." + minor + "." + micro;
            break;
         default :
            throw new IllegalSelectorException();
      }

      return new VersionRange(versionRange);
   }

   private static boolean isStrictVersionMatch(String refVersion, String identVersion)
   {
      if (isDefaultVersion(refVersion))
      {
         return true;
      }
      return refVersion.equals(identVersion);
   }

   private static boolean isDefaultVersion(String versionRange)
   {
      return versionRange == null || "0.0.0".equals(versionRange) || versionRange.startsWith("0.0.0.");
   }
}
