/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.module.AbstractModuleParserExtender;
import org.sourcepit.b2.directory.parser.internal.module.IModuleParserExtender;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.UIDetector;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.module.PluginProject;

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
   protected void doExtend(Annotatable modelElement, IConverter converter)
   {
      final PluginProject pluginProject = (PluginProject) modelElement;
      modelElement.putAnnotationEntry("project.properties", "bundle.symbolicName", pluginProject.getId());
      modelElement.putAnnotationEntry("project.properties", "bundle.requiresUI",
         Boolean.toString(uiDetector.requiresUI(pluginProject, converter)));
   }
}
