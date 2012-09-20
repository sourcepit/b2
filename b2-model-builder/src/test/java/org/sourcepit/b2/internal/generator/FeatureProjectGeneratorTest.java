/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.model.builder.harness.ModelBuilderHarness.addFeatureProject;
import static org.sourcepit.b2.model.builder.harness.ModelBuilderHarness.createBasicModule;

import java.util.Locale;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.common.utils.nls.NlsUtils;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

import com.google.common.base.Optional;

public class FeatureProjectGeneratorTest extends AbstractTestEnvironmentTest
{
   @Test
   public void test()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(properties));

      final BasicModule module = createBasicModule("foo");
      module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
      module.getLocales().add(Locale.GERMAN);

      final FeatureProject featureProject = addFeatureProject(module, "features", "foo.feature", module.getVersion());
      featureProject.setDirectory(ws.getRoot());

      B2MetadataUtils.addAssemblyName(featureProject, "public");
      B2MetadataUtils.addAssemblyClassifier(featureProject, "");

      FeatureProjectGenerator generator = gLookup(FeatureProjectGenerator.class);
      generator.generate(featureProject, properties, templates);
   }
}
