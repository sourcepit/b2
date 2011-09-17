/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.common;

import java.util.Locale;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EDataType;

public class ELocaleTest extends TestCase
{
   public void testConvert() throws Exception
   {
      EDataType eLocaleType = CommonPackage.eINSTANCE.getELocale();
      CommonFactory converter = CommonFactory.eINSTANCE;

      assertNull(converter.convertToString(eLocaleType, null));
      assertEquals("", converter.convertToString(eLocaleType, new Locale("")));
      assertEquals("de", converter.convertToString(eLocaleType, new Locale("de")));
      assertEquals("de_DE", converter.convertToString(eLocaleType, new Locale("de", "DE")));
      assertEquals("de_DE_SCHWÄBISCH", converter.convertToString(eLocaleType, new Locale("de", "DE", "SCHWÄBISCH")));
   }

   public void testFromString() throws Exception
   {
      EDataType eLocaleType = CommonPackage.eINSTANCE.getELocale();
      CommonFactory converter = CommonFactory.eINSTANCE;

      assertNull(converter.convertToString(eLocaleType, null));
      assertEquals("", converter.convertToString(eLocaleType, new Locale("")));
      assertEquals("de", converter.convertToString(eLocaleType, new Locale("de")));
      assertEquals("de_DE", converter.convertToString(eLocaleType, new Locale("de", "DE")));
      assertEquals("de_DE_SCHWÄBISCH", converter.convertToString(eLocaleType, new Locale("de", "DE", "SCHWÄBISCH")));

      assertNull(converter.createFromString(eLocaleType, null));
      assertEquals(new Locale(""), converter.createFromString(eLocaleType, ""));
      assertEquals(new Locale("de", "DE"), converter.createFromString(eLocaleType, "de_DE"));
      assertEquals(new Locale("de", "DE", "SCHWÄBISCH"), converter.createFromString(eLocaleType, "de_DE_SCHWÄBISCH"));
      assertEquals(new Locale(""), converter.createFromString(eLocaleType, "______"));
   }
}
