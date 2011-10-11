/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.DecouplingB2ModelWalker;
import org.sourcepit.b2.model.module.AbstractModule;

@Named
public class B2Generator
{
   @Inject
   private List<IB2GenerationParticipant> generators;

   public void generate(final IB2GenerationRequest request)
   {
      final AbstractModule module = request.getModule();

      final List<IB2GenerationParticipant> copy = new ArrayList<IB2GenerationParticipant>(generators);
      Collections.sort(copy);

      for (final IB2GenerationParticipant generator : copy)
      {
         final DecouplingB2ModelWalker walker = new DecouplingB2ModelWalker(request.getModelCache(),
            generator.isReverse(), true)
         {
            @Override
            protected boolean doVisit(EObject eObject)
            {
               if (generator.isGeneratorInput(eObject))
               {
                  generator.generate(eObject, request.getConverter(), request.getTemplates());
               }
               return true;
            }
         };
         walker.walk(module);
      }
   }
}
