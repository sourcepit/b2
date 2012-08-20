/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.sourcepit.b2.common.internal.utils.XmlUtils;
import org.w3c.dom.Element;

public class P2GeneratorB2ExtensionIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return true;
   }
   
   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "clean");
      assertThat(err, is(0));
   }
}
