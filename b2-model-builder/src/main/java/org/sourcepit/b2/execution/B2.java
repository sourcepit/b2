/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.internal.cleaner.IFileService;
import org.sourcepit.b2.internal.generator.B2GenerationRequest;
import org.sourcepit.b2.internal.generator.B2Generator;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.AbstractModule;

@Named
public class B2
{
   @Inject
   private IFileService fileService;

   @Inject
   private IB2ModelBuilder modelBuilder;

   @Inject
   private B2Generator generator;

   @Inject
   private List<IB2Listener> listeners;
   
   public AbstractModule generate(B2Request request)
   {
      fileService.clean(request.getModuleDirectory());

      final AbstractModule module = modelBuilder.build(request);

      final IConverter converter = request.getConverter();
      if (!converter.isSkipGenerator())
      {
         for (IB2Listener listener : listeners)
         {
            listener.startGeneration(module);
         }

         final B2GenerationRequest genRequest = new B2GenerationRequest();
         genRequest.setModule(module);
         genRequest.setConverter(converter);
         genRequest.setTemplates(request.getTemplates());

         generator.generate(genRequest);
      }

      return module;
   }
}
