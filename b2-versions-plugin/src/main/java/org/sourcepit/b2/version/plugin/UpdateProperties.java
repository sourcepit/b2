/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.sourcepit.b2.version.plugin;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.stream.XMLStreamException;

import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.mojo.versions.api.PomHelper;
import org.codehaus.mojo.versions.rewriting.ModifiedPomXMLEventReader;
import org.sourcepit.b2.version.plugin.internal.ModuleVersionsMojo;
import org.sourcepit.b2.version.plugin.internal.Property;

/**
 * @author eddi-weiss
 */
@Mojo(requiresProject = true, name = "update-properties", defaultPhase = LifecyclePhase.POST_CLEAN)
public class UpdateProperties extends ModuleVersionsMojo
{

   @Parameter(name = "properties", required = false, readonly = true)
   private Property[] properties;


   @Override
   protected void updateModule(ModifiedPomXMLEventReader reader, Model projectModel) throws MojoExecutionException,
      MojoFailureException, XMLStreamException
   {
      updateProperties(reader, projectModel.getProperties(), null);
      Set<String> activeProfiles = new TreeSet<String>();
      for (Profile profile : project.getActiveProfiles())
      {
         activeProfiles.add(profile.getId());
      }

      for (Profile profile : projectModel.getProfiles())
      {
         if (activeProfiles.contains(profile.getId()))
         {
            updateProperties(reader, profile.getProperties(), profile.getId());
         }

      }
   }

   private void updateProperties(ModifiedPomXMLEventReader reader, Properties modelProperites, String profileId)
      throws MojoExecutionException, XMLStreamException
   {
      Set<Object> keySet = modelProperites.keySet();
      for (Property prop : properties)
      {
         if (keySet.contains(prop.getName()))
         {
            updatePropertyToNewestVersion(reader, prop, profileId, modelProperites.getProperty(prop.getName()));
         }
      }
   }


   private void updatePropertyToNewestVersion(ModifiedPomXMLEventReader reader, Property property, String profileId,
      String oldValue) throws MojoExecutionException, XMLStreamException
   {
      final String newValue = property.getValue();
      final String name = property.getName();
      if (PomHelper.setPropertyVersion(reader, profileId, name, newValue))
      {
         getLog().info("Updated ${" + name + "} from " + oldValue + " to " + newValue);
      }
   }


}
