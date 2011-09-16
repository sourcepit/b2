/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.beef.b2.model.module;


/**
 * @author Bernd
 */
public class SitesFacetTest extends AbstractProjectFacetTest<SiteProject, SitesFacet>
{
   @Override
   protected SitesFacet createFacet()
   {
      return ModuleFactory.eINSTANCE.createSitesFacet();
   }

   @Override
   protected SiteProject createProject()
   {
      return ModuleFactory.eINSTANCE.createSiteProject();
   }
}
