/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.module.AbstractModule;

public interface IB2SessionService
{
   ResourceSet getCurrentResourceSet();

   List<File> getCurrentProjectDirs();
   
   List<AbstractModule> getCurrentModules();

}
