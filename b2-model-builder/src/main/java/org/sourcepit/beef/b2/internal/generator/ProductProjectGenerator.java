/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.common.internal.utils.XmlUtils;
import org.sourcepit.beef.b2.generator.AbstractGenerator;
import org.sourcepit.beef.b2.generator.GeneratorType;
import org.sourcepit.beef.b2.generator.IB2GenerationParticipant;
import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.internal.model.PluginProject;
import org.sourcepit.beef.b2.internal.model.PluginsFacet;
import org.sourcepit.beef.b2.internal.model.ProductDefinition;
import org.sourcepit.beef.b2.internal.model.Reference;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Named
public class ProductProjectGenerator extends AbstractGenerator implements IB2GenerationParticipant
{
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

      final PluginProject productPlugin = resolveProductPlugin(module, product);
      if (productPlugin != null)
      {
         // tycho icon bug fix
         final Document productDoc = XmlUtils.readXml(productFile);
         boolean modified = false;
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
         if (modified)
         {
            XmlUtils.writeXml(productDoc, productFile);
         }
      }
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
