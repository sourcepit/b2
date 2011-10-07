/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.util.Properties;

public interface ITemplates
{
   void copy(String resourcePath, File targetDir);

   void copy(String resourcePath, File targetDir, Properties properties);
}