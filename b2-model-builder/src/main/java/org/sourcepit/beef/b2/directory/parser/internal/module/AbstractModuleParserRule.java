/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.module;

import java.io.File;
import java.util.Locale;
import java.util.Set;

import org.sourcepit.beef.b2.common.internal.utils.NlsUtils;
import org.sourcepit.beef.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.model.builder.util.IConverter;

public abstract class AbstractModuleParserRule<M extends AbstractModule>
{
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

   protected String getModuleVersion(IConverter converter)
   {
      return converter.getModuleVersion();
   }

   protected String getModuleId(final IConverter converter, final File baseDir)
   {
      return converter.getModuleId(baseDir);
   }
}
