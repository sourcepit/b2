/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.validation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.logging.Logger;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.utils.lang.Exceptions;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

@Named("projectEncoding")
public class ProjectEncodingConstraint implements ModuleValidationConstraint
{
   private Logger logger;

   @Inject
   public ProjectEncodingConstraint(Logger logger)
   {
      this.logger = logger;
   }

   public void validate(EObject eObject, PropertiesMap properties, boolean quickFixesEnabled)
   {
      if (eObject instanceof Project)
      {
         final String projectEncoding = properties.get("project.build.sourceEncoding");
         if (projectEncoding != null)
         {
            Project project = (Project) eObject;
            if (!project.isDerived())
            {
               validate(project, projectEncoding, quickFixesEnabled);
            }
         }
      }
   }

   private void validate(Project project, String expectedProjectEncoding, boolean quickFixesEnabled)
   {
      final PropertiesMap resourcePrefs = new LinkedPropertiesMap();
      final File resourcePrefsFile = new File(project.getDirectory(), ".settings/org.eclipse.core.resources.prefs");
      if (resourcePrefsFile.exists())
      {
         resourcePrefs.load(resourcePrefsFile);
      }

      final String actualProjectEncoding = resourcePrefs.get("encoding/<project>", "<not-set>");
      if (!expectedProjectEncoding.equals(actualProjectEncoding))
      {
         final StringBuilder msg = new StringBuilder();
         msg.append(project.getId());
         msg.append(": Expected project encoding ");
         msg.append(expectedProjectEncoding);
         msg.append(" but is ");
         msg.append(actualProjectEncoding);
         msg.append(".");

         if (quickFixesEnabled)
         {
            msg.append(" (applied quick fix)");
            resourcePrefs.put("encoding/<project>", expectedProjectEncoding);
            save(resourcePrefs, resourcePrefsFile);
         }
         else
         {
            msg.append(" (quick fix available)");
         }

         logger.warn(msg.toString());
      }
   }

   private void save(final PropertiesMap resourcePrefs, final File resourcePrefsFile)
   {
      OutputStream out = null;
      try
      {
         if (!resourcePrefsFile.exists())
         {
            resourcePrefsFile.getParentFile().mkdirs();
            resourcePrefsFile.createNewFile();
         }
         out = new BufferedOutputStream(new FileOutputStream(resourcePrefsFile));
         resourcePrefs.toJavaProperties().store(out, null);
      }
      catch (IOException e)
      {
         throw Exceptions.pipe(e);
      }
      finally
      {
         IOUtils.closeQuietly(out);
      }
   }
}
