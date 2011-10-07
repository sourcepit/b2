/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
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