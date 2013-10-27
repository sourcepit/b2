/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.generator;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.common.utils.props.PropertiesSource;

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
      final Collection<Class<? extends Derivable>> typesOfInputs = new ArrayList<Class<? extends Derivable>>();
      addTypesOfInputs(typesOfInputs);
      inputTypes.addAll(typesOfInputs);
   }

   protected abstract void addTypesOfInputs(Collection<Class<? extends Derivable>> inputTypes);

   @Override
   public void generate(EObject inputElement, PropertiesSource properties, ITemplates templates, ModuleDirectory moduleDirectory)
   {
      generate((Derivable) inputElement, properties, templates);
   }

   protected abstract void generate(Derivable inputElement, PropertiesSource properties, ITemplates templates);
}
