/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addFeatureProject;
import static org.sourcepit.b2.model.harness.ModelTestHarness.createBasicModule;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyClassifier;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyName;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.setFacetClassifier;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.setFacetName;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.setSourceFeature;

import java.io.File;
import java.util.Locale;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.common.utils.nls.NlsUtils;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

import com.google.common.base.Optional;

public class FeatureProjectGeneratorTest extends AbstractTestEnvironmentTest
{
   @Test
   public void testSourceLabelForFacets()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("nls_de.b2.sourceFeatureLabelAppendix", "(Quelldateien)");

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(properties));

      final BasicModule module = createBasicModule("foo");
      module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
      module.getLocales().add(Locale.GERMAN);

      final FeatureProject featureProject = addFeatureProject(module, "features", "foo.feature", module.getVersion());
      featureProject.setDirectory(ws.getRoot());

      setFacetName(featureProject, "public");
      setFacetClassifier(featureProject, "");
      setSourceFeature(featureProject, true);

      FeatureProjectGenerator generator = gLookup(FeatureProjectGenerator.class);
      generator.generate(featureProject, properties, templates);

      File featureDir = featureProject.getDirectory();
      assertTrue(featureDir.exists());

      PropertiesMap featureProperties;

      // default locale
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(featureDir, "feature.properties"));
      assertEquals("Core Plug-ins (Sources)", featureProperties.get("featureName"));

      // de
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(featureDir, "feature_de.properties"));
      assertEquals("Kern Plug-ins (Quelldateien)", featureProperties.get("featureName"));
   }

   @Test
   public void testNoSourceLabelForAssemblies()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("nls_de.b2.sourceClassifierLabel", "(Quelldateien)");

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(properties));

      final BasicModule module = createBasicModule("foo");
      module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
      module.getLocales().add(Locale.GERMAN);

      final FeatureProject featureProject = addFeatureProject(module, "features", "foo.feature", module.getVersion());
      featureProject.setDirectory(ws.getRoot());

      addAssemblyName(featureProject, "public");
      addAssemblyClassifier(featureProject, "");
      setSourceFeature(featureProject, true);

      FeatureProjectGenerator generator = gLookup(FeatureProjectGenerator.class);
      generator.generate(featureProject, properties, templates);

      File featureDir = featureProject.getDirectory();
      assertTrue(featureDir.exists());

      PropertiesMap featureProperties;

      // default locale
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(featureDir, "feature.properties"));
      assertEquals("Core Plug-ins", featureProperties.get("featureName"));

      // de
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(featureDir, "feature_de.properties"));
      assertEquals("Kern Plug-ins", featureProperties.get("featureName"));
   }
}
