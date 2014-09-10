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

package org.sourcepit.b2.validation;

import static org.sourcepit.common.manifest.osgi.BundleHeaderName.BUNDLE_REQUIREDEXECUTIONENVIRONMENT;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.base.Strings;

@Named("executionEnvironment")
public class ExecutionEnvironmentConstraint implements ModuleValidationConstraint
{

   private Logger logger;

   private static class ComplianceSetting
   {
      private final String compliance;
      private final String target;
      private final String source;

      public ComplianceSetting(String compliance, String target, String source)
      {
         this.compliance = compliance;
         this.target = target;
         this.source = source;
      }

      public String getCompliance()
      {
         return compliance;
      }

      public String getTarget()
      {
         return target;
      }

      public String getSource()
      {
         return source;
      }
   }

   private final static Map<String, ComplianceSetting> COMPLIANCE = new HashMap<String, ExecutionEnvironmentConstraint.ComplianceSetting>();

   static
   {
      COMPLIANCE.put("J2SE-1.3", new ComplianceSetting("1.3", "1.1", "1.3"));
      COMPLIANCE.put("J2SE-1.4", new ComplianceSetting("1.4", "1.2", "1.3"));
      COMPLIANCE.put("J2SE-1.5", new ComplianceSetting("1.5", "1.5", "1.5"));
      COMPLIANCE.put("JavaSE-1.6", new ComplianceSetting("1.6", "1.6", "1.6"));
      COMPLIANCE.put("JavaSE-1.7", new ComplianceSetting("1.7", "1.7", "1.7"));
   }

   @Inject
   public ExecutionEnvironmentConstraint(Logger logger)
   {
      this.logger = logger;
   }

   public void validate(EObject eObject, PropertiesSource properties, boolean quickFixesEnabled)
   {
      if (eObject instanceof PluginProject)
      {
         PluginProject project = (PluginProject) eObject;
         if (!project.isDerived() && hasJavaNature(project.getDirectory()))
         {
            String id = project.getId();

            String ee = properties.get("b2.executionEnvironment." + id);
            if (Strings.isNullOrEmpty(ee))
            {
               ee = properties.get("b2.executionEnvironment");
            }

            if (!Strings.isNullOrEmpty(ee))
            {
               validateClasspath(project, ee, quickFixesEnabled);
               validateCompliance(project, ee, quickFixesEnabled);
               validateMF(project, ee, quickFixesEnabled);
            }
         }
      }
   }

   private void validateMF(PluginProject project, String ee, boolean quickFixesEnabled)
   {
      final BundleManifest manifest = project.getBundleManifest();

      final String actualEE = manifest.getHeaderValue(BUNDLE_REQUIREDEXECUTIONENVIRONMENT);
      if (!ee.equals(actualEE))
      {
         final StringBuilder msg = new StringBuilder();
         msg.append(project.getId());
         msg.append(": Expected value for manifest header 'Bundle-RequiredExecutionEnvironment' is '");
         msg.append(ee);
         msg.append("' but is '");
         msg.append(actualEE);
         msg.append("'.");

         if (quickFixesEnabled)
         {
            msg.append(" (applied quick fix)");
            manifest.setHeader(BUNDLE_REQUIREDEXECUTIONENVIRONMENT, ee);
            EclipseBundleShapeConstraint.save(project.getDirectory(), manifest);
         }
         else
         {
            msg.append(" (quick fix available)");
         }

         logger.warn(msg.toString());
      }
   }

