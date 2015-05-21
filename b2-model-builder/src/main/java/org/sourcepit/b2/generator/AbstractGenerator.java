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
import org.sourcepit.common.utils.props.PropertiesSource;

public abstract class AbstractGenerator implements IB2GenerationParticipant {
   private Collection<Class<? extends EObject>> inputTypes;

   /**
    * {@inheritDoc}
    */
   public boolean isGeneratorInput(EObject eObject) {
      return isGeneratorInputType(eObject.getClass());
   }

   protected boolean isGeneratorInputType(Class<? extends EObject> clazz) {
      for (Class<? extends EObject> inputType : getInputTypes()) {
         if (inputType.isAssignableFrom(clazz)) {
            return true;
         }
      }
      return false;
   }

   protected Collection<Class<? extends EObject>> getInputTypes() {
      if (inputTypes == null) {
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

   public boolean isReverse() {
      return false;
   }

   @Override
   public abstract void generate(EObject inputElement, PropertiesSource properties, ITemplates templates,
      ModuleDirectory moduleDirectory);

   public int compareTo(IB2GenerationParticipant other) {
      return getGeneratorType().compareTo(other.getGeneratorType());
   }
}
