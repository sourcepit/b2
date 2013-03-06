/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Model;
import org.apache.maven.model.Prerequisites;
import org.apache.maven.model.Profile;
import org.apache.maven.model.merge.MavenModelMerger;

public class ModelTemplateMerger extends MavenModelMerger
{
   @Override
   protected void mergeModel_ModelVersion(Model target, Model source, boolean sourceDominant,
      Map<Object, Object> context)
   {
      String src = source.getModelVersion();
      if (src != null)
      {
         if (sourceDominant || target.getModelVersion() == null)
         {
            target.setModelVersion(src);
            target.setLocation("modelVersion", source.getLocation("modelVersion"));
         }
      }
   }

   @Override
   protected void mergeModel_ArtifactId(Model target, Model source, boolean sourceDominant, Map<Object, Object> context)
   {
      String src = source.getArtifactId();
      if (src != null)
      {
         if (sourceDominant || target.getArtifactId() == null)
         {
            target.setArtifactId(src);
            target.setLocation("artifactId", source.getLocation("artifactId"));
         }
      }
   }

   @Override
   protected void mergeModel_Profiles(Model target, Model source, boolean sourceDominant, Map<Object, Object> context)
   {
      List<Profile> src = source.getProfiles();
      if (!src.isEmpty())
      {
         List<Profile> tgt = target.getProfiles();
         Map<Object, Profile> merged = new LinkedHashMap<Object, Profile>((src.size() + tgt.size()) * 2);

         for (Profile element : tgt)
         {
            Object key = getProfileKey(element);
            merged.put(key, element);
         }

         for (Profile element : src)
         {
            Object key = getProfileKey(element);
            if (sourceDominant || !merged.containsKey(key))
            {
               merged.put(key, element);
            }
         }

         target.setProfiles(new ArrayList<Profile>(merged.values()));
      }
   }

   @Override
   protected void mergeModel_Prerequisites(Model target, Model source, boolean sourceDominant,
      Map<Object, Object> context)
   {
      Prerequisites src = source.getPrerequisites();
      if (src != null)
      {
         Prerequisites tgt = target.getPrerequisites();
         if (tgt == null)
         {
            tgt = new Prerequisites();
            tgt.setMaven(null);
            target.setPrerequisites(tgt);
         }
         mergePrerequisites(tgt, src, sourceDominant, context);
      }
   }
}
