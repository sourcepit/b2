/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.generator;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.model.builder.util.IConverter;


public interface IB2GenerationParticipant extends Comparable<IB2GenerationParticipant>
{
   boolean isGeneratorInput(EObject eObject);

   GeneratorType getGeneratorType();

   boolean isReverse();

   void generate(EObject inputElement, IConverter converter, ITemplates templates);
}
