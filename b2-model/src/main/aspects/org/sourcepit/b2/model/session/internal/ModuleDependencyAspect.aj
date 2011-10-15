/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal;

import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.internal.util.ModuleDependencyUtils;

public aspect ModuleDependencyAspect
{
   pointcut isSatisfiableBy(ModuleDependency dependency, ModuleProject project) : target(dependency) && args(project) && execution(boolean isSatisfiableBy(ModuleProject));

   boolean around(ModuleDependency dependency, ModuleProject project) : isSatisfiableBy(dependency, project)
   {
      return ModuleDependencyUtils.isSatisfiableBy(dependency, project);
   }
}
