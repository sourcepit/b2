/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.maven.ext.internal.util;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal do-nothing
 * @requiresProject false
 */
public class DoNothingMojo extends AbstractMojo
{
   public void execute() throws MojoExecutionException, MojoFailureException
   {
   }
}
