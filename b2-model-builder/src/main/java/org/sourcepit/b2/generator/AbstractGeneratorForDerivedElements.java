/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
   public void generate(EObject inputElement, PropertiesSource properties, ITemplates templates,
      ModuleDirectory moduleDirectory)
   {
      generate((Derivable) inputElement, properties, templates);
   }

   protected abstract void generate(Derivable inputElement, PropertiesSource properties, ITemplates templates);
}
