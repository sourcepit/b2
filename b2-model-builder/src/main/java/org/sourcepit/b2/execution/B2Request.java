/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
