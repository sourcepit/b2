/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.util;
public final class Identifier
{
   private final String id;

   private final String version;

   public Identifier(String id, String version)
   {
      if (id == null)
      {
         throw new IllegalArgumentException("Id must not be null.");
      }
      this.id = id;
      this.version = version == null ? "0.0.0" : version;
   }

   public String getId()
   {
      return id;
   }

   public String getVersion()
   {
      return version;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
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
      Identifier other = (Identifier) obj;
      if (id == null)
      {
         if (other.id != null)
            return false;
      }
      else if (!id.equals(other.id))
         return false;
      if (version == null)
      {
         if (other.version != null)
            return false;
      }
      else if (!version.equals(other.version))
         return false;
      return true;
   } // CSON

}
