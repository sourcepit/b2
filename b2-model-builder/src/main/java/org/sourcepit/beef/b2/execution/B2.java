/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.execution;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.beef.b2.directory.parser.module.IModuleFilter;
import org.sourcepit.beef.b2.internal.cleaner.IFileService;
import org.sourcepit.beef.b2.internal.generator.B2GenerationRequest;
import org.sourcepit.beef.b2.internal.generator.B2Generator;
import org.sourcepit.beef.b2.internal.generator.ITemplates;
import org.sourcepit.beef.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.beef.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;
import org.sourcepit.beef.b2.model.module.AbstractModule;

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

   public AbstractModule generate(File moduleDir, IModelCache modelCache, IModuleFilter traversalStrategy,
      IConverter converter, ITemplates templates)
   {
      fileService.clean(moduleDir);

      final B2ModelBuildingRequest modelRequest = new B2ModelBuildingRequest();
      modelRequest.setModuleDirectory(moduleDir);
      modelRequest.setConverter(converter);
      modelRequest.setModelCache(modelCache);
      modelRequest.setModuleFilter(traversalStrategy);
      modelRequest.setInterpolate(!converter.isSkipInterpolator());

      final AbstractModule module = modelBuilder.build(modelRequest);

      if (!converter.isSkipGenerator())
      {
         for (IB2Listener listener : listeners)
         {
            listener.startGeneration(module);
         }

         final B2GenerationRequest genRequest = new B2GenerationRequest();
         genRequest.setModule(module);
         genRequest.setConverter(converter);
         genRequest.setModelCache(modelCache);
         genRequest.setTemplates(templates);

         generator.generate(genRequest);
      }

      return module;
   }
}
