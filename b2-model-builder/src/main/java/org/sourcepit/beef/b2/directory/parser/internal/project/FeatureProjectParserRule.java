/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.project;

import java.io.File;

import javax.inject.Named;

import org.sourcepit.beef.b2.common.internal.utils.XmlUtils;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.module.FeatureInclude;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.ModuleFactory;
import org.sourcepit.beef.b2.model.module.PluginInclude;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Named("feature")
public class FeatureProjectParserRule extends AbstractProjectParserRule<FeatureProject>
{
   @Override
   public FeatureProject parse(File directory, IConverter converter)
   {
      final File featureXmlFile = new File(directory, "feature.xml");

      final Document featureXml;
      try
      {
         featureXml = XmlUtils.readXml(featureXmlFile);
      }
      catch (IllegalArgumentException e)
      {
         return null;
      }

      final Element featureElem = featureXml.getDocumentElement();
      if (featureElem == null || !"feature".equals(featureElem.getTagName()))
      {
         return null;
      }

      final String featureId = featureElem.getAttribute("id");
      final String featureVersion = featureElem.getAttribute("version");

      final FeatureProject featureProject = ModuleFactory.eINSTANCE.createFeatureProject();
      featureProject.setDirectory(directory);
      featureProject.setId(featureId);
      featureProject.setVersion(featureVersion);

      for (Node node : XmlUtils.queryNodes(featureXml, "/feature/includes"))
      {
         final Element includeElem = (Element) node;
         final FeatureInclude fi = ModuleFactory.eINSTANCE.createFeatureInclude();
         final String id = includeElem.getAttribute("id");
         if (id == null || id.length() == 0)
         {
            throw new IllegalArgumentException("Include id in " + featureXmlFile + " must not be empty");
         }
         fi.setId(id);
         featureProject.getIncludedFeatures().add(fi);
      }

      for (Node node : XmlUtils.queryNodes(featureXml, "/feature/plugin"))
      {
         final Element pluginElem = (Element) node;
         final PluginInclude pi = ModuleFactory.eINSTANCE.createPluginInclude();
         final String id = pluginElem.getAttribute("id");
         if (id == null || id.length() == 0)
         {
            throw new IllegalArgumentException("Plugin id in " + featureXmlFile + " must not be empty");
         }
         pi.setId(id);
         final String version = pluginElem.getAttribute("version");
         if (version != null && version.length() > 0)
         {
            pi.setVersionRange(version);
         }
         final String unpack = pluginElem.getAttribute("unpack");
         if (unpack != null && unpack.length() > 0)
         {
            pi.setUnpack(Boolean.valueOf(unpack));
         }
         featureProject.getIncludedPlugins().add(pi);
      }

      return featureProject;
   }
}
