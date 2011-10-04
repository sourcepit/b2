/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.beef.b2.common.internal.utils.PathMatcher;
import org.sourcepit.beef.b2.common.internal.utils.XmlUtils;
import org.sourcepit.beef.b2.generator.AbstractGenerator;
import org.sourcepit.beef.b2.generator.GeneratorType;
import org.sourcepit.beef.b2.generator.IB2GenerationParticipant;
import org.sourcepit.beef.b2.model.builder.util.IB2SessionService;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.common.util.ArtifactIdentifier;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.PluginProject;
import org.sourcepit.beef.b2.model.module.PluginsFacet;
import org.sourcepit.beef.b2.model.module.ProductDefinition;
import org.sourcepit.beef.b2.model.module.Reference;
import org.sourcepit.beef.b2.model.session.ModuleDependency;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Named
public class ProductProjectGenerator extends AbstractGenerator implements IB2GenerationParticipant
{
   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private IB2SessionService sessionService;

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
   public void generate(EObject inputElement, IConverter converter, ITemplates templates)
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

      final String classifier = getClassifier(productFile.getName());

      final StringBuilder sb = new StringBuilder();
      sb.append("b2.product");
      if (classifier != null)
      {
         sb.append('.');
         sb.append(classifier);
      }
      sb.append(".filter");

      final PathMatcher matcher = PathMatcher.parsePackagePatterns(converter.getProperties().get(sb.toString(), "**"));

      final List<FeatureProject> featureIncludes = resolveFeatureIncludes(module, matcher);
      if (!featureIncludes.isEmpty())
      {
         Element features = (Element) XmlUtils.queryNode(productDoc, "/product/features");
         for (FeatureProject featureProject : featureIncludes)
         {
            Element element = productDoc.createElement("feature");
            element.setAttribute("id", featureProject.getId());
            features.appendChild(element);

            modified = true;
         }
      }

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
                     Element element = (Element) node;
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

   protected static String getClassifier(String productFileName)
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
         classifier = null;
      }
      return classifier == null || classifier.length() == 0 ? null : classifier;
   }

   private List<FeatureProject> resolveFeatureIncludes(AbstractModule module, PathMatcher matcher)
   {
      List<FeatureProject> includes = new ArrayList<FeatureProject>();

      collectIncludedFeatures(includes, matcher, module);

      ResourceSet resourceSet = sessionService.getCurrentResourceSet();
      ModuleProject project = sessionService.getCurrentSession().getCurrentProject();

      for (ModuleDependency dependency : project.getDependencies())
      {
         AbstractModule depModule = resolveModule(resourceSet, dependency);
         collectIncludedFeatures(includes, matcher, depModule);
      }

      return includes;
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

   private AbstractModule resolveModule(ResourceSet resourceSet, ModuleDependency dependency)
   {
      final URI uri = new ArtifactIdentifier(dependency.getGroupId(), dependency.getArtifactId(),
         dependency.getVersionRange(), dependency.getClassifier(), "module").toUri();

      Resource resource = resourceSet.getResource(uri, true);
      AbstractModule module = (AbstractModule) resource.getContents().get(0);
      return module;
   }

   private PluginProject resolveProductPlugin(final AbstractModule module, final ProductDefinition project)
   {
      final Reference reference = project.getProductPlugin();
      if (reference != null)
      {
         return module.resolveReference(reference, PluginsFacet.class);
      }
      return null;
   }
}
