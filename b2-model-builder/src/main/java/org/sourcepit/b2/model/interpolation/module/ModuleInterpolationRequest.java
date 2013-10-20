/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.module;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.props.PropertiesSource;

/**
 * @author Bernd
 */
public class ModuleInterpolationRequest implements IModuleInterpolationRequest
{
   private AbstractModule module;

   private PropertiesSource moduleProperties;

   public AbstractModule getModule()
   {
      return module;
   }

   public void setModule(AbstractModule module)
   {
      this.module = module;
   }

   public PropertiesSource getModuleProperties()
   {
      return moduleProperties;
   }

   public void setModuleProperties(PropertiesSource moduleProperties)
   {
      this.moduleProperties = moduleProperties;
   }

}
