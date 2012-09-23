/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.AbstractGenerator;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.base.Optional;

@Named
public class ProductProjectGenerator extends AbstractGenerator implements IB2GenerationParticipant
{
   // @Inject
   // private IAggregationService aggregationService;

   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(ProductDefinition.class);
   }

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_GENERATOR;
   }

   @Override
   public void generate(EObject inputElement, PropertiesSource properties, ITemplates templates)
   {
      final ProductDefinition product = (ProductDefinition) inputElement;
      final AbstractModule module = product.getParent().getParent();
      final IInterpolationLayout layout = layoutMap.get(module.getLayoutId());

      final String uid = product.getAnnotationEntry("product", "uid");

      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      projectDir.mkdirs();

      EMap<String, String> data = product.getAnnotation("product").getEntries();

      File srcFile = product.getFile();
      File productFile = new File(projectDir, srcFile.getName());
      try
      {
         productFile.createNewFile();
         FileUtils.copyFile(srcFile, productFile);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      final Document productDoc = XmlUtils.readXml(productFile);
      boolean modified = false;

      final String classifier = getAssemblyClassifier(productFile.getName());

      final Optional<FeatureProject> oAssemblyFeature = findAssemblyFeatureForClassifier(module, classifier);
      if (!oAssemblyFeature.isPresent())
      {
         throw new IllegalStateException("Cannot determine assembly feature for classifier '" + classifier + "'");
      }

      final FeatureProject assemblyFeature = oAssemblyFeature.get();

      Element features = getFeaturesNode(productDoc);
      Element element = productDoc.createElement("feature");
      element.setAttribute("id", assemblyFeature.getId());
      features.appendChild(element);

      modified = true;

      final PluginProject productPlugin = resolveProductPlugin(module, product);
      if (productPlugin != null)
      {
         // tycho icon bug fix
         for (Entry<String, String> entry : data)
         {
            String key = entry.getKey();
            if (key.endsWith(".icon"))
            {
               final String os = key.substring(0, key.length() - ".icon".length());
               String path = entry.getValue();
               if (path.startsWith("/"))
               {
                  path = path.substring(1);
               }

               final File iconFile = new File(productPlugin.getDirectory().getParentFile(), path);
               if (iconFile.exists())
               {
                  for (Node node : XmlUtils.queryNodes(productDoc, "product/launcher/" + os + "/ico"))
                  {
                     element = (Element) node;
                     element.setAttribute("path", iconFile.getAbsolutePath());
                     modified = true;
                  }
               }
            }
         }
      }

      if (modified)
      {
         XmlUtils.writeXml(productDoc, productFile);
      }
   }

   private static Optional<FeatureProject> findAssemblyFeatureForClassifier(final AbstractModule module,
      String classifier)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            final int idx = B2MetadataUtils.getAssemblyClassifiers(featureProject).indexOf(classifier);
            if (idx > -1)
            {
               return Optional.of(featureProject);
            }
         }
      }
      return Optional.absent();
   }

   private Element getFeaturesNode(final Document productDoc)
   {
      Element features = (Element) XmlUtils.queryNode(productDoc, "/product/features");
      if (features == null)
      {
         features = productDoc.createElement("features");
         Element product = (Element) productDoc.getElementsByTagName("product").item(0);
         product.appendChild(features);
      }
      return features;
   }

   private void collectIncludedFeatures(List<FeatureProject> includes, PathMatcher matcher, AbstractModule module)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         EList<FeatureProject> projects = featuresFacet.getProjects();
         for (FeatureProject featureProject : projects)
         {
            if (matcher.isMatch(featureProject.getId()))
            {
               includes.add(featureProject);
            }
         }
      }
   }

   public static String getAssemblyClassifier(String productFileName)
   {
      String classifier = productFileName;
      int idx = classifier.lastIndexOf('.');
      if (idx > -1)
      {
         classifier = classifier.substring(0, idx);
      }
      idx = classifier.lastIndexOf('-');
      if (idx > -1)
      {
         classifier = classifier.substring(idx + 1, classifier.length());
      }
      else
      {
         classifier = "";
      }
      return classifier;
   }

   private PluginProject resolveProductPlugin(final AbstractModule module, final ProductDefinition project)
   {
      final StrictReference reference = project.getProductPlugin();
      if (reference != null)
      {
         return module.resolveReference(reference, PluginsFacet.class);
      }
      return null;
   }
}
