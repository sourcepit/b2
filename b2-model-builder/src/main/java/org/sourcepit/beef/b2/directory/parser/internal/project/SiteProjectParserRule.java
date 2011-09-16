/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.project;

import java.io.File;

import javax.inject.Named;

import org.sourcepit.beef.b2.common.internal.utils.XmlUtils;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.module.ModuleFactory;
import org.sourcepit.beef.b2.model.module.SiteProject;

@Named("site")
public class SiteProjectParserRule extends AbstractProjectParserRule<SiteProject>
{
   @Override
   public SiteProject parse(File directory, IConverter converter)
   {
      try
      {
         XmlUtils.readXml(new File(directory, "site.xml"));

         final SiteProject siteProject = ModuleFactory.eINSTANCE.createSiteProject();
         siteProject.setDirectory(directory);
         siteProject.setId(directory.getName());
         siteProject.setVersion(converter.getModuleVersion());
         return siteProject;
      }
      catch (IllegalArgumentException e)
      { // no site project
      }
      return null;
   }
}
