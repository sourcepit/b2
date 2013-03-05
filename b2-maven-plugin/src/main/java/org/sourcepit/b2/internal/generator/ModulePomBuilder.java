/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.internal.maven.util.MavenDepenenciesUtils.removeDependencies;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Activation;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.inheritance.InheritanceAssembler;
import org.apache.maven.model.merge.ModelMerger;
import org.apache.maven.model.normalization.ModelNormalizer;
import org.apache.maven.project.MavenProject;

import com.google.common.base.Predicate;

@Named
public class ModulePomBuilder
{
   @Inject
   private ModelNormalizer modelNormalizer;

   @Inject
   private InheritanceAssembler inheritanceAssembler;

   private static class ProfileMerger extends ModelMerger
   {
      @Override
      public void mergeModel_Profiles(Model target, Model source, boolean sourceDominant, Map<Object, Object> context)
      {
         final Map<String, Profile> targetProfiles = new LinkedHashMap<String, Profile>();
         for (Profile profile : target.getProfiles())
         {
            targetProfiles.put(profile.getId(), profile);
         }
         for (Profile srcProfile : source.getProfiles())
         {
            final Profile tgtProfile = targetProfiles.get(srcProfile.getId());
            if (tgtProfile == null)
            {
               targetProfiles.put(srcProfile.getId(), srcProfile.clone());
            }
            else
            {
               mergeProfile(tgtProfile, srcProfile, sourceDominant, context);
            }
         }
         target.setProfiles(new ArrayList<Profile>(targetProfiles.values()));
      }

      @Override
      protected void mergeProfile(Profile tgtProfile, Profile srcProfile, boolean sourceDominant,
         Map<Object, Object> context)
      {
         final Activation tgtActivation = tgtProfile.getActivation();
         final Activation srcActivation = srcProfile.getActivation();
         if (tgtActivation == null)
         {
            tgtProfile.setActivation(srcActivation);
         }
         else if (srcActivation != null)
         {
            if (sourceDominant)
            {
               tgtProfile.setActivation(srcActivation);
            }
         }

         mergeModelBase(tgtProfile, srcProfile, sourceDominant, context);

         BuildBase tgtBuild = tgtProfile.getBuild();
         BuildBase srcBuild = srcProfile.getBuild();
         if (tgtBuild == null)
         {
            tgtProfile.setBuild(srcBuild);
         }
         else if (srcBuild != null)
         {
            mergeBuildBase(tgtBuild, srcBuild, sourceDominant, context);
         }
      }
   }

   public Model buildModulePom(MavenProject bootProject)
   {
      final List<Model> modelHierarchy = cloneModelHierarchy(bootProject);
      for (Model model : modelHierarchy)
      {
         modelNormalizer.mergeDuplicates(model, null, null);
      }
      for (int i = modelHierarchy.size() - 2; i >= 0; i--)
      {
         Model parent = modelHierarchy.get(i + 1);
         Model child = modelHierarchy.get(i);
         inheritanceAssembler.assembleModelInheritance(child, parent, null, null);

         // Bug #76: Parent profiles are lost while generating Maven poms
         new ProfileMerger().mergeModel_Profiles(child, parent, false, null);
      }

      Model resultModel = modelHierarchy.get(0);
      modelNormalizer.mergeDuplicates(resultModel, null, null);

      resultModel.setParent(null);

      removeDependencies(resultModel, new Predicate<Dependency>()
      {
         public boolean apply(Dependency input)
         {
            return "module".equals(input.getType());
         }
      });

      // poms
      return resultModel;
   }

   private List<Model> cloneModelHierarchy(MavenProject project)
   {
      List<Model> models = new ArrayList<Model>();
      MavenProject currentProject = project;
      while (currentProject != null)
      {
         models.add(currentProject.getOriginalModel().clone());
         currentProject = currentProject.getParent();
      }
      return models;
   }
}
