/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static org.sourcepit.b2.files.ModuleFiles.FLAG_FORBIDDEN;

import java.io.File;
import java.util.Map;

import javax.inject.Named;

import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class B2ModulesFileFlagsProvider implements FileFlagsProvider
{
   @Override
   public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
   {
      return null;
   }

   @Override
   public FileFlagsInvestigator createFileFlagsInvestigator(final File moduleDir, PropertiesSource properties)
   {
      final String filePatterns = properties.get("b2.modules");
      if (filePatterns == null)
      {
         return null;
      }
      final PathMatcher pathMatcher = PathMatcher.parseFilePatterns(moduleDir, filePatterns);
      return new FileFlagsInvestigator()
      {
         @Override
         public int determineFileFlags(File file)
         {
            final boolean match = pathMatcher.isMatch(file.getPath());
            return match ? 0 : FLAG_FORBIDDEN;
         }
      };
   }

}
