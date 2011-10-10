/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.common.Annotatable;

public abstract class AbstractModuleParserExtender implements IModuleParserExtender
{
   private Collection<Class<? extends Annotatable>> inputTypes;

   protected Collection<Class<? extends Annotatable>> getInputTypes()
   {
      if (inputTypes == null)
      {
         inputTypes = new ArrayList<Class<? extends Annotatable>>();
         addInputTypes(inputTypes);
      }
      return inputTypes;
   }

   protected boolean isInputType(Class<? extends EObject> clazz)
   {
      for (Class<? extends EObject> inputType : getInputTypes())
      {
         if (inputType.isAssignableFrom(clazz))
         {
            return true;
         }
      }
      return false;
   }

   protected abstract void addInputTypes(Collection<Class<? extends Annotatable>> inputTypes);

   public void extend(Annotatable modelElement, IConverter converter)
   {
      if (isInputType(modelElement.getClass()))
      {
         doExtend(modelElement, converter);
      }
   }

   protected abstract void doExtend(Annotatable modelElement, IConverter converter);
}
