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

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;

import javax.inject.Named;

import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Named("feature")
public class FeatureProjectParserRule extends AbstractProjectParserRule<FeatureProject>
   implements
      ProjectDetectionRule<FeatureProject>,
      ProjectModelInitializationParticipant {
   @Override
   public FeatureProject detect(File directory, PropertiesSource properties) {
      return parse(directory, properties);
   }

   @Override
   public FeatureProject parse(File directory, PropertiesSource properties) {
      final File featureXmlFile = new File(directory, "feature.xml");

      final Document featureXml;
      try {
         featureXml = XmlUtils.readXml(featureXmlFile);
      }
      catch (IllegalArgumentException e) {
         return null;
      }

      final Element featureElem = featureXml.getDocumentElement();
      if (featureElem == null || !"feature".equals(featureElem.getTagName())) {
         return null;
      }

      final FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
      featureProject.setDirectory(directory);

      return featureProject;
   }

   @Override
   public void initialize(Project project, PropertiesSource properties) {
      if (project instanceof FeatureProject) {
         initializeeee((FeatureProject) project, properties);
      }
   }

   @Override
   public void initializeeee(FeatureProject featureProject, PropertiesSource properties) {
      final File directory = featureProject.getDirectory();

      final File featureXmlFile = new File(directory, "feature.xml");

      final Element featureElem = XmlUtils.readXml(featureXmlFile).getDocumentElement();

      final String featureId = featureElem.getAttribute("id");
      final String featureVersion = featureElem.getAttribute("version");

      featureProject.setId(featureId);
      featureProject.setVersion(featureVersion);

      for (Node node : XmlUtils.queryNodes(XmlUtils.readXml(featureXmlFile), "/feature/includes")) {
         final Element includeElem = (Element) node;
         final FeatureInclude fi = ModuleModelFactory.eINSTANCE.createFeatureInclude();
         final String id = includeElem.getAttribute("id");
         if (id == null || id.length() == 0) {
            throw new IllegalArgumentException("Include id in " + featureXmlFile + " must not be empty");
         }
         fi.setId(id);
         featureProject.getIncludedFeatures().add(fi);
      }

      for (Node node : XmlUtils.queryNodes(XmlUtils.readXml(featureXmlFile), "/feature/plugin")) {
         final Element pluginElem = (Element) node;
         final PluginInclude pi = ModuleModelFactory.eINSTANCE.createPluginInclude();
         final String id = pluginElem.getAttribute("id");
         if (id == null || id.length() == 0) {
            throw new IllegalArgumentException("Plugin id in " + featureXmlFile + " must not be empty");
         }
         pi.setId(id);
         final String version = pluginElem.getAttribute("version");
         if (version != null && version.length() > 0) {
            pi.setVersion(version);
         }
         final String unpack = pluginElem.getAttribute("unpack");
         if (unpack != null && unpack.length() > 0) {
            pi.setUnpack(Boolean.valueOf(unpack).booleanValue());
         }
         featureProject.getIncludedPlugins().add(pi);
      }
   }
}
