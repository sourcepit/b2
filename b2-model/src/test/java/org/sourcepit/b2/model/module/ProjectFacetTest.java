/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.test.internal.harness.EcoreUtils;
import org.sourcepit.b2.model.module.test.internal.harness.EcoreUtils.RunnableWithEObject;

/**
 * @author Bernd
 */
public class ProjectFacetTest extends TestCase
{
   public void testResolveReference() throws Exception
   {
      EcoreUtils.foreachSupertype(ModuleModelPackage.eINSTANCE.getProjectFacet(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            ProjectFacet<?> facet = (ProjectFacet<?>) eObject;
            try
            {
               facet.resolveReference(null);
               fail();
            }
            catch (IllegalArgumentException e)
            {
            }
         }
      });
   }
}
