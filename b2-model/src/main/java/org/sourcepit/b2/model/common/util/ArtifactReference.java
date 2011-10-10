/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common.util;

import org.eclipse.emf.common.util.URI;
import org.sourcepit.b2.model.session.util.ArtifactVersion;
import org.sourcepit.b2.model.session.util.DefaultArtifactVersion;
import org.sourcepit.b2.model.session.util.InvalidVersionSpecificationException;
import org.sourcepit.b2.model.session.util.VersionRange;

public final class ArtifactReference
{
   private final String groupId, artifactId, versionRange, classifier, type;

   public ArtifactReference(String groupId, String artifactId, String versionRange, String type)
   {
      this(groupId, artifactId, versionRange, null, type);
   }

   public ArtifactReference(String groupId, String artifactId, String versionRange, String classifier, String type)
   {
      this.groupId = groupId;
      this.artifactId = artifactId;
      this.versionRange = versionRange;
      this.classifier = "".equals(classifier) ? null : classifier;
      this.type = type;
   }

   public String getGroupId()
   {
      return groupId;
   }

   public String getArtifactId()
   {
      return artifactId;
   }

   public String getVersionRange()
   {
      return versionRange;
   }

   public String getClassifier()
   {
      return classifier;
   }

   public String getType()
   {
      return type;
   }

   public boolean isAmingAt(ArtifactIdentifier id)
   {
      if (!equals(getGroupId(), id.getGroupId()))
      {
         return false;
      }
      if (!equals(getArtifactId(), id.getArtifactId()))
      {
         return false;
      }
      if (!equals(getClassifier(), id.getClassifier()))
      {
         return false;
      }
      if (!equals(getType(), id.getType()))
      {
         return false;
      }

      final ArtifactVersion version = new DefaultArtifactVersion(id.getVersion());
      final VersionRange range = getRange();
      if (range.hasRestrictions())
      {
         return range.containsVersion(version);
      }
      else
      {
         return range.getRecommendedVersion().compareTo(version) == 0;
      }
   }

   private VersionRange range;

   private VersionRange getRange()
   {
      if (range == null)
      {
         try
         {
            range = VersionRange.createFromVersionSpec(getVersionRange());
         }
         catch (InvalidVersionSpecificationException e)
         {
         }
      }
      return range;
   }

   private static boolean equals(Object o1, Object o2)
   {
      if (o1 == null)
      {
         return o2 == null;
      }
      return o1.equals(o2);
   }

   public URI toUri()
   {
      final StringBuilder sb = new StringBuilder();
      sb.append("gav:/");
      sb.append(getGroupId());
      sb.append("/");
      sb.append(getArtifactId());
      sb.append("/");
      sb.append(getVersionRange());
      if (getClassifier() != null)
      {
         sb.append("/");
         sb.append(getClassifier());
      }
      sb.append("/");
      sb.append(getType());
      return URI.createURI(sb.toString());
   }

   public static ArtifactReference fromUri(URI gavUri)
   {
      // gav:/groupId/artifactId/version/classifier/type -> 5 segments with classifier, without 4
      final String groupId, artifactId, version, classifier, type;
      switch (gavUri.segmentCount())
      {
         case 4 :
            groupId = gavUri.segment(0);
            artifactId = gavUri.segment(1);
            version = gavUri.segment(2);
            classifier = null;
            type = gavUri.segment(3);
            break;
         case 5 :
            groupId = gavUri.segment(0);
            artifactId = gavUri.segment(1);
            version = gavUri.segment(2);
            classifier = gavUri.segment(3);
            type = gavUri.segment(4);
            break;
         default :
            throw new IllegalArgumentException(gavUri.toString() + " is not a wellformed GAV URI.");
      }
      return new ArtifactReference(groupId, artifactId, version, classifier, type);
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((artifactId == null) ? 0 : artifactId.hashCode());
      result = prime * result + ((classifier == null) ? 0 : classifier.hashCode());
      result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
      result = prime * result + ((type == null) ? 0 : type.hashCode());
      result = prime * result + ((versionRange == null) ? 0 : versionRange.hashCode());
      return result;
   }

   // CSOFF
   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      ArtifactReference other = (ArtifactReference) obj;
      if (artifactId == null)
      {
         if (other.artifactId != null)
            return false;
      }
      else if (!artifactId.equals(other.artifactId))
         return false;
      if (classifier == null)
      {
         if (other.classifier != null)
            return false;
      }
      else if (!classifier.equals(other.classifier))
         return false;
      if (groupId == null)
      {
         if (other.groupId != null)
            return false;
      }
      else if (!groupId.equals(other.groupId))
         return false;
      if (type == null)
      {
         if (other.type != null)
            return false;
      }
      else if (!type.equals(other.type))
         return false;
      if (versionRange == null)
      {
         if (other.versionRange != null)
            return false;
      }
      else if (!versionRange.equals(other.versionRange))
         return false;
      return true;
   } // CSON
}
