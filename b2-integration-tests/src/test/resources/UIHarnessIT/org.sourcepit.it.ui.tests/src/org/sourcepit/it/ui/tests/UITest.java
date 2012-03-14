/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.it.ui.tests;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.widgets.Display;
import org.junit.Test;

public class UITest
{
   @Test
   public void testRunInUI()
   {
      assertThat(Display.getCurrent(), notNullValue());
   }
}
