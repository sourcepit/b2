/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
{
   @Inject
   private BasicConverter converter;

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
   public void initialize(SiteProject siteProject, PropertiesSource properties)
   {
      siteProject.setId(siteProject.getDirectory().getName());
      siteProject.setVersion(this.converter.getModuleVersion(properties));
   }
}
