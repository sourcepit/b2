/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.execution;

import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;

public class B2Request extends B2ModelBuildingRequest
{
   private ITemplates templates;
   
   public ITemplates getTemplates()
   {
      return templates;
   }
   
   public void setTemplates(ITemplates templates)
   {
      this.templates = templates;
   }
}
