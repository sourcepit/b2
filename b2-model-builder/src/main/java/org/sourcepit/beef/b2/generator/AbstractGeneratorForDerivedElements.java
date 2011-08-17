/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.generator;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.internal.generator.ITemplates;
import org.sourcepit.beef.b2.internal.model.Derivable;
import org.sourcepit.beef.b2.model.builder.util.IConverter;

public abstract class AbstractGeneratorForDerivedElements extends AbstractGenerator
{
   @Override
   public boolean isGeneratorInput(EObject eObject)
   {
      return eObject instanceof Derivable ? isGeneratorInput((Derivable) eObject) : false;
   }

   protected boolean isGeneratorInput(Derivable derivable)
   {
      if (derivable.isDerived())
      {
         return isGeneratorInputType(derivable.getClass());
      }
      return false;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      final Collection<Class<? extends Derivable>> _inputTypes = new ArrayList<Class<? extends Derivable>>();
      addTypesOfInputs(_inputTypes);
      inputTypes.addAll(_inputTypes);
   }

   protected abstract void addTypesOfInputs(Collection<Class<? extends Derivable>> inputTypes);

   @Override
   public void generate(EObject inputElement, IConverter converter, ITemplates templates)
   {
      generate((Derivable) inputElement, converter, templates);
   }

   protected abstract void generate(Derivable inputElement, IConverter converter, ITemplates templates);
}
