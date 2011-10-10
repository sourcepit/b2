/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import junit.framework.TestCase;

/**
 * @author Bernd
 */
public class PluginProjectTest extends TestCase
{
   public void testIsFragment() throws Exception
   {
      PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setId("my.fragment");
      assertFalse(pluginProject.isFragment());

      pluginProject.setFragmentHostVersion("1.0.0");
      assertFalse(pluginProject.isFragment());

      pluginProject.setFragmentHostSymbolicName("my");
      assertTrue(pluginProject.isFragment());

      pluginProject.setFragmentHostVersion(null);
      assertTrue(pluginProject.isFragment());
   }
}
