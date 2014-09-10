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

package org.sourcepit.b2.model.module.util;
public final class Identifier implements Identifiable
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

   @Override
   public boolean isIdentifyableBy(Identifier identifier)
   {
      throw new IllegalStateException();
   }

   @Override
   public Identifier toIdentifier()
   {
      throw new IllegalStateException();
   }

}
