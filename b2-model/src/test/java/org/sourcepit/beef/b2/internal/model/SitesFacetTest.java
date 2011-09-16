/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.beef.b2.internal.model;

import org.sourcepit.beef.b2.model.module.B2ModelFactory;
import org.sourcepit.beef.b2.model.module.SiteProject;
import org.sourcepit.beef.b2.model.module.SitesFacet;


/**
 * @author Bernd
 */
public class SitesFacetTest extends AbstractProjectFacetTest<SiteProject, SitesFacet>
{
   @Override
   protected SitesFacet createFacet()
   {
      return B2ModelFactory.eINSTANCE.createSitesFacet();
   }

   @Override
   protected SiteProject createProject()
   {
      return B2ModelFactory.eINSTANCE.createSiteProject();
   }
}
