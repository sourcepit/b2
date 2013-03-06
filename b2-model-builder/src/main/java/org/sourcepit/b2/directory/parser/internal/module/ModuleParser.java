/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.directory.parser.module.IModuleParser;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.directory.parser.module.ModuleParserLifecycleParticipant;
import org.sourcepit.b2.execution.LifecyclePhase;
import org.sourcepit.b2.model.builder.util.ModuleWalker;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.modeling.common.Annotatable;

@Named
public class ModuleParser implements IModuleParser
{
   @Inject
   private List<AbstractModuleParserRule<? extends AbstractModule>> rules;

   @Inject
   private List<IModuleParserExtender> extenders;

   @Inject
   private List<ModuleParserLifecycleParticipant> lifecycleParticipants;

   public AbstractModule parse(final IModuleParsingRequest request)
   {
      checkRequest(request);
      return newLifecyclePhase().execute(request);
   }

   private LifecyclePhase<AbstractModule, IModuleParsingRequest, ModuleParserLifecycleParticipant> newLifecyclePhase()
   {
      return new LifecyclePhase<AbstractModule, IModuleParsingRequest, ModuleParserLifecycleParticipant>(
         lifecycleParticipants)
      {
         @Override
         protected void pre(ModuleParserLifecycleParticipant participant, IModuleParsingRequest request)
         {
            participant.preParse(request);
         }

         @Override
         protected AbstractModule doExecute(IModuleParsingRequest request)
         {
            return doParse(request);
         }

         @Override
         protected void post(ModuleParserLifecycleParticipant participant, IModuleParsingRequest request,
            AbstractModule result, ThrowablePipe errors)
         {
            participant.postParse(request, result, errors);
         }
      };
   }

   private AbstractModule doParse(IModuleParsingRequest request)
   {
      final List<AbstractModuleParserRule<? extends AbstractModule>> orderedRules = new ArrayList<AbstractModuleParserRule<? extends AbstractModule>>(
         rules);
      Collections.sort(orderedRules);

      for (AbstractModuleParserRule<? extends AbstractModule> rule : orderedRules)
      {
         final AbstractModule module = rule.parse(request);
         if (module != null)
         {
            return enhance(module, request.getModuleProperties());
         }
      }
      return null;
   }

   protected AbstractModule enhance(AbstractModule module, final PropertiesSource properties)
   {
      if (!extenders.isEmpty())
      {
         final ModuleWalker walker = new ModuleWalker()
         {
            @Override
            protected boolean doVisit(EObject eObject)
            {
               if (eObject instanceof Annotatable)
               {
                  for (IModuleParserExtender extender : extenders)
                  {
                     extender.extend((Annotatable) eObject, properties);
                  }
               }
               return true;
            }
         };
         walker.walk(module);
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

      final PropertiesSource properties = request.getModuleProperties();
      if (properties == null)
      {
         throw new IllegalArgumentException("properties must not be null.");
      }
   }
}
