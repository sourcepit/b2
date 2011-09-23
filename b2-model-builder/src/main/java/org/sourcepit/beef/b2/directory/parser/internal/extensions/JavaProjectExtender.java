/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.extensions;

import java.io.File;
import java.util.Collection;

import javax.inject.Named;

import org.sourcepit.beef.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.beef.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.beef.b2.common.internal.utils.XmlUtils;
import org.sourcepit.beef.b2.directory.parser.internal.module.AbstractModuleParserExtender;
import org.sourcepit.beef.b2.directory.parser.internal.module.IModuleParserExtender;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.common.Annotatable;
import org.sourcepit.beef.b2.model.module.PluginProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Named
public class JavaProjectExtender extends AbstractModuleParserExtender implements IModuleParserExtender
{
   @Override
   protected void addInputTypes(Collection<Class<? extends Annotatable>> inputTypes)
   {
      inputTypes.add(PluginProject.class);
   }

   @Override
   protected void doExtend(Annotatable modelElement, IConverter converter)
   {
      final PluginProject pluginProject = (PluginProject) modelElement;
      final File projectDir = pluginProject.getDirectory();
      processJdtSettings(modelElement, projectDir);
      processClasspath(modelElement, projectDir);
   }

   private void processClasspath(Annotatable modelElement, final File projectDir)
   {
      final File cpFile = new File(projectDir, ".classpath");
      if (cpFile.exists())
      {
         final Document cpDoc = XmlUtils.readXml(cpFile);
         StringBuilder sb = new StringBuilder();
         for (Node node : XmlUtils.queryNodes(cpDoc, "/classpath/classpathentry[@kind='src']"))
         {
            Element srcEntry = (Element) node;
            String srcPath = srcEntry.getAttribute("path");
            sb.append(',');
            sb.append(srcPath);
         }
         if (sb.length() > 0)
         {
            sb.deleteCharAt(0);
            modelElement.putAnnotationEntry("java", "source.paths", sb.toString());
         }

         for (Node node : XmlUtils.queryNodes(cpDoc,
            "/classpath/classpathentry[@kind='con' and starts-with(@path,'org.eclipse.jdt.launching.JRE_CONTAINER/')]"))
         {
            Element conEntry = (Element) node;
            String conPath = conEntry.getAttribute("path");
            String jreName = conPath.substring(conPath.lastIndexOf('/') + 1);
            modelElement.putAnnotationEntry("java", "jre.name", jreName);
         }
      }
   }

   private void processJdtSettings(Annotatable modelElement, final File projectDir)
   {
      final File prefFile = new File(projectDir, ".settings/org.eclipse.jdt.core.prefs");
      if (prefFile.exists())
      {
         final PropertiesMap jdtPrefs = new LinkedPropertiesMap();
         jdtPrefs.load(prefFile);

         final String target = jdtPrefs.get("org.eclipse.jdt.core.compiler.codegen.targetPlatform");
         if (target != null)
         {
            modelElement.putAnnotationEntry("java", "compiler.target", target);
         }

         final String source = jdtPrefs.get("org.eclipse.jdt.core.compiler.codegen.targetPlatform");
         if (source != null)
         {
            modelElement.putAnnotationEntry("java", "compiler.source", source);
         }
      }
   }
}
