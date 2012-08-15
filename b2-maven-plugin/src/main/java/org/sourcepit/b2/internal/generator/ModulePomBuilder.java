/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.inheritance.InheritanceAssembler;
import org.apache.maven.model.normalization.ModelNormalizer;
import org.apache.maven.project.MavenProject;

@Named
public class ModulePomBuilder
{
   @Inject
   private ModelNormalizer modelNormalizer;

   @Inject
   private InheritanceAssembler inheritanceAssembler;

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
      }

      Model resultModel = modelHierarchy.get(0);
      modelNormalizer.mergeDuplicates(resultModel, null, null);

      resultModel.setParent(null);
      resultModel.setProfiles(collectProfiles(bootProject)); // Bug #76: Parent profiles are lost while generating Maven
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

   private List<Profile> collectProfiles(MavenProject project)
   {
      final Map<String, Profile> profileMap = new LinkedHashMap<String, Profile>();
      MavenProject currentProject = project;
      while (currentProject != null)
      {
         for (Profile profile : currentProject.getOriginalModel().getProfiles())
         {
            final String id = profile.getId();
            if (!profileMap.containsKey(id))
            {
               profileMap.put(id, profile.clone());
            }
         }
         currentProject = currentProject.getParent();
      }
      return new ArrayList<Profile>(profileMap.values());
   }
}
