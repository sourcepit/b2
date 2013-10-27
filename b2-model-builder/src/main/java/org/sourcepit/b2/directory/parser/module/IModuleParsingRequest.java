/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.module;

import java.io.File;
import java.util.Map;

import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.props.PropertiesSource;

public interface IModuleParsingRequest
{
   ModuleDirectory getModuleDirectory();
   
   PropertiesSource getModuleProperties();

   Map<File, AbstractModule> getModulesCache();
}
