/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;

import org.sourcepit.common.utils.props.PropertiesSource;

public final class ProjectResourcesUtils
{
   private ProjectResourcesUtils()
   {
      super();
   }

   public static File getResourcesDir(File projectDir, PropertiesSource properties)
   {
      return new File(projectDir, "resources");
   }
}
