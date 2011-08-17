/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.generator;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.internal.generator.ITemplates;
import org.sourcepit.beef.b2.model.builder.util.IConverter;

public abstract class AbstractGenerator implements IB2GenerationParticipant
{
   private Collection<Class<? extends EObject>> inputTypes;

   /**
    * {@inheritDoc}
    */
   public boolean isGeneratorInput(EObject eObject)
   {
      return isGeneratorInputType(eObject.getClass());
   }

   protected boolean isGeneratorInputType(Class<? extends EObject> clazz)
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

   protected Collection<Class<? extends EObject>> getInputTypes()
   {
      if (inputTypes == null)
      {
         inputTypes = new ArrayList<Class<? extends EObject>>();
         addInputTypes(inputTypes);
      }
      return inputTypes;
   }

   protected abstract void addInputTypes(Collection<Class<? extends EObject>> inputTypes);

   /**
    * {@inheritDoc}
    */
   public abstract GeneratorType getGeneratorType();

   public boolean isReverse()
   {
      return false;
   }

   /**
    * {@inheritDoc}
    */
   public abstract void generate(EObject inputElement, IConverter converter, ITemplates templates);

   public int compareTo(IB2GenerationParticipant other)
   {
      return getGeneratorType().compareTo(other.getGeneratorType());
   }
}
