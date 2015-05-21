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

package org.sourcepit.b2.model.interpolation.layout;

import java.io.File;

import javax.inject.Named;

import org.sourcepit.b2.model.module.AbstractModule;

@Named("simple")
public class SimpleInterpolationLayout implements IInterpolationLayout {
   public String pathOfFeatureProject(final AbstractModule module, String classifier) {
      return pathOfFacetProject(module, "features", classifier, "feature");
   }

   public String idOfFeatureProject(AbstractModule module, String classifier) {
      return idOfProject(module, classifier, "feature");
   }

   public String pathOfSiteProject(AbstractModule module, String classifier) {
      return pathOfFacetProject(module, "sites", classifier, "site");
   }

   public String idOfSiteProject(final AbstractModule module, String classifier) {
      return idOfProject(module, classifier, "site");
   }

   protected String pathOfFacetProject(final AbstractModule module, String facetType, String classifier, String appendix) {
      return pathOfFacetMetaData(module, facetType, idOfProject(module, classifier, appendix));
   }

   public String pathOfFacetMetaData(AbstractModule module, String facetType, String name) {
      return pathOfMetaDataFile(module, facetType + File.separatorChar + name);
   }

   public String pathOfMetaDataFile(AbstractModule module, String name) {
      final StringBuilder sb = new StringBuilder();
      final String modulePath = module.getDirectory().getPath();
      if (modulePath.length() != 0) {
         sb.append(modulePath);
         sb.append(File.separatorChar);
      }
      sb.append(".b2");
      sb.append(File.separatorChar);
      sb.append(name);
      return sb.toString();
   }

   protected String idOfProject(final AbstractModule module, String classifier, String appendix) {
      final StringBuilder sb = new StringBuilder();
      sb.append(module.getId());
      if (classifier != null && classifier.length() > 0) {
         sb.append('.');
         sb.append(classifier);
      }
      if (appendix != null && appendix.length() > 0) {
         sb.append(".");
         sb.append(appendix);
      }
      return sb.toString();
   }
}
