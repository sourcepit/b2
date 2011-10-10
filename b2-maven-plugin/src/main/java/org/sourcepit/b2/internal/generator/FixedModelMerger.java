/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.Map;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.merge.ModelMerger;

public class FixedModelMerger extends ModelMerger
{
   // see https://jira.codehaus.org/browse/MNG-5153
   protected void mergeModel_Parent(Model target, Model source, boolean sourceDominant, Map<Object, Object> context)
   {
      final Parent src = source.getParent();
      if (src != null)
      {
         Parent tgt = target.getParent();
         if (tgt == null)
         {
            tgt = new Parent();
            tgt.setRelativePath(null);
            target.setParent(tgt);
         }
         mergeParent(tgt, src, sourceDominant, context);
      }
   }
}
