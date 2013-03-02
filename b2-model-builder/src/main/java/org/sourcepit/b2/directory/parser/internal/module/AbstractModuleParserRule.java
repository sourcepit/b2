/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
