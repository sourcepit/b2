/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common.util;

import org.eclipse.emf.common.util.URI;

public final class ArtifactIdentifier
{
   private final String groupId, artifactId, version, classifier, type;

   public ArtifactIdentifier(String groupId, String artifactId, String version, String type)
   {
      this(groupId, artifactId, version, null, type);
   }

   public ArtifactIdentifier(String groupId, String artifactId, String version, String classifier, String type)
   {
      this.groupId = groupId;
      this.artifactId = artifactId;
      this.version = version;
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

   public String getVersion()
   {
      return version;
   }

   public String getClassifier()
   {
      return classifier;
   }

   public String getType()
   {
      return type;
   }

   public URI toUri()
   {
      final StringBuilder sb = new StringBuilder();
      sb.append("gav:/");
      sb.append(getGroupId());
      sb.append("/");
      sb.append(getArtifactId());
      sb.append("/");
      sb.append(getVersion());
      if (getClassifier() != null)
      {
         sb.append("/");
         sb.append(getClassifier());
      }
      sb.append("/");
      sb.append(getType());
      return URI.createURI(sb.toString());
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
      result = prime * result + ((version == null) ? 0 : version.hashCode());
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
      ArtifactIdentifier other = (ArtifactIdentifier) obj;
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
      if (version == null)
      {
         if (other.version != null)
            return false;
      }
      else if (!version.equals(other.version))
         return false;
      return true;
   }

   // CSON

   @Override
   public String toString()
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(getGroupId());
      sb.append(":");
      sb.append(getArtifactId());
      sb.append(":");
      sb.append(getType());
      if (getClassifier() != null && getClassifier().length() > 0)
      {
         sb.append(":");
         sb.append(getClassifier());
      }
      sb.append(":");
      sb.append(getVersion());
      return sb.toString();
   }

}
