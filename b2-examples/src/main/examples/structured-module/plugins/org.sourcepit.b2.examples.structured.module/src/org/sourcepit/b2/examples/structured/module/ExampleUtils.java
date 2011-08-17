/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
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
