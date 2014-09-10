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

package org.sourcepit.b2.directory.parser.internal.module;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.PropertiesSource;

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

   public void extend(Annotatable modelElement, PropertiesSource properties)
   {
      if (isInputType(modelElement.getClass()))
      {
         doExtend(modelElement, properties);
      }
   }

   protected abstract void doExtend(Annotatable modelElement, PropertiesSource properties);
}
