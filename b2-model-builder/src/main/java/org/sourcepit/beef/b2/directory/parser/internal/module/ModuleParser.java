/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.directory.parser.module.IModuleParser;
import org.sourcepit.beef.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.beef.b2.model.builder.util.DecouplingB2ModelWalker;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.Annotateable;

@Named
public class ModuleParser implements IModuleParser
{
   @Inject
   private List<AbstractModuleParserRule<AbstractModule>> rules;

   @Inject
   private List<IModuleParserExtender> extenders;

   public AbstractModule parse(IModuleParsingRequest request)
   {
      checkRequest(request);

      final IModelCache cache = request.getModelCache();
      if (cache != null)
      {
         AbstractModule module = cache.get(request.getModuleDirectory());
         if (module != null)
         {
            return module;
         }
      }

      final List<AbstractModuleParserRule<AbstractModule>> orderedRules = new ArrayList<AbstractModuleParserRule<AbstractModule>>(
         rules);
      Collections.sort(orderedRules);

      for (AbstractModuleParserRule<AbstractModule> rule : orderedRules)
      {
         final AbstractModule module = rule.parse(request);
         if (module != null)
         {
            return enhance(module, request.getConverter(), cache);
         }
      }
      return null;
   }

   protected AbstractModule enhance(AbstractModule module, final IConverter converter, IModelCache cache)
   {
      if (!extenders.isEmpty())
      {
         new DecouplingB2ModelWalker(cache)
         {
            @Override
            protected boolean doVisit(EObject eObject)
            {
               if (eObject instanceof Annotateable)
               {
                  for (IModuleParserExtender extender : extenders)
                  {
                     extender.extend((Annotateable) eObject, converter);
                  }
               }
               return true;
            }
         }.walk(module);
      }
      return module;
   }

   protected void checkRequest(IModuleParsingRequest request)
   {
      if (request == null)
      {
         throw new IllegalArgumentException("Request must not be null.");
      }

      final File baseDir = request.getModuleDirectory();
      if (baseDir == null)
      {
         throw new IllegalArgumentException("Project must not be null.");
      }

      final IConverter converter = request.getConverter();
      if (converter == null)
      {
         throw new IllegalArgumentException("converter must not be null.");
      }
   }
}
