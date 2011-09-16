/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.model;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.internal.model.harness.EcoreUtils;
import org.sourcepit.beef.b2.internal.model.harness.EcoreUtils.RunnableWithEObject;
import org.sourcepit.beef.b2.model.module.B2ModelPackage;
import org.sourcepit.beef.b2.model.module.ProjectFacet;

/**
 * @author Bernd
 */
public class ProjectFacetTest extends TestCase
{
   public void testResolveReference() throws Exception
   {
      EcoreUtils.foreachSupertype(B2ModelPackage.eINSTANCE.getProjectFacet(), new RunnableWithEObject()
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
