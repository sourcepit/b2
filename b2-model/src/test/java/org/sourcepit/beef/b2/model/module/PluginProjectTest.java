/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.beef.b2.model.module;

import org.sourcepit.beef.b2.model.module.ModuleFactory;
import org.sourcepit.beef.b2.model.module.PluginProject;

import junit.framework.TestCase;

/**
 * @author Bernd
 */
public class PluginProjectTest extends TestCase
{
   public void testIsFragment() throws Exception
   {
      PluginProject pluginProject = ModuleFactory.eINSTANCE.createPluginProject();
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
