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

package org.sourcepit.b2.directory.parser.internal.extensions;

import java.io.File;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.module.AbstractModuleParserExtender;
import org.sourcepit.b2.directory.parser.internal.module.IModuleParserExtender;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class BuildPropertiesExtender extends AbstractModuleParserExtender implements IModuleParserExtender
{
   @Override
   protected void addInputTypes(Collection<Class<? extends Annotatable>> inputTypes)
   {
      inputTypes.add(Project.class);
   }

   @Override
   protected void doExtend(Annotatable modelElement, PropertiesSource properties)
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
            modelElement.setAnnotationData("build", entry.getKey(), entry.getValue());
         }
      }
   }
}
