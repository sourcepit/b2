/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.module;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.props.PropertiesSource;

public class ModuleParsingRequest implements IModuleParsingRequest
{
   private File moduleDirectory;

   private IModuleFilter moduleFilter;

   private PropertiesSource moduleProperties;
   
   private Map<File, AbstractModule> modulesCache = new LinkedHashMap<File, AbstractModule>();

   public PropertiesSource getModuleProperties()
   {
      return moduleProperties;
   }

   public void setModuleProperties(PropertiesSource moduleProperties)
   {
      this.moduleProperties = moduleProperties;
   }

   /**
    * {@inheritDoc}
    */
   public File getModuleDirectory()
   {
      return moduleDirectory;
   }

   public void setModuleDirectory(File moduleDirectory)
   {
      this.moduleDirectory = moduleDirectory;
   }

   public IModuleFilter getModuleFilter()
   {
      return moduleFilter;
   }

   public void setModuleFilter(IModuleFilter moduleFilter)
   {
      this.moduleFilter = moduleFilter;
   }
   
   public Map<File, AbstractModule> getModulesCache()
   {
      return modulesCache;
   }
}
