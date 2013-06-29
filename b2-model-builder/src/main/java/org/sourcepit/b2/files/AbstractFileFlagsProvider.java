/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import java.io.File;
import java.util.Map;

import org.sourcepit.common.utils.props.PropertiesSource;

public abstract class AbstractFileFlagsProvider implements FileFlagsProvider
{
   @Override
   public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
   {
      return null;
   }

   @Override
   public FileFlagsInvestigator createFileFlagsInvestigator(File moduleDir, PropertiesSource properties)
   {
      return null;
   }
}