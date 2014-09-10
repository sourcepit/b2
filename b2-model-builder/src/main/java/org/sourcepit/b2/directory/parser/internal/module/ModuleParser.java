/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.model.builder.util.ModuleWalker;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.common.utils.props.PropertiesSource;

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

      final ModuleDirectory moduleDirectory = request.getModuleDirectory();
      if (moduleDirectory == null)
      {
         throw new IllegalArgumentException("Module files must not be null.");
      }

      final File baseDir = moduleDirectory.getFile();
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
