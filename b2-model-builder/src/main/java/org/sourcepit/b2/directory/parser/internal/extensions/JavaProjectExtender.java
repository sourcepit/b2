/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import java.io.File;
import java.util.Collection;

import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.module.AbstractModuleParserExtender;
import org.sourcepit.b2.directory.parser.internal.module.IModuleParserExtender;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.xml.XmlUtils;
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
   protected void doExtend(Annotatable modelElement, PropertiesSource properties)
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
            modelElement.setAnnotationData("java", "source.paths", sb.toString());
         }

         for (Node node : XmlUtils.queryNodes(cpDoc,
            "/classpath/classpathentry[@kind='con' and starts-with(@path,'org.eclipse.jdt.launching.JRE_CONTAINER/')]"))
         {
            Element conEntry = (Element) node;
            String conPath = conEntry.getAttribute("path");
            String jreName = conPath.substring(conPath.lastIndexOf('/') + 1);
            modelElement.setAnnotationData("java", "jre.name", jreName);
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
            modelElement.setAnnotationData("java", "compiler.target", target);
         }

         final String source = jdtPrefs.get("org.eclipse.jdt.core.compiler.codegen.targetPlatform");
         if (source != null)
         {
            modelElement.setAnnotationData("java", "compiler.source", source);
         }
      }
   }
}
