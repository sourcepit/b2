/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.osgi.framework.Version;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class DefaultConverter implements SitesConverter, BasicConverter, FeaturesConverter
{
   public boolean isSkipInterpolator(PropertiesSource moduleProperties)
   {
      return moduleProperties.getBoolean("b2.skipInterpolator", false);
   }

   public boolean isSkipGenerator(PropertiesSource moduleProperties)
   {
      return moduleProperties.getBoolean("b2.skipGenerator", false);
   }

   public boolean isPotentialModuleDirectory(PropertiesSource moduleProperties, File baseDir, File file)
   {
      if (file.isDirectory() && file.exists() && new File(file, "module.xml").exists())
      {
         final PathMatcher moduleDirMacher = PathMatcher.parseFilePatterns(baseDir,
            moduleProperties.get("b2.modules.filter", "**"));
         if (moduleDirMacher.isMatch(file.getAbsolutePath()))
         {
            return true;
         }
      }
      return false;
   }

   public List<String> getAssemblyNames(PropertiesSource moduleProperties)
   {
      final List<String> assemblies = new ArrayList<String>();
      final String rawAssemblies = moduleProperties.get("b2.assemblies");
      if (rawAssemblies != null)
      {
         for (String rawAssembly : rawAssemblies.split(","))
         {
            final String assembly = rawAssembly.trim();
            if (assembly.length() > 0 && !assemblies.contains(assembly))
            {
               assemblies.add(assembly);
            }
         }
      }
      return assemblies;
   }

   public String getAssemblyClassifier(PropertiesSource properties, String assemblyName)
   {
      if (assemblyName.length() == 0)
      {
         throw new IllegalArgumentException("assemblyName must not be empty.");
      }
      // TODO assert result is valid id
      return properties.get(assemblyKey(assemblyName, "classifier"), toValidId(assemblyName));
   }

   public AggregatorMode getAggregatorMode(PropertiesSource moduleProperties, String assemblyName)
   {
      final String literal = get(moduleProperties, assemblyKey(assemblyName, "aggregator.mode"),
         b2Key("aggregator.mode"));
      return literal == null ? AggregatorMode.OFF : AggregatorMode.valueOf(literal.toUpperCase());
   }

   public PathMatcher getAggregatorFeatureMatcherForAssembly(PropertiesSource moduleProperties, String assemblyName)
   {
      final String patterns = moduleProperties.get(assemblyKey(assemblyName, "aggregator.featuresFilter"), "**");
      return PathMatcher.parse(patterns, ".", ",");
   }

   public PathMatcher getFeatureMatcherForAssembly(PropertiesSource moduleProperties, String assemblyName)
   {
      final String patterns = moduleProperties.get(assemblyKey(assemblyName, "featuresFilter"), "**");
      return PathMatcher.parse(patterns, ".", ",");
   }

   public PathMatcher getPluginMatcherForAssembly(PropertiesSource moduleProperties, String assemblyName)
   {
      final String patterns = moduleProperties.get(assemblyKey(assemblyName, "pluginsFilter"), "!**");
      return PathMatcher.parse(patterns, ".", ",");
   }

   public List<FeatureInclude> getIncludedFeaturesForAssembly(PropertiesSource moduleProperties, String assemblyName)
   {
      final String key = "includedFeatures";
      final String rawIncludes = get(moduleProperties, assemblyKey(assemblyName, key), b2Key(key));
      return toFeatureIncludeList(rawIncludes);
   }

   public List<PluginInclude> getIncludedPluginsForAssembly(PropertiesSource moduleProperties, String assemblyName)
   {
      final String key = "includedPlugins";
      final String rawIncludes = get(moduleProperties, assemblyKey(assemblyName, key), b2Key(key));
      return toPluginIncludeList(rawIncludes);
   }

   public List<RuledReference> getRequiredFeaturesForAssembly(PropertiesSource moduleProperties, String assemblyName)
   {
      final String key = "requiredFeatures";
      final String rawIncludes = get(moduleProperties, assemblyKey(assemblyName, key), b2Key(key));
      return toRuledReferenceList(rawIncludes);
   }

   public List<RuledReference> getRequiredPluginsForAssembly(PropertiesSource moduleProperties, String assemblyName)
   {
      final String key = "requiredPlugins";
      final String rawIncludes = get(moduleProperties, assemblyKey(assemblyName, key), b2Key(key));
      return toRuledReferenceList(rawIncludes);
   }

   public String getFacetClassifier(PropertiesSource properties, String facetName)
   {
      if (facetName.length() == 0)
      {
         throw new IllegalArgumentException("facetName must not be empty.");
      }
      // TODO assert result is valid id
      return properties.get(facetKey(facetName, "classifier"), toValidId(facetName));
   }

   public PathMatcher getPluginMatcherForFacet(PropertiesSource moduleProperties, String facetName)
   {
      final String patterns = moduleProperties.get(facetKey(facetName, "pluginsFilter"), "**");
      return PathMatcher.parse(patterns, ".", ",");
   }

   public List<FeatureInclude> getIncludedFeaturesForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource)
   {
      final String key = isSource ? "includedSourceFeatures" : "includedFeatures";
      final String rawIncludes = get(moduleProperties, facetKey(facetName, key), b2Key(key));
      return toFeatureIncludeList(rawIncludes);
   }

   private List<FeatureInclude> toFeatureIncludeList(final String rawIncludes)
   {
      final List<FeatureInclude> result = new ArrayList<FeatureInclude>();

      if (rawIncludes != null)
      {
         for (String rawInclude : rawIncludes.split(","))
         {
            final String include = rawInclude.trim();
            if (include.length() > 0)
            {
               result.add(toFeatureInclude(include));
            }
         }
      }

      return result;
   }

   public List<PluginInclude> getIncludedPluginsForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource)
   {
      final String key = isSource ? "includedSourcePlugins" : "includedPlugins";
      final String rawIncludes = get(moduleProperties, facetKey(facetName, key), b2Key(key));
      return toPluginIncludeList(rawIncludes);
   }

   private List<PluginInclude> toPluginIncludeList(final String rawIncludes)
   {
      final List<PluginInclude> result = new ArrayList<PluginInclude>();

      if (rawIncludes != null)
      {
         for (String rawInclude : rawIncludes.split(","))
         {
            final String include = rawInclude.trim();
            if (include.length() > 0)
            {
               result.add(toPluginInclude(include));
            }
         }
      }

      return result;
   }

   public List<RuledReference> getRequiredFeaturesForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource)
   {
      final String key = isSource ? "requiredSourceFeatures" : "requiredFeatures";
      final String requirements = get(moduleProperties, facetKey(facetName, key), b2Key(key));
      return toRuledReferenceList(requirements);
   }

   public List<RuledReference> getRequiredPluginsForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource)
   {
      final String key = isSource ? "requiredSourcePlugins" : "requiredPlugins";
      final String requirements = get(moduleProperties, facetKey(facetName, key), b2Key(key));
      return toRuledReferenceList(requirements);
   }

   private static List<RuledReference> toRuledReferenceList(String rawRequirements)
   {
      final List<RuledReference> result = new ArrayList<RuledReference>();

      // foo.feature:1.0.0:compatible,
      if (rawRequirements != null)
      {
         for (String rawRequirement : rawRequirements.split(","))
         {
            final String requirement = rawRequirement.trim();
            if (requirement.length() > 0)
            {
               result.add(toRuledReference(requirement));
            }
         }
      }

      return result;
   }

   private static RuledReference toRuledReference(String string)
   {
      final RuledReference ref = ModuleModelFactory.eINSTANCE.createRuledReference();
      final String[] segments = string.split(":");
      if (segments.length < 1 || segments.length > 3)
      {
         throw new IllegalArgumentException(string + " is not a valid requirement specification");
      }

      parseAndSetIdAndVersion(string, ref, segments);

      if (segments.length > 2)
      {
         final String ruleString = segments[2].trim();
         final VersionMatchRule rule = VersionMatchRule.get(ruleString);
         if (rule == null)
         {
            throw new IllegalArgumentException("'" + ruleString + "' in " + string
               + " is not a valid version matching rule");
         }
         ref.setVersionMatchRule(rule);
      }

      return ref;
   }

   private static void parseAndSetIdAndVersion(String reference, final AbstractReference ref, final String[] segments)
   {
      // TODO assert is valid
      ref.setId(segments[0].trim());

      if (segments.length > 1)
      {
         final String versionString = segments[1].trim();
         try
         {
            new Version(versionString);
         }
         catch (IllegalArgumentException e)
         {
            throw new IllegalArgumentException("'" + versionString + "' in " + reference + " is not a valid version");
         }
         ref.setVersion(versionString);
      }
   }

   private static FeatureInclude toFeatureInclude(String include)
   {
      // foo:1.0.0:optional
      final FeatureInclude inc = ModuleModelFactory.eINSTANCE.createFeatureInclude();
      final String[] segments = include.split(":");
      if (segments.length < 1 || segments.length > 3)
      {
         throw new IllegalArgumentException(include + " is not a valid feature include specification");
      }

      parseAndSetIdAndVersion(include, inc, segments);

      if (segments.length > 2)
      {
         final String optionalString = segments[2].trim();
         if (!"optional".equals(optionalString))
         {
            throw new IllegalArgumentException("'" + optionalString + "' in " + include
               + " must be 'optional' or missing");
         }
         inc.setOptional(true);
      }

      return inc;
   }

   private static PluginInclude toPluginInclude(String include)
   {
      // foo:1.0.0:unpack
      final PluginInclude inc = ModuleModelFactory.eINSTANCE.createPluginInclude();
      final String[] segments = include.split(":");
      if (segments.length < 1 || segments.length > 3)
      {
         throw new IllegalArgumentException(include + " is not a valid plugin include specification");
      }

      parseAndSetIdAndVersion(include, inc, segments);

      inc.setUnpack(false);

      if (segments.length > 2)
      {
         final String optionalString = segments[2].trim();
         if (!"unpack".equals(optionalString))
         {
            throw new IllegalArgumentException("'" + optionalString + "' in " + include
               + " must be 'optional' or missing");
         }
         inc.setUnpack(true);
      }

      return inc;
   }

   private static String assemblyKey(String assemblyName, String key)
   {
      return b2Key("assemblies." + assemblyName + "." + key);
   }

   private static String facetKey(String facetName, String key)
   {
      return b2Key("facets." + facetName + "." + key);
   }

   private static String b2Key(String key)
   {
      return "b2." + key;
   }

   private static String get(PropertiesSource moduleProperties, String... keys)
   {
      for (String key : keys)
      {
         final String value = moduleProperties.get(key);
         if (value != null)
         {
            return value;
         }
      }
      return null;
   }

   public String getFeatureId(PropertiesSource properties, String moduleId, String classifier, boolean isSource)
   {
      final StringBuilder sb = new StringBuilder();
      if (classifier != null)
      {
         sb.append(classifier);
      }
      if (isSource)
      {
         if (sb.length() > 0)
         {
            sb.append('.');
         }
         sb.append(properties.get("b2.featuresSourceClassifier", "sources"));
      }

      return idOfProject(moduleId, sb.toString(), "feature");
   }

   public String getSourcePluginId(PropertiesSource moduleProperties, String pluginId)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(pluginId);
      if (sb.length() > 0)
      {
         sb.append('.');
         sb.append(moduleProperties.get("b2.pluginsSourceClassifier", "source"));
      }
      return sb.toString();
   }

   public String getSiteId(PropertiesSource moduleProperties, String moduleId, String classifier)
   {
      return idOfProject(moduleId, classifier, "site");
   }

   private static String idOfProject(String moduleId, String classifier, String appendix)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(moduleId);
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

   protected static String toValidId(String string)
   {
      // TODO assert not empty
      return toJavaIdentifier(string.toLowerCase());
   }

   /**
    * Converts the specified string into a valid Java identifier. All illegal characters are replaced by underscores.
    * 
    * @param aString <i>(required)</i>. The string must contain at least one character.
    * @return <i>(required)</i>.
    */
   private static String toJavaIdentifier(String aString)
   {
      if (aString.length() == 0)
      {
         return "_";
      }

      final StringBuilder res = new StringBuilder();
      int idx = 0;
      char c = aString.charAt(idx);
      if (Character.isJavaIdentifierStart(c))
      {
         res.append(c);
         idx++;
      }
      else if (Character.isJavaIdentifierPart(c))
      {
         res.append('_');
      }
      while (idx < aString.length())
      {
         c = aString.charAt(idx++);
         res.append(Character.isJavaIdentifierPart(c) ? c : '_');
      }
      return res.toString();
   }

   public String getModuleVersion(PropertiesSource moduleProperties)
   {
      return moduleProperties.get("b2.moduleVersion", "0.1.0.qualifier");
   }

   public String getNameSpace(PropertiesSource moduleProperties)
   {
      return moduleProperties.get("b2.moduleNameSpace", "b2.module");
   }
}
