/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import java.io.File;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Named;

import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.directory.parser.internal.module.AbstractModuleParserExtender;
import org.sourcepit.b2.directory.parser.internal.module.IModuleParserExtender;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.module.Project;

@Named
public class BuildPropertiesExtender extends AbstractModuleParserExtender implements IModuleParserExtender
{
   @Override
   protected void addInputTypes(Collection<Class<? extends Annotatable>> inputTypes)
   {
      inputTypes.add(Project.class);
   }

   @Override
   protected void doExtend(Annotatable modelElement, IConverter converter)
   {
      final Project project = (Project) modelElement;
      final File projectDir = project.getDirectory();
      final File propsFile = new File(projectDir, "build.properties");
      if (propsFile.exists())
      {
         final PropertiesMap props = new LinkedPropertiesMap();
         props.load(propsFile);
         for (Entry<String, String> entry : props.entrySet())
         {
            modelElement.putAnnotationEntry("build", entry.getKey(), entry.getValue());
         }
      }
   }
}
