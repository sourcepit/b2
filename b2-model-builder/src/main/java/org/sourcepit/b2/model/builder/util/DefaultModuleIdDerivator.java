/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.session.ModuleProject;

@Named
public class DefaultModuleIdDerivator implements ModuleIdDerivator
{
   private final B2SessionService sessionService;

   @Inject
   public DefaultModuleIdDerivator(B2SessionService sessionService)
   {
      this.sessionService = sessionService;
   }

   public String deriveModuleId(File moduleDir)
   {
      ModuleProject project = sessionService.getCurrentSession().getCurrentProject();
      return beautify(resolveSymbolicName(project.getGroupId(), project.getArtifactId()));
   }

   private String beautify(String symbolicName)
   {
      if (symbolicName == null)
      {
         return null;
      }
      final StringBuilder sb = new StringBuilder();
      for (char c : symbolicName.toCharArray())
      {
         switch (c)
         {
            case '-' :
            case '_' :
               sb.append('.');
               break;
            default :
               sb.append(c);
               break;
         }
      }
      return sb.toString();
   }

   private String resolveSymbolicName(String groupId, String artifactId)
   {
      final String[] segments = groupId.split("\\.");
      final StringBuilder sb = new StringBuilder();
      sb.append(groupId);

      String idPrefix = groupId;
      if (!artifactId.startsWith(idPrefix))
      {
         idPrefix = segments[segments.length - 1];
      }

      if (artifactId.startsWith(idPrefix))
      {
         final String appendix = artifactId.substring(idPrefix.length());
         boolean trim = true;
         for (char c : appendix.toCharArray())
         {
            switch (c)
            {
               case '-' :
               case '_' :
               case '.' :
                  if (trim)
                  {
                     break;
                  }
               default :
                  if (trim)
                  {
                     sb.append('.');
                  }
                  trim = false;
                  sb.append(c);
                  break;
            }
         }
      }
      else
      {
         sb.append('.');
         sb.append(artifactId);
      }
      return sb.toString();
   }
}
