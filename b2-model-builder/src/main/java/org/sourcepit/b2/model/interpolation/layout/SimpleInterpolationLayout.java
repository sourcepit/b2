/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.layout;

import java.io.File;

import javax.inject.Named;

import org.sourcepit.b2.model.module.AbstractModule;

@Named("simple")
public class SimpleInterpolationLayout implements IInterpolationLayout
{
   public String pathOfFeatureProject(final AbstractModule module, String classifier)
   {
      return pathOfFacetProject(module, "features", classifier, "feature");
   }

   public String idOfFeatureProject(AbstractModule module, String classifier)
   {
      return idOfProject(module, classifier, "feature");
   }

   public String pathOfSiteProject(AbstractModule module, String classifier)
   {
      return pathOfFacetProject(module, "sites", classifier, "site");
   }

   public String idOfSiteProject(final AbstractModule module, String classifier)
   {
      return idOfProject(module, classifier, "site");
   }

   protected String pathOfFacetProject(final AbstractModule module, String facetType, String classifier, String appendix)
   {
      return pathOfFacetMetaData(module, facetType, idOfProject(module, classifier, appendix));
   }

   public String pathOfFacetMetaData(AbstractModule module, String facetType, String name)
   {
      return pathOfMetaDataFile(module, facetType + File.separatorChar + name);
   }

   public String pathOfMetaDataFile(AbstractModule module, String name)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(module.getDirectory().getAbsolutePath());
      sb.append(File.separatorChar);
      sb.append(".b2");
      sb.append(File.separatorChar);
      sb.append(name);
      return sb.toString();
   }

   protected String idOfProject(final AbstractModule module, String classifier, String appendix)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(module.getId());
      if (classifier != null && classifier.length() > 0)
      {
         sb.append('.');
         sb.append(classifier);
      }
      if (appendix != null && appendix.length() > 0)
      {
         sb.append(".");
         sb.append(appendix);
      }
      return sb.toString();
   }
}
