/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
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
