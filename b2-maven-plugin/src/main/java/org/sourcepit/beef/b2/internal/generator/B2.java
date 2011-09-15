/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.beef.b2.directory.parser.module.IModuleFilter;
import org.sourcepit.beef.b2.internal.cleaner.ModuleCleaner;
import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.beef.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;

@Named
public class B2
{
   @Inject
   private ModuleCleaner cleaner;

   @Inject
   private IB2ModelBuilder modelBuilder;

   @Inject
   private B2Generator generator;

   public AbstractModule generate(File moduleDir, IModelCache modelCache, IModuleFilter traversalStrategy, IConverter converter, ITemplates templates)
   {
      cleaner.clean(moduleDir);

      final B2ModelBuildingRequest modelRequest = new B2ModelBuildingRequest();
      modelRequest.setModuleDirectory(moduleDir);
      modelRequest.setConverter(converter);
      modelRequest.setModelCache(modelCache);
      modelRequest.setModuleFilter(traversalStrategy);
      modelRequest.setInterpolate(!converter.isSkipInterpolator());

      final AbstractModule module = modelBuilder.build(modelRequest);

      if (!converter.isSkipGenerator())
      {
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
