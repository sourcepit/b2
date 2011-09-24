/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.util.ArtifactVersion;
import org.sourcepit.beef.b2.model.session.util.DefaultArtifactVersion;
import org.sourcepit.beef.b2.model.session.util.InvalidVersionSpecificationException;
import org.sourcepit.beef.b2.model.session.util.VersionRange;

public class CModuleDependencyImpl extends ModuleDependencyImpl
{
   @Override
   public boolean isSatisfiableBy(ModuleProject moduleProject)
   {
      if (equals(getGroupId(), moduleProject.getGroupId()))
      {
         if (equals(getArtifactId(), moduleProject.getArtifactId()))
         {
            try
            {
               ArtifactVersion version = new DefaultArtifactVersion(moduleProject.getVersion());
               VersionRange range = VersionRange.createFromVersionSpec(getVersionRange());
               if (range.hasRestrictions())
               {
                  return range.containsVersion(version);
               }
               else
               {
                  return range.getRecommendedVersion().compareTo(version) == 0;
               }
            }
            catch (InvalidVersionSpecificationException e)
            {
            }
         }
      }

      return false;
   }

   private static boolean equals(Object o1, Object o2)
   {
      if (o1 == null)
      {
         return o2 == null;
      }
      return o1.equals(o2);
   }
}
