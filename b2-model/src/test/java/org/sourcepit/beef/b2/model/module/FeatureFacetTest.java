/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.beef.b2.model.module;

import org.sourcepit.beef.b2.model.module.B2ModelFactory;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;


/**
 * @author Bernd
 * 
 */
public class FeatureFacetTest extends AbstractProjectFacetTest<FeatureProject, FeaturesFacet>
{
   @Override
   protected FeaturesFacet createFacet()
   {
      return B2ModelFactory.eINSTANCE.createFeaturesFacet();
   }

   @Override
   protected FeatureProject createProject()
   {
      return B2ModelFactory.eINSTANCE.createFeatureProject();
   }

}
