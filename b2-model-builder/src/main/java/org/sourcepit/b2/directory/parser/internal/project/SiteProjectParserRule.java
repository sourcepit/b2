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

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.xml.XmlUtils;

@Named("site")
public class SiteProjectParserRule extends AbstractProjectParserRule<SiteProject>
   implements
      ProjectDetectionRule<SiteProject>
{
   @Inject
   private BasicConverter converter;

   @Override
   public SiteProject detect(File directory, PropertiesSource properties)
   {
      return parse(directory, properties);
   }

   @Override
   public SiteProject parse(File directory, PropertiesSource properties)
   {
      try
      {
         XmlUtils.readXml(new File(directory, "site.xml"));
         final SiteProject siteProject = ModuleModelFactory.eINSTANCE.createSiteProject();
         siteProject.setDirectory(directory);
         return siteProject;
      }
      catch (IllegalArgumentException e)
      {
         return null;
      }
   }

   @Override
   public void initializeeee(SiteProject siteProject, PropertiesSource properties)
   {
      siteProject.setId(siteProject.getDirectory().getName());
      siteProject.setVersion(this.converter.getModuleVersion(properties));
   }
}
