/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.common.internal.impl;

import java.util.Locale;

import org.eclipse.emf.ecore.EDataType;

public class CCommonFactoryImpl extends CommonFactoryImpl
{
   @Override
   public Locale createELocaleFromString(EDataType eDataType, String initialValue)
   {
      if (initialValue == null)
      {
         return null;
      }
      String[] groups = initialValue.split("_");
      final String[] segments = new String[3];
      for (int i = 0; i < segments.length; i++)
      {
         String group = groups.length > i ? groups[i] : null;
         segments[i] = group == null ? "" : group.startsWith("_") ? group.substring(1) : group;
      }
      return new Locale(segments[0], segments[1], segments[2]);
   }
}
