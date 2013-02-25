/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.validation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

public class RecordingLogger extends MarkerIgnoringBase
{
   private static final long serialVersionUID = 1L;
   private static final String WARN = "WARN";
   private static final String INFO = "INFO";
   private static final String ERROR = "ERROR";

   private final List<String> messages = new ArrayList<String>();

   public List<String> getMessages()
   {
      return messages;
   }

   private void formatAndLog(String level, String format, Object... argArray)
   {
      FormattingTuple tuple = MessageFormatter.arrayFormat(format, argArray);
      log(level, tuple.getMessage(), null);
   }

   private void log(String level, String message, Throwable t)
   {
      messages.add(level + " " + message);
   }

   public boolean isTraceEnabled()
   {
      return false;
   }

   public void trace(String msg)
   {
      // NOP
   }

   public void trace(String format, Object arg)
   {
      // NOP
   }

   public void trace(String format, Object arg1, Object arg2)
   {
      // NOP
   }

   public void trace(String format, Object... argArray)
   {
      // NOP
   }

   public void trace(String msg, Throwable t)
   {
      // NOP
   }

   public boolean isDebugEnabled()
   {
      return false;
   }

   public void debug(String msg)
   {
      // NOP
   }

   public void debug(String format, Object arg)
   {
      // NOP
   }

   public void debug(String format, Object arg1, Object arg2)
   {
      // NOP
   }

   public void debug(String format, Object... argArray)
   {
      // NOP
   }

   public void debug(String msg, Throwable t)
   {
      // NOP
   }

   public boolean isInfoEnabled()
   {
      return true;
   }

   public void info(String msg)
   {
      log(INFO, msg, null);
   }

   public void info(String format, Object arg)
   {
      formatAndLog(INFO, format, arg);
   }

   public void info(String format, Object arg1, Object arg2)
   {
      formatAndLog(INFO, format, arg1, arg2);
   }

   public void info(String format, Object... argArray)
   {
      formatAndLog(INFO, format, argArray);
   }

   public void info(String msg, Throwable t)
   {
      log(INFO, msg, t);
   }

   public boolean isWarnEnabled()
   {
      return true;
   }

   public void warn(String msg)
   {
      log(WARN, msg, null);
   }

   public void warn(String format, Object arg)
   {
      formatAndLog(WARN, format, arg);
   }

   public void warn(String format, Object... argArray)
   {
      formatAndLog(WARN, format, argArray);
   }

   public void warn(String format, Object arg1, Object arg2)
   {
      formatAndLog(WARN, format, arg1, arg2);
   }

   public void warn(String msg, Throwable t)
   {
      log(WARN, msg, t);
   }

   public boolean isErrorEnabled()
   {
      return true;
   }

   public void error(String msg)
   {
      log(ERROR, msg, null);
   }

   public void error(String format, Object arg)
   {
      formatAndLog(ERROR, format, arg);
   }

   public void error(String format, Object arg1, Object arg2)
   {
      formatAndLog(ERROR, format, arg1, arg2);
   }

   public void error(String format, Object... argArray)
   {
      formatAndLog(ERROR, format, argArray);
   }

   public void error(String msg, Throwable t)
   {
      log(ERROR, msg, t);
   }
}
