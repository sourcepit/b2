/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;

import javax.inject.Named;

import org.sourcepit.b2.common.internal.utils.XmlUtils;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.SiteProject;

@Named("site")
public class SiteProjectParserRule extends AbstractProjectParserRule<SiteProject>
{
   @Override
   public SiteProject parse(File directory, IConverter converter)
   {
      try
      {
         XmlUtils.readXml(new File(directory, "site.xml"));

         final SiteProject siteProject = ModuleModelFactory.eINSTANCE.createSiteProject();
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
