/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.generator.IB2GenerationParticipant;
import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.model.builder.util.DecouplingB2ModelWalker;

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
         new DecouplingB2ModelWalker(request.getModelCache(), generator.isReverse(), true)
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
         }.walk(module);
      }
   }
}
