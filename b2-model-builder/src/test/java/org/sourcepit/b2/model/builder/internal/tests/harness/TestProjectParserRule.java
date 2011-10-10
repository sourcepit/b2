/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.project.AbstractProjectParserRule;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.PluginProject;

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
