/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.validation;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.logging.AbstractLogger;
import org.codehaus.plexus.logging.Logger;

public class RecordingLogger extends AbstractLogger
{
   private final List<String> messages = new ArrayList<String>();

   public RecordingLogger()
   {
      super(0, "foo");
   }

   public List<String> getMessages()
   {
      return messages;
   }

   public void debug(String message, Throwable throwable)
   {
      messages.add("DEBUG " + message);
   }

   public void info(String message, Throwable throwable)
   {
      messages.add("INFO " + message);
   }

   public void warn(String message, Throwable throwable)
   {
      messages.add("WARN " + message);
   }

   public void error(String message, Throwable throwable)
   {
      messages.add("ERROR " + message);
   }

   public void fatalError(String message, Throwable throwable)
   {
      messages.add("FATAL " + message);
   }

   public Logger getChildLogger(String name)
   {
      return null;
   }

}
