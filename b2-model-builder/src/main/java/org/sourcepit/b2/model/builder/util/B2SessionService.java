/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.module.AbstractModule;


@Named
@Singleton
public class B2SessionService
{
   private List<File> projectDirs;

   private List<AbstractModule> modules;

   private ResourceSet resourceSet;

   public void setCurrentProjectDirs(List<File> currentSession)
   {
      this.projectDirs = currentSession;
   }

   public List<File> getCurrentProjectDirs()
   {
      return projectDirs;
   }

   public void setCurrentModules(List<AbstractModule> modules)
   {
      this.modules = modules;
   }

   public List<AbstractModule> getCurrentModules()
   {
      return modules;
   }

   public void setCurrentResourceSet(ResourceSet currentResourceSet)
   {
      resourceSet = currentResourceSet;
   }

   public ResourceSet getCurrentResourceSet()
   {
      return resourceSet;
   }
}
