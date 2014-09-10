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

import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.builder.util.ModuleIdDerivator;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.nls.NlsUtils;
import org.sourcepit.common.utils.props.PropertiesSource;

public abstract class AbstractModuleParserRule<M extends AbstractModule>
   implements
      Comparable<AbstractModuleParserRule<M>>
{
   @Inject
   private ModuleIdDerivator moduleIdDerivator;

   @Inject
   private BasicConverter converter;

   public final M parse(IModuleParsingRequest request)
   {
      final M module = doParse(request);
      if (module != null)
      {
         final Set<Locale> locales = NlsUtils.getNlsPropertyFiles(module.getDirectory(), "module", "properties")
            .keySet();
         if (locales.isEmpty())
         {
            module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
         }
         else
         {
            module.getLocales().addAll(locales);
         }
      }
      return module;
   }

   protected abstract M doParse(IModuleParsingRequest request);

   protected String getModuleVersion(PropertiesSource properties)
   {
      return converter.getModuleVersion(properties);
   }

   protected String getModuleId(AbstractModule module, PropertiesSource properties)
   {
      return moduleIdDerivator.deriveModuleId(module, properties);
   }


   public int compareTo(AbstractModuleParserRule<M> otherRule)
   {
      final int otherPrio = otherRule.getPriority();
      final int priority = getPriority();
      if (otherPrio == priority)
      {
         return 0;
      }
      return priority < otherPrio ? 1 : -1;
   }

   protected int getPriority()
   {
      return Integer.MAX_VALUE / 2;
   }
}
