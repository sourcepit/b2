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

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.common.utils.file.FileUtils.deleteFileOrDirectory;
import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.File;
import java.io.FileFilter;
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
import org.sourcepit.b2.model.interpolation.internal.module.B2ModelUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.manifest.osgi.BundleManifest;
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
      generateFeature(feature, properties, templates);
   }

   private void generateFeature(final FeatureProject feature, PropertiesSource properties, ITemplates templates)
   {
      final Properties featureProperties = new Properties();
      featureProperties.setProperty("feature.id", feature.getId());
      featureProperties.setProperty("feature.version", feature.getVersion());

      final PluginProject brandingPlugin = determineBrandingPlugin(feature);

      final boolean isAssemblyFeature = B2MetadataUtils.isAssemblyFeature(feature);

      final String facetOrAssemblyName;
      if (isAssemblyFeature)
      {
         facetOrAssemblyName = getAssemblyName(feature);
      }
      else
      {
         facetOrAssemblyName = getFacetName(feature);
      }

      final String classifier = B2MetadataUtils.getClassifier(feature);

      final boolean isSourceFeature = B2MetadataUtils.isFacetSourceFeature(feature);

      featureProperties.setProperty("feature.classifier", classifier);

      final Map<String, PropertiesQuery> queries = propertiesQueryFactory.createPropertyQueries(isAssemblyFeature,
         isSourceFeature, facetOrAssemblyName, classifier);

      if (brandingPlugin != null)
      {
         queries.get("feature.plugin").setDefaultValue(brandingPlugin.getId());
      }

      insertNlsProperties(queries, NlsUtils.DEFAULT_LOCALE, properties, featureProperties);
      insertIncludesProperty(feature, featureProperties);
      insertPluginsProperty(feature, featureProperties);
      insertRequiresProperty(feature, featureProperties);
      templates.copy("feature-project", feature.getDirectory(), featureProperties);

      final AbstractModule module = feature.getParent().getParent();
      final EList<Locale> locales = module.getLocales();
      generateNlsPropertyFiles(locales, "feature", templates, "feature-project", feature.getDirectory(), properties,
         queries);

      if (brandingPlugin != null && brandingPlugin.isDerived())
      {
         final File pluginDir = brandingPlugin.getDirectory();

         final String defaultIcon = determineDefaultFeatureIconName(pluginDir, templates, featureProperties);
         PropertiesQuery query = queries.get("feature.featureImage");
         query.setDefaultValue(defaultIcon);
         final String customIcon = query.lookup(properties);

         final File moduleIcon = getModuleIcon(module);
         if (moduleIcon == null)
         {
            featureProperties.put("feature.featureImage", customIcon);
         }
         else
         {
            featureProperties.put("feature.featureImage", moduleIcon.getName());
         }

         // must be set for branding plugins
         queries.get("feature.providerName").setDefaultValue("<empty>");

         templates.copy("branding-plugin-project", pluginDir, featureProperties);

         if (moduleIcon != null)
         {
            try
            {
               File file = new File(pluginDir, defaultIcon);
               if (file.exists())
               {
                  deleteFileOrDirectory(file);
               }
               file = new File(pluginDir, customIcon);
               if (file.exists())
               {
                  deleteFileOrDirectory(file);
               }
               FileUtils.copyFileToDirectory(moduleIcon, pluginDir);
            }
            catch (IOException e)
            {
               throw pipe(e);
            }
         }

         generateNlsPropertyFiles(locales, "plugin", templates, "branding-plugin-project", pluginDir, properties,
            queries);
         generateNlsPropertyFiles(locales, "about", templates, "branding-plugin-project", pluginDir, properties,
            queries);

         brandingPlugin.setBundleManifest((BundleManifest) B2ModelUtils.readManifest(new File(pluginDir,
            "META-INF/MANIFEST.MF"), true));
      }
   }

   private File getModuleIcon(final AbstractModule module)
   {
      final List<String> iconNames = getFeatureIconNames();
      final File moduleDir = module.getDirectory();
      final File[] iconFiles = moduleDir.listFiles(new FileFilter()
      {
         @Override
         public boolean accept(File file)
         {
            return file.isFile() && iconNames.contains(file.getName());
         }
      });

      return iconFiles.length == 0 ? null : iconFiles[0];
   }

   private static String determineDefaultFeatureIconName(final File pluginDir, ITemplates templates,
      Properties featureProperties)
   {
      final List<String> featureFileNames = getFeatureIconNames();

      for (String featureIconName : featureFileNames)
      {
         try
         {
            templates.copy("branding-plugin-project/" + featureIconName, pluginDir, featureProperties, false);
            FileUtils.deleteQuietly(new File(pluginDir, featureIconName));
         }
         catch (RuntimeException e)
         {
            continue;
         }
         return featureIconName;
      }

      for (String featureIconName : featureFileNames)
      {
         try
         {
            templates.copy("branding-plugin-project/" + featureIconName, pluginDir, featureProperties, true);
            FileUtils.deleteQuietly(new File(pluginDir, featureIconName));
         }
         catch (RuntimeException e)
         {
            continue;
         }
         return featureIconName;
      }

      return "";
   }

   private static List<String> getFeatureIconNames()
   {
      final List<String> featureFileNames = new ArrayList<String>();
      featureFileNames.add("feature32.png");
      featureFileNames.add("feature.png");
      featureFileNames.add("eclipse32.png");
      featureFileNames.add("eclipse.png");
      featureFileNames.add("feature32.gif");
      featureFileNames.add("feature.gif");
      featureFileNames.add("eclipse32.gif");
      featureFileNames.add("eclipse.gif");
      featureFileNames.add("module32.gif");
      featureFileNames.add("module32.png");
      featureFileNames.add("module.gif");
      featureFileNames.add("module.png");
      return featureFileNames;
   }

   private PluginProject determineBrandingPlugin(FeatureProject feature)
   {
      final String pluginId = B2MetadataUtils.getBrandingPlugin(feature);
      if (pluginId != null)
      {
         final StrictReference reference = ModuleModelFactory.eINSTANCE.createStrictReference();
         reference.setId(pluginId);

         return feature.getParent().getParent().resolveReference(reference, PluginsFacet.class);
      }
      return null;
   }

   private static String getFacetName(FeatureProject feature)
   {
      return B2MetadataUtils.getFacetName(feature);
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

   private void generateNlsPropertyFiles(final EList<Locale> locales, final String fileName, ITemplates templates,
      String templateProject, final File projectDir, PropertiesSource properties, Map<String, PropertiesQuery> queries)
   {
      final String extension = ".properties";
      final String templatePath = templateProject + "/" + fileName + extension;
      try
      {
         final File workDir = new File(projectDir, ".b2");
         workDir.mkdirs();

         final List<File> nlsPropertyFiles = new ArrayList<File>();
         for (Locale locale : locales)
         {
            if (locale.equals(NlsUtils.DEFAULT_LOCALE))
            {
               continue;
            }

            final Properties nlsProperties = new Properties();
            insertNlsProperties(queries, locale, properties, nlsProperties);

            templates.copy(templatePath, workDir, nlsProperties);

            String nlsSuffix = locale.toString();
            if (nlsSuffix.length() > 0)
            {
               nlsSuffix = "_" + nlsSuffix;
            }


            final File srcFile = new File(workDir, fileName + extension);
            File destFile = saveNlsProperty(srcFile, locale, projectDir, fileName, extension);
            nlsPropertyFiles.add(destFile);
         }
         deleteFileOrDirectory(workDir);

         addToBinIncludes(projectDir, nlsPropertyFiles);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   private File saveNlsProperty(File srcFile, Locale locale, File projectDir, String fileName, String extension)
      throws IOException
   {
      final File destFile;
      if ("about".equals(fileName))
      {
         String nl = locale.toString();
         if (nl.length() > 0)
         {
            destFile = new File(projectDir, "nl/" + nl + "/" + fileName + extension);
         }
         else
         {
            destFile = new File(projectDir, fileName + extension);
         }
      }
      else
      {
         String nlsSuffix = locale.toString();
         if (nlsSuffix.length() > 0)
         {
            nlsSuffix = "_" + nlsSuffix;
         }
         destFile = new File(projectDir, fileName + nlsSuffix + extension);
      }
      FileUtils.copyFile(srcFile, destFile);
      deleteFileOrDirectory(srcFile);
      return destFile;
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

         final String version = plugin.getVersion();
         if (version != null && !version.endsWith(".qualifier"))
         {
            xml.addAttribute("version", version);
         }
         else
         {
            xml.addAttribute("version", "0.0.0");
         }

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
