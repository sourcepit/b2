/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.module;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.module.Annotateable;

public abstract class AbstractModuleParserExtender implements IModuleParserExtender
{
   private Collection<Class<? extends Annotateable>> inputTypes;

   protected Collection<Class<? extends Annotateable>> getInputTypes()
   {
      if (inputTypes == null)
      {
         inputTypes = new ArrayList<Class<? extends Annotateable>>();
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

   protected abstract void addInputTypes(Collection<Class<? extends Annotateable>> inputTypes);

   public void extend(Annotateable modelElement, IConverter converter)
   {
      if (isInputType(modelElement.getClass()))
      {
         doExtend(modelElement, converter);
      }
   }

   protected abstract void doExtend(Annotateable modelElement, IConverter converter);
}
