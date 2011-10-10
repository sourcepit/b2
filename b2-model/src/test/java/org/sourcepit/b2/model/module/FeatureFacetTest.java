/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;


/**
 * @author Bernd
 * 
 */
public class FeatureFacetTest extends AbstractProjectFacetTest<FeatureProject, FeaturesFacet>
{
   @Override
   protected FeaturesFacet createFacet()
   {
      return ModuleModelFactory.eINSTANCE.createFeaturesFacet();
   }

   @Override
   protected FeatureProject createProject()
   {
      return ModuleModelFactory.eINSTANCE.createFeatureProject();
   }

}
