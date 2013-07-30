/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static java.lang.Integer.valueOf;
import static java.util.Collections.singletonMap;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_HIDDEN;

import java.io.File;
import java.util.Map;

import javax.inject.Named;

import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class B2FileFlagsProvider implements FileFlagsProvider
{
   @Override
   public Map<File, Integer> getFileFlags(File moduleDir, PropertiesSource properties)
   {
      return singletonMap(new File(moduleDir, ".b2"), valueOf(FLAG_DERIVED | FLAG_HIDDEN));
   }
}