   private void validateCompliance(PluginProject project, String ee, boolean quickFixesEnabled)
   {
      final ComplianceSetting setting = COMPLIANCE.get(ee);
      if (setting != null)
      {
         final PropertiesMap jdtPrefs = new LinkedPropertiesMap();
         final File prefsFile = new File(project.getDirectory(), ".settings/org.eclipse.jdt.core.prefs");
         if (prefsFile.exists())
         {
            jdtPrefs.load(prefsFile);
         }

         boolean changed = false;

         final String compliance = jdtPrefs.get("org.eclipse.jdt.core.compiler.compliance");
         if (!setting.getCompliance().equals(compliance))
         {
            jdtPrefs.put("org.eclipse.jdt.core.compiler.compliance", setting.getCompliance());
            warn(project, "compiler compliance level", setting.getCompliance(), compliance, quickFixesEnabled);
            changed = true;
         }

         final String target = jdtPrefs.get("org.eclipse.jdt.core.compiler.codegen.targetPlatform");
         if (!setting.getTarget().equals(target))
         {
            jdtPrefs.put("org.eclipse.jdt.core.compiler.codegen.targetPlatform", setting.getTarget());
            warn(project, "compiler target compatibility", setting.getTarget(), target, quickFixesEnabled);
            changed = true;
         }

         final String source = jdtPrefs.get("org.eclipse.jdt.core.compiler.source");
         if (!setting.getSource().equals(source))
         {
            jdtPrefs.put("org.eclipse.jdt.core.compiler.source", setting.getSource());
            warn(project, "compiler source compatibility", setting.getSource(), source, quickFixesEnabled);
            changed = true;
         }

         if (quickFixesEnabled && changed)
         {
            jdtPrefs.store(prefsFile);
         }
      }
   }

   private void warn(PluginProject project, String what, String expected, final String actual, boolean quickFixesEnabled)
   {
      final StringBuilder msg = new StringBuilder();
      msg.append(project.getId());
      msg.append(": Expected ");
      msg.append(what);
      msg.append(" ");
      msg.append(expected);
      msg.append(" but is ");
      msg.append(actual);
      msg.append(".");

      if (quickFixesEnabled)
      {
         msg.append(" (applied quick fix)");
      }
      else
      {
         msg.append(" (quick fix available)");
      }

      logger.warn(msg.toString());
   }

   private void validateClasspath(PluginProject project, String ee, boolean quickFixesEnabled)
   {
      final File cpFile = new File(project.getDirectory(), ".classpath");

      final Document cpDoc;
      if (cpFile.exists())
      {
         cpDoc = XmlUtils.readXml(cpFile);
      }
      else
      {
         cpDoc = XmlUtils.newDocument();
      }

      Element eeNode = (Element) XmlUtils
         .queryNode(
            cpDoc,
            "/classpath/classpathentry[@kind='con' and starts-with(@path,'org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/')]");

      final String eeName;
      if (eeNode == null)
      {
         eeName = null;
      }
      else
      {
         final String eePath = eeNode.getAttribute("path");
         eeName = eePath.substring(eePath.lastIndexOf('/') + 1);
      }

      if (!ee.equals(eeName))
      {
         final StringBuilder msg = new StringBuilder();
         msg.append(project.getId());
         msg.append(": Expected execution environment '");
         msg.append(ee);
         msg.append("' but was '");
         msg.append(eeName);
         msg.append("'.");

         if (quickFixesEnabled)
         {
            msg.append(" (applied quick fix)");

            if (eeNode == null)
            {
               Node cpNode = cpDoc.getFirstChild();
               if (cpNode == null)
               {
                  cpNode = cpDoc.createElement("classpath");
                  cpDoc.appendChild(cpNode);
               }
               eeNode = cpDoc.createElement("classpathentry");
               eeNode.setAttribute("kind", "con");
               cpNode.appendChild(eeNode);
            }

            eeNode.setAttribute("path",
               "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/"
                  + ee);

            XmlUtils.writeXml(cpDoc, cpFile);
         }
         else
         {
            msg.append(" (quick fix available)");
         }

         logger.warn(msg.toString());
      }
   }

   private boolean hasJavaNature(File projectDir)
   {
      final File prjFile = new File(projectDir, ".project");
      if (prjFile.exists())
      {
         final Document doc = XmlUtils.readXml(prjFile);
         final Node node = XmlUtils.queryNode(doc,
            "/projectDescription/natures[nature='org.eclipse.jdt.core.javanature']");
         return node != null;
      }
      return false;
   }
}
