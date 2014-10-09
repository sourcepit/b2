/**
 * Copyright (c) 2014 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its.util;


import java.io.File;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class FilePresentMatcher extends TypeSafeDiagnosingMatcher<File>
{
   private boolean shouldBePresent;

   
   public FilePresentMatcher(boolean shouldBePresent)
   {
      this.shouldBePresent = shouldBePresent;
   }


   @Factory
   public static Matcher<File> isPresent() {
       return new FilePresentMatcher(true);
   }
   
   @Factory
   public static Matcher<File> isNotPresent() {
       return new FilePresentMatcher(false);
   }
   
   @Override
   public void describeTo(Description description)
   {
      description.appendText("A" + (shouldBePresent ? "n" : " NOT") + " existing file/directory.");
      
   }

   @Override
   protected boolean matchesSafely(File item, Description mismatchDescription)
   {
      if (item.exists() != shouldBePresent)
      {
         mismatchDescription.appendText( (item.isDirectory() ? "Directory '" : "File '") 
            + item.getName() + "' did " + (item.exists() ? "" : " NOT ")
            + " exist.\nFull Path: " + item.getAbsolutePath());
         return false;
      }
      return true;
   }
}
