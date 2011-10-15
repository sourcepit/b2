/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common.internal.impl;

import java.util.Locale;

import org.eclipse.emf.ecore.EDataType;

public aspect CommonModelFactoryImplAspect
{
   pointcut createELocaleFromString(EDataType eDataType, String initialValue):  args(eDataType, initialValue) && execution(Locale CommonModelFactoryImpl.createELocaleFromString(EDataType, String));

   Locale around(EDataType eDataType, String initialValue) : createELocaleFromString(eDataType, initialValue) {
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
