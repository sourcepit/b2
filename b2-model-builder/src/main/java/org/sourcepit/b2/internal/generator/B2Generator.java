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
import org.sourcepit.b2.execution.LifecyclePhase;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.ModuleWalker;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;

@Named
public class B2Generator
{
   private final List<? extends IB2GenerationParticipant> generators;

   private final List<B2GeneratorLifecycleParticipant> lifecycleParticipants;

   @Inject
   public B2Generator(List<? extends IB2GenerationParticipant> generators,
      List<B2GeneratorLifecycleParticipant> lifecycleParticipants)
   {
      this.generators = generators;
      this.lifecycleParticipants = lifecycleParticipants;
   }

   public void generate(final IB2GenerationRequest request)
   {
      newLifecyclePhase().execute(request);
   }

   private LifecyclePhase<Void, IB2GenerationRequest, B2GeneratorLifecycleParticipant> newLifecyclePhase()
   {
      return new LifecyclePhase<Void, IB2GenerationRequest, B2GeneratorLifecycleParticipant>(lifecycleParticipants)
      {
         @Override
         protected void pre(B2GeneratorLifecycleParticipant participant, IB2GenerationRequest request)
         {
            participant.preGenerate(request.getModule());
         }

         @Override
         protected Void doExecute(IB2GenerationRequest request)
         {
            doGenerate(request);
            return null;
         }

         @Override
         protected void post(B2GeneratorLifecycleParticipant participant, IB2GenerationRequest request, Void result,
            ThrowablePipe errors)
         {
            participant.postGenerate(request.getModule(), errors);
         }
      };
   }

   private void doGenerate(final IB2GenerationRequest request)
   {
      final AbstractModule module = request.getModule();

      final List<IB2GenerationParticipant> copy = new ArrayList<IB2GenerationParticipant>(generators);
      Collections.sort(copy);

      for (final IB2GenerationParticipant generator : copy)
      {
         final ModuleWalker walker = new ModuleWalker(generator.isReverse(), true)
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
