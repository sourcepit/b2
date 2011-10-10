/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.examples.structured.module;
/**
 * @author Bernd
 */
public final class ExampleUtils
{
   private ExampleUtils()
   {
      super();
   }

   public static String convertSrtring(String string, boolean toUpper)
   {
      if (toUpper)
      {
         return string.toUpperCase();
      }
      else
      {
         return string.toLowerCase();
      }
   }

}
