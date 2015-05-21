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

package org.sourcepit.b2.model.module.internal.util;

import java.nio.channels.IllegalSelectorException;

import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.b2.model.module.util.Identifiable;

public final class ReferenceUtils {
   private ReferenceUtils() {
      super();
   }

   public static boolean isSatisfiableBy(AbstractReference reference, Identifiable identifiable) {
      if (identifiable == null) {
         throw new IllegalArgumentException("identifiable may not be null");
      }
      if (!isIdMatch(reference, identifiable)) {
         return false;
      }
      if (!isVersionMatch(reference, identifiable)) {
         return false;
      }
      return true;
   }

   private static boolean isVersionMatch(AbstractReference reference, Identifiable identifiable) {
      final VersionMatchRule rule;
      final boolean isStrict;

      if (reference instanceof AbstractStrictReference) {
         isStrict = true;
         rule = null;
      }
      else if (reference instanceof RuledReference) {
         isStrict = false;
         rule = ((RuledReference) reference).getVersionMatchRule();
      }
      else {
         throw new IllegalStateException("Cannot determine version match mode for reference " + reference);
      }

      if (isStrict) {
         return isStrictVersionMatch(reference.getVersion(), identifiable.getVersion());
      }
      else if (rule != null) {
         return isRuledVersionMatch(rule, reference.getVersion(), identifiable.getVersion());
      }

      throw new IllegalStateException();
   }

   private static boolean isIdMatch(AbstractReference reference, Identifiable identifiable) {
      return reference.getId().equals(identifiable.getId());
   }

   private static boolean isRuledVersionMatch(VersionMatchRule rule, String refVersion, String idenVersion) {
      if (refVersion == null) {
         return true;
      }
      return toVersionRange(refVersion, rule).isIncluded(new Version(idenVersion));
   }

   public static VersionRange toVersionRange(String version, VersionMatchRule rule) {
      // perfect : [1.0.0,1.0.0]
      // equivalent : [1.0.0, 1.1.0)
      // compatible : [1.0.0, 2.0.0)
      // greaterOrEqual : 1.0.0

      final Version v = Version.parseVersion(version);
      final int major = v.getMajor();
      final int minor = v.getMinor();
      final int micro = v.getMicro();

      final String versionRange;
      switch (rule) {
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

   private static boolean isStrictVersionMatch(String refVersion, String identVersion) {
      if (isDefaultVersion(refVersion)) {
         return true;
      }
      return refVersion.equals(identVersion);
   }

   private static boolean isDefaultVersion(String versionRange) {
      return versionRange == null || "0.0.0".equals(versionRange) || versionRange.startsWith("0.0.0.");
   }
}
