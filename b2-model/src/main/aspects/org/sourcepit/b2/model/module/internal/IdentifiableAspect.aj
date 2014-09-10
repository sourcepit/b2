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

package org.sourcepit.b2.model.module.internal;

import org.sourcepit.b2.model.module.internal.util.IdentifiableUtils;
import org.sourcepit.b2.model.module.util.Identifiable;
import org.sourcepit.b2.model.module.util.Identifier;

public aspect IdentifiableAspect
{
   pointcut isIdentifyableBy(Identifiable identifiable, Identifier identifier): target(identifiable) && args(identifier) && execution(boolean isIdentifyableBy(Identifier));

   pointcut toIdentifier(Identifiable identifiable): target(identifiable)  && execution(Identifier toIdentifier());

   boolean around(Identifiable identifiable, Identifier identifier) : isIdentifyableBy(identifiable, identifier)
   {
      return IdentifiableUtils.isIdentifyableBy(identifiable, identifier);
   }

   Identifier around(Identifiable identifiable) : toIdentifier(identifiable)
   {
      return IdentifiableUtils.toIdentifier(identifiable);
   }
}
