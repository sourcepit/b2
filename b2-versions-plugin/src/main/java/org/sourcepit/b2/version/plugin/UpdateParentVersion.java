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

import javax.xml.stream.XMLStreamException;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.mojo.versions.api.PomHelper;
import org.codehaus.mojo.versions.rewriting.ModifiedPomXMLEventReader;
import org.sourcepit.b2.version.plugin.internal.ModuleVersionsMojo;

/**
 * @author eddi-weiss
 */
@Mojo(requiresProject = true, name = "update-parent", defaultPhase = LifecyclePhase.POST_CLEAN)
public class UpdateParentVersion extends ModuleVersionsMojo {

   @Parameter(name = "parentVersion", required = true, readonly = true, defaultValue = "${parentVersion}")
   private String parentVersion = null;

   @Override
   protected void updateModule(ModifiedPomXMLEventReader reader, Model projectModel) throws MojoExecutionException,
      MojoFailureException, XMLStreamException {
      Parent parent = projectModel.getParent();
      if (parent == null) {
         getLog().info("Skip, no parent");
         return;
      }
      getLog().info("Updating parent from " + parent.getVersion() + " to " + parentVersion);
      PomHelper.setProjectParentVersion(reader, parentVersion);
   }

}
