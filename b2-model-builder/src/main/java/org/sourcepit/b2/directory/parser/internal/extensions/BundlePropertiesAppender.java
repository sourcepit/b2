/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.module.AbstractModuleParserExtender;
import org.sourcepit.b2.directory.parser.internal.module.IModuleParserExtender;
import org.sourcepit.b2.model.builder.util.UIDetector;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.modeling.common.Annotatable;

@Named
public class BundlePropertiesAppender extends AbstractModuleParserExtender implements IModuleParserExtender
{
   @Inject
   private UIDetector uiDetector;

   @Override
   protected void addInputTypes(Collection<Class<? extends Annotatable>> inputTypes)
   {
      inputTypes.add(PluginProject.class);
   }

   @Override
   protected void doExtend(Annotatable modelElement, PropertiesSource properties)
   {
      final PluginProject pluginProject = (PluginProject) modelElement;
      modelElement.setAnnotationData("project.properties", "bundle.symbolicName", pluginProject.getId());
      modelElement.setAnnotationData("project.properties", "bundle.requiresUI",
         Boolean.toString(uiDetector.requiresUI(pluginProject, properties)));
   }
}
