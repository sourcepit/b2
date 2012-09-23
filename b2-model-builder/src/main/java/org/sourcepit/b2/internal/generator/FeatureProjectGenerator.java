/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.codehaus.plexus.util.xml.PrettyPrintXMLWriter;
import org.codehaus.plexus.util.xml.XMLWriter;
import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.generator.AbstractGeneratorForDerivedElements;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.common.manifest.osgi.Version;
import org.sourcepit.common.utils.nls.NlsUtils;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.props.PropertiesUtils;
import org.sourcepit.tools.shared.resources.harness.StringInterpolator;

@Named
public class FeatureProjectGenerator extends AbstractGeneratorForDerivedElements implements IB2GenerationParticipant
{
   private final FeaturePropertiesQueryFactory propertiesQueryFactory;

   @Inject
   public FeatureProjectGenerator(FeaturePropertiesQueryFactory propertiesQueryFactory)
   {
      this.propertiesQueryFactory = propertiesQueryFactory;
   }

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_GENERATOR;
   }

   @Override
   protected void addTypesOfInputs(Collection<Class<? extends Derivable>> inputTypes)
   {
      inputTypes.add(FeatureProject.class);
   }

   @Override
   protected void generate(Derivable inputElement, PropertiesSource properties, ITemplates templates)
   {
      final FeatureProject feature = (FeatureProject) inputElement;

      final Properties featureProperties = new Properties();
      featureProperties.setProperty("feature.id", feature.getId());
      featureProperties.setProperty("feature.version", feature.getVersion());

      // TODO determine ${feature.plugin}
      featureProperties.setProperty("feature.plugin", "");

      final boolean isAssemblyFeature = isAssemblyFeature(feature);

      final String facetOrAssemblyName;
      if (isAssemblyFeature)
      {
         facetOrAssemblyName = getAssemblyName(feature);
      }
      else
      {
         facetOrAssemblyName = getFacetName(feature);
      }

      final String classifier;
      if (isAssemblyFeature)
      {
         classifier = getAssemblyClassifier(feature);
      }
      else
      {
         classifier = getFacetClassifier(feature);
      }

      final boolean isSourceFeature = !isAssemblyFeature && B2MetadataUtils.isSourceFeature(feature);

      featureProperties.setProperty("feature.classifier", classifier);

      final Map<String, PropertiesQuery> queries = propertiesQueryFactory.createPropertyQueries(isAssemblyFeature,
         isSourceFeature, facetOrAssemblyName, classifier);

      insertNlsProperties(queries, NlsUtils.DEFAULT_LOCALE, properties, featureProperties);
      insertIncludesProperty(feature, featureProperties);
      insertPluginsProperty(feature, featureProperties);
      insertRequiresProperty(feature, featureProperties);
      templates.copy("feature-project", feature.getDirectory(), featureProperties);

      generateNlsPropertyFiles(feature, properties, templates, queries);
   }

   private static boolean isAssemblyFeature(FeatureProject feature)
   {
      String facetName = B2MetadataUtils.getFacetName(feature);
      if (facetName != null)
      {
         return false;
      }
      else
      {
         List<String> assemblyNames = B2MetadataUtils.getAssemblyNames(feature);
         if (assemblyNames.size() != 1)
         {
            throw new IllegalStateException();
         }
         return true;
      }
   }

   private static String getFacetName(FeatureProject feature)
   {
      return B2MetadataUtils.getFacetName(feature);
   }

   private static String getFacetClassifier(FeatureProject feature)
   {
      return B2MetadataUtils.getFacetClassifier(feature);
   }

   private static String getAssemblyName(FeatureProject feature)
   {
      final List<String> assemblyNames = B2MetadataUtils.getAssemblyNames(feature);
      if (assemblyNames.size() != 1)
      {
         throw new IllegalStateException();
      }
      return assemblyNames.get(0);
   }

   private static String getAssemblyClassifier(FeatureProject feature)
   {
      final List<String> assemblyClassifiers = B2MetadataUtils.getAssemblyClassifiers(feature);
      if (assemblyClassifiers.size() != 1)
      {
         throw new IllegalStateException();
      }
      return assemblyClassifiers.get(0);
   }

   private void generateNlsPropertyFiles(FeatureProject feature, PropertiesSource properties, ITemplates templates,
      Map<String, PropertiesQuery> queries)
   {
      try
      {
         File projectDir = feature.getDirectory();
         final File workDir = new File(projectDir, ".b2");
         workDir.mkdirs();

         final List<File> nlsPropertyFiles = new ArrayList<File>();
         final EList<Locale> locales = feature.getParent().getParent().getLocales();
         for (Locale locale : locales)
         {
            if (locale.equals(NlsUtils.DEFAULT_LOCALE))
            {
               continue;
            }

            final Properties nlsProperties = new Properties();
            insertNlsProperties(queries, locale, properties, nlsProperties);

            templates.copy("feature-project/feature.properties", workDir, nlsProperties);

            String nlsSuffix = locale.toString();
            if (nlsSuffix.length() > 0)
            {
               nlsSuffix = "_" + nlsSuffix;
            }

            final File destFile = new File(projectDir, "feature" + nlsSuffix + ".properties");
            final File srcFile = new File(workDir, "feature.properties");
            FileUtils.copyFile(srcFile, destFile);
            FileUtils.forceDelete(srcFile);

            nlsPropertyFiles.add(destFile);
         }
         FileUtils.deleteDirectory(workDir);

         addToBinIncludes(projectDir, nlsPropertyFiles);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   public static void addToBinIncludes(File projectDir, final List<File> nlsPropertyFiles)
   {
      if (nlsPropertyFiles.isEmpty())
      {
         return;
      }
      final File propertiesFile = new File(projectDir, "build.properties");
      if (!propertiesFile.exists())
      {
         try
         {
            propertiesFile.createNewFile();
         }
         catch (IOException e)
         {
            throw new IllegalStateException(e);
         }
      }
      final Properties buildProperties = PropertiesUtils.load(propertiesFile);
      final Set<String> binIncludes = new LinkedHashSet<String>();
      final String rawIncludes = buildProperties.getProperty("bin.includes");
      if (rawIncludes != null)
      {
         for (String rawInclude : rawIncludes.split(","))
         {
            binIncludes.add(rawInclude.trim());
         }
      }
      for (File file : nlsPropertyFiles)
      {
         binIncludes.add(file.getName());
      }
      final StringBuilder sb = new StringBuilder();
      for (String include : binIncludes)
      {
         sb.append(include);
         sb.append(',');
      }
      sb.deleteCharAt(sb.length() - 1);
      buildProperties.setProperty("bin.includes", sb.toString());
      PropertiesUtils.store(buildProperties, propertiesFile);
   }

   private void insertPluginsProperty(final FeatureProject feature, final Properties featureProperties)
   {
      final StringWriter plugins = new StringWriter();
      XMLWriter xml = new PrettyPrintXMLWriter(plugins);
      for (PluginInclude plugin : feature.getIncludedPlugins())
      {
         xml.startElement("plugin");
         xml.addAttribute("id", plugin.getId());
         xml.addAttribute("download-size", "0");
         xml.addAttribute("install-size", "0");
         xml.addAttribute("version", "0.0.0");
         xml.addAttribute("unpack", String.valueOf(plugin.isUnpack()));
         xml.endElement();
      }
      plugins.flush();
      featureProperties.setProperty("feature.plugins", plugins.toString());
   }

   private void insertIncludesProperty(final FeatureProject feature, final Properties featureProperties)
   {
      final StringWriter includes = new StringWriter();
      XMLWriter xml = new PrettyPrintXMLWriter(includes);
      for (FeatureInclude include : feature.getIncludedFeatures())
      {
         xml.startElement("includes");
         xml.addAttribute("id", include.getId());
         xml.addAttribute("version", "0.0.0");
         xml.endElement();
      }
      includes.flush();
      featureProperties.setProperty("feature.includes", includes.toString());
   }

   private void insertRequiresProperty(final FeatureProject feature, final Properties featureProperties)
   {
      final StringWriter requires = new StringWriter();
      final EList<RuledReference> requiredFeatures = feature.getRequiredFeatures();
      final EList<RuledReference> requiredPlugins = feature.getRequiredPlugins();

      if (!requiredFeatures.isEmpty() || !requiredPlugins.isEmpty())
      {
         XMLWriter xml = new PrettyPrintXMLWriter(requires);
         xml.startElement("requires");

         for (RuledReference requirement : requiredFeatures)
         {
            xml.startElement("import");
            xml.addAttribute("feature", requirement.getId());
            if (requirement.getVersion() != null)
            {
               // TODO erase qualifier on reference creation
               // TODO tycho improvement request: replace version or at least .qualifier (like done with includes)
               Version v = Version.parse(requirement.getVersion());
               xml.addAttribute("version", v.getMajor() + "." + v.getMinor() + "." + v.getMicro());
               xml.addAttribute("match", requirement.getVersionMatchRule().getLiteral());
            }
            xml.endElement();
         }
         for (RuledReference requirement : requiredPlugins)
         {
            xml.startElement("import");
            xml.addAttribute("plugin", requirement.getId());
            if (requirement.getVersion() != null)
            {
               // TODO erase qualifier on reference creation
               Version v = Version.parse(requirement.getVersion());
               xml.addAttribute("version", v.getMajor() + "." + v.getMinor() + "." + v.getMicro());
               xml.addAttribute("match", requirement.getVersionMatchRule().getLiteral());
            }
            xml.endElement();
         }
         xml.endElement();
         requires.flush();
      }
      featureProperties.setProperty("feature.requires", requires.toString());
   }

   private void insertNlsProperties(final Map<String, PropertiesQuery> queries, Locale locale,
      final PropertiesSource properties, final Properties featureProperties)
   {
      final String nlsPrefix = createNlsPrefix(locale);

      StringInterpolator s = new StringInterpolator();
      s.setEscapeString("\\");
      s.getValueSources().add(new AbstractValueSource(false)
      {
         public Object getValue(String expression)
         {
            return properties.get(expression);
         }
      });
      s.getValueSources().add(new AbstractValueSource(false)
      {
         public Object getValue(String expression)
         {
            PropertiesQuery query = queries.get(expression);
            if (query != null)
            {
               query.setPrefix(nlsPrefix);
               return query.lookup(properties);
            }
            return null;
         }
      });


      for (Entry<String, PropertiesQuery> entry : queries.entrySet())
      {
         final String key = entry.getKey();
         final PropertiesQuery query = entry.getValue();

         query.setPrefix(nlsPrefix);

         String value = s.interpolate(query.lookup(properties));

         // HACK
         if (key.equals("feature.name"))
         {
            value = removeRedundantWS(value);
         }

         featureProperties.setProperty(key, value);
      }
   }

   private static String removeRedundantWS(String value)
   {
      final StringBuilder sb = new StringBuilder(value.length());
      final char[] chars = value.toCharArray();
      int ws = 0;
      for (char c : chars)
      {
         boolean isWs = Character.isWhitespace(c);
         if (isWs)
         {
            if (ws == 0)
            {
               sb.append(c);
            }
            ws++;
         }
         else
         {
            ws = 0;
            sb.append(c);
         }
      }
      return sb.toString().trim();
   }

   private String createNlsPrefix(Locale locale)
   {
      String nlsPrefix = locale.toString();
      if (nlsPrefix.length() > 0)
      {
         nlsPrefix = "nls_" + nlsPrefix + ".";
      }
      return nlsPrefix;
   }
}
