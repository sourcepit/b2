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

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.common.utils.props.PropertiesSource;


public interface IB2GenerationParticipant extends Comparable<IB2GenerationParticipant> {
   boolean isGeneratorInput(EObject eObject);

   GeneratorType getGeneratorType();

   boolean isReverse();

   void generate(EObject inputElement, PropertiesSource properties, ITemplates templates,
      ModuleDirectory moduleDirectory);
}
