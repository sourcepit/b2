/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.beef.b2.model.builder.internal.tests.harness;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Named;

import org.sourcepit.beef.b2.directory.parser.internal.project.AbstractProjectParserRule;
import org.sourcepit.beef.b2.internal.model.PluginProject;
import org.sourcepit.beef.b2.model.builder.util.IConverter;

/**
 * @author Bernd
 */
@Named("test")
public class TestProjectParserRule extends AbstractProjectParserRule<PluginProject>
{
   private AtomicInteger calls = new AtomicInteger();

   @Override
   public PluginProject parse(File directory, IConverter converter)
   {
      calls.incrementAndGet();
      return null;
   }

   public int getCalls()
   {
      return calls.intValue();
   }
}
