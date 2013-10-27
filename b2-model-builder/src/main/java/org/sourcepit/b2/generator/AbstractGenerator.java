/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.generator;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.files.ModuleFiles;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.common.utils.props.PropertiesSource;

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

   @Override
   public abstract void generate(EObject inputElement, PropertiesSource properties, ITemplates templates,
      ModuleFiles moduleFiles);

   public int compareTo(IB2GenerationParticipant other)
   {
      return getGeneratorType().compareTo(other.getGeneratorType());
   }
}
