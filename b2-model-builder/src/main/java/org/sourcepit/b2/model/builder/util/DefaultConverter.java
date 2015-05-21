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

package org.sourcepit.b2.model.builder.util;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.sourcepit.common.utils.path.PathMatcher.parsePackagePatterns;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.osgi.framework.Version;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.base.Function;
import com.google.common.base.Strings;

@Named
public class DefaultConverter implements SitesConverter, BasicConverter, FeaturesConverter, ProductsConverter {
   public boolean isSkipInterpolator(PropertiesSource moduleProperties) {
      return moduleProperties.getBoolean("b2.skipInterpolator", false);
   }

   public boolean isSkipGenerator(PropertiesSource moduleProperties) {
      return moduleProperties.getBoolean("b2.skipGenerator", false);
   }

   public List<String> getAssemblyNames(PropertiesSource moduleProperties) {
      final List<String> assemblies = new ArrayList<String>();
      final String rawAssemblies = moduleProperties.get("b2.assemblies");
      if (rawAssemblies != null) {
         for (String rawAssembly : rawAssemblies.split(",")) {
            final String assembly = rawAssembly.trim();
            if (assembly.length() > 0 && !assemblies.contains(assembly)) {
               assemblies.add(assembly);
            }
         }
      }
      return assemblies;
   }

   public String getAssemblyClassifier(PropertiesSource properties, String assemblyName) {
      if (assemblyName == null || assemblyName.length() == 0) {
         throw new IllegalArgumentException("assemblyName must not be empty.");
      }
      // TODO assert result is valid id
      return properties.get(assemblyKey(assemblyName, "classifier"), toValidId(assemblyName));
   }

   public AggregatorMode getAggregatorMode(PropertiesSource moduleProperties, String assemblyName) {
      final String literal = get(moduleProperties, assemblyKey(assemblyName, "aggregator.mode"),
         b2Key("aggregator.mode"));
      return literal == null ? AggregatorMode.OFF : AggregatorMode.valueOf(literal.toUpperCase());
   }

   public PathMatcher getAggregatorFeatureMatcherForAssembly(PropertiesSource moduleProperties, String assemblyName) {
      final String patterns = moduleProperties.get(assemblyKey(assemblyName, "aggregator.featuresFilter"), "**");
      return PathMatcher.parse(patterns, ".", ",");
   }

   public PathMatcher getFeatureMatcherForAssembly(PropertiesSource moduleProperties, String assemblyName) {
      final String patterns = moduleProperties.get(assemblyKey(assemblyName, "featuresFilter"), "**");
      return PathMatcher.parse(patterns, ".", ",");
   }

   public PathMatcher getPluginMatcherForAssembly(PropertiesSource moduleProperties, String assemblyName) {
      final String patterns = moduleProperties.get(assemblyKey(assemblyName, "pluginsFilter"), "!**");
      return PathMatcher.parse(patterns, ".", ",");
   }

   public List<FeatureInclude> getIncludedFeaturesForAssembly(PropertiesSource moduleProperties, String assemblyName) {
      final String key = "includedFeatures";
      final String rawIncludes = get(moduleProperties, assemblyKey(assemblyName, key), assemblyKey(null, key),
         b2Key(key));
      return toFeatureIncludeList(rawIncludes);
   }

   public List<PluginInclude> getIncludedPluginsForAssembly(PropertiesSource moduleProperties, String assemblyName) {
      final String key = "includedPlugins";
      final String rawIncludes = get(moduleProperties, assemblyKey(assemblyName, key), assemblyKey(null, key),
         b2Key(key));
      return toPluginIncludeList(rawIncludes);
   }

   public List<RuledReference> getRequiredFeaturesForAssembly(PropertiesSource moduleProperties, String assemblyName) {
      final String key = "requiredFeatures";
      final String rawIncludes = get(moduleProperties, assemblyKey(assemblyName, key), assemblyKey(null, key),
         b2Key(key));
      return toRuledReferenceList(rawIncludes, GREATER_OR_EQUAL);
   }

   public List<RuledReference> getRequiredPluginsForAssembly(PropertiesSource moduleProperties, String assemblyName) {
      final String key = "requiredPlugins";
      final String rawIncludes = get(moduleProperties, assemblyKey(assemblyName, key), assemblyKey(null, key),
         b2Key(key));
      return toRuledReferenceList(rawIncludes, GREATER_OR_EQUAL);
   }

   public String getFacetClassifier(PropertiesSource properties, String facetName) {
      if (facetName == null || facetName.length() == 0) {
         throw new IllegalArgumentException("facetName must not be empty.");
      }
      // TODO assert result is valid id
      return properties.get(facetKey(facetName, "classifier"), toValidId(facetName));
   }

   public PathMatcher getPluginMatcherForFacet(PropertiesSource moduleProperties, String facetName) {
      final String patterns = moduleProperties.get(facetKey(facetName, "pluginsFilter"), "**");
      return PathMatcher.parse(patterns, ".", ",");
   }

   public List<FeatureInclude> getIncludedFeaturesForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource) {
      final String key = isSource ? "includedSourceFeatures" : "includedFeatures";
      final String rawIncludes = get(moduleProperties, facetKey(facetName, key), facetKey(null, key), b2Key(key));
      return toFeatureIncludeList(rawIncludes);
   }

   private List<FeatureInclude> toFeatureIncludeList(final String rawIncludes) {
      final List<FeatureInclude> result = new ArrayList<FeatureInclude>();

      if (rawIncludes != null) {
         for (String rawInclude : rawIncludes.split(",")) {
            final String include = rawInclude.trim();
            if (include.length() > 0) {
               result.add(toFeatureInclude(include));
            }
         }
      }

      return result;
   }

   public List<PluginInclude> getIncludedPluginsForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource) {
      final String key = isSource ? "includedSourcePlugins" : "includedPlugins";
      final String rawIncludes = get(moduleProperties, facetKey(facetName, key), facetKey(null, key), b2Key(key));
      return toPluginIncludeList(rawIncludes);
   }

   private List<PluginInclude> toPluginIncludeList(final String rawIncludes) {
      final List<PluginInclude> result = new ArrayList<PluginInclude>();

      if (rawIncludes != null) {
         for (String rawInclude : rawIncludes.split(",")) {
            final String include = rawInclude.trim();
            if (include.length() > 0) {
               result.add(toPluginInclude(include));
            }
         }
      }

      return result;
   }

   private List<StrictReference> toStrictReferenceList(final String rawIncludes) {
      final List<StrictReference> result = new ArrayList<StrictReference>();

      if (rawIncludes != null) {
         for (String rawInclude : rawIncludes.split(",")) {
            final String include = rawInclude.trim();
            if (include.length() > 0) {
               result.add(toStrictReference(include));
            }
         }
      }

      return result;
   }

   public List<RuledReference> getRequiredFeaturesForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource) {
      final String key = isSource ? "requiredSourceFeatures" : "requiredFeatures";
      final String requirements = get(moduleProperties, facetKey(facetName, key), facetKey(null, key), b2Key(key));
      return toRuledReferenceList(requirements, GREATER_OR_EQUAL);
   }

   public List<RuledReference> getRequiredPluginsForFacet(PropertiesSource moduleProperties, String facetName,
      boolean isSource) {
      final String key = isSource ? "requiredSourcePlugins" : "requiredPlugins";
      final String requirements = get(moduleProperties, facetKey(facetName, key), facetKey(null, key), b2Key(key));
      return toRuledReferenceList(requirements, GREATER_OR_EQUAL);
   }

   private static final Function<String, VersionMatchRule> GREATER_OR_EQUAL = new Function<String, VersionMatchRule>() {
      @Override
      public VersionMatchRule apply(String input) {
         return VersionMatchRule.GREATER_OR_EQUAL;
      }
   };

   private static List<RuledReference> toRuledReferenceList(String rawRequirements,
      Function<String, VersionMatchRule> defaultVersionMatchRule) {
      final List<RuledReference> result = new ArrayList<RuledReference>();

      // foo.feature:1.0.0:compatible,
      if (rawRequirements != null) {
         for (String rawRequirement : rawRequirements.split(",")) {
            final String requirement = rawRequirement.trim();
            if (requirement.length() > 0) {
               result.add(toRuledReference(requirement, defaultVersionMatchRule));
            }
         }
      }

      return result;
   }

   private static RuledReference toRuledReference(String string,
      Function<String, VersionMatchRule> defaultVersionMatchRule) {
      final RuledReference ref = ModuleModelFactory.eINSTANCE.createRuledReference();
      final String[] segments = string.split(":");
      if (segments.length < 1 || segments.length > 3) {
         throw new IllegalArgumentException(string + " is not a valid requirement specification");
      }

      parseAndSetIdAndVersion(string, ref, segments);

      if (segments.length > 2) {
         final String ruleString = segments[2].trim();
         final VersionMatchRule rule = VersionMatchRule.get(ruleString);
         if (rule == null) {
            throw new IllegalArgumentException("'" + ruleString + "' in " + string
               + " is not a valid version matching rule");
         }
         ref.setVersionMatchRule(rule);
      }
      else {
         if (!ref.isSetVersion()) // force set
         {
            ref.setVersion("0.0.0");
         }
         ref.setVersionMatchRule(defaultVersionMatchRule.apply(ref.getId()));
      }

      return ref;
   }

   private static void parseAndSetIdAndVersion(String reference, final AbstractReference ref, final String[] segments) {
      // TODO assert is valid
      ref.setId(segments[0].trim());

      if (segments.length > 1) {
         final String versionString = segments[1].trim();
         try {
            new Version(versionString);
         }
         catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("'" + versionString + "' in " + reference + " is not a valid version");
         }
         ref.setVersion(versionString);
      }
   }

   private static FeatureInclude toFeatureInclude(String include) {
      // foo:1.0.0:optional
      final FeatureInclude inc = ModuleModelFactory.eINSTANCE.createFeatureInclude();
      final String[] segments = include.split(":");
      if (segments.length < 1 || segments.length > 3) {
         throw new IllegalArgumentException(include + " is not a valid feature include specification");
      }

      parseAndSetIdAndVersion(include, inc, segments);

      if (segments.length > 2) {
         final String optionalString = segments[2].trim();
         if (!"optional".equals(optionalString)) {
            throw new IllegalArgumentException("'" + optionalString + "' in " + include
               + " must be 'optional' or missing");
         }
         inc.setOptional(true);
      }

      return inc;
   }

   private static PluginInclude toPluginInclude(String include) {
      // foo:1.0.0:unpack
      final PluginInclude inc = ModuleModelFactory.eINSTANCE.createPluginInclude();
      final String[] segments = include.split(":");
      if (segments.length < 1 || segments.length > 3) {
         throw new IllegalArgumentException(include + " is not a valid plugin include specification");
      }

      parseAndSetIdAndVersion(include, inc, segments);

      inc.setUnpack(false);

      if (segments.length > 2) {
         final String optionalString = segments[2].trim();
         if (!"unpack".equals(optionalString)) {
            throw new IllegalArgumentException("'" + optionalString + "' in " + include
               + " must be 'optional' or missing");
         }
         inc.setUnpack(true);
      }

      return inc;
   }

   private static StrictReference toStrictReference(String include) {
      // foo:1.0.0
      final StrictReference ref = ModuleModelFactory.eINSTANCE.createStrictReference();
      final String[] segments = include.split(":");
      if (segments.length < 1 || segments.length > 2) {
         throw new IllegalArgumentException(include + " is not a valid reference specification");
      }

      parseAndSetIdAndVersion(include, ref, segments);

      return ref;
   }

   private static String assemblyKey(String assemblyName, String key) {
      if (Strings.isNullOrEmpty(assemblyName)) {
         return b2Key("assemblies." + key);
      }
      return b2Key("assemblies." + assemblyName + "." + key);
   }

   private static String facetKey(String facetName, String key) {
      if (Strings.isNullOrEmpty(facetName)) {
         return b2Key("facets." + key);
      }
      return b2Key("facets." + facetName + "." + key);
   }

   private static String b2Key(String key) {
      return "b2." + key;
   }

   private static String get(PropertiesSource moduleProperties, String... keys) {
      for (String key : keys) {
         final String value = moduleProperties.get(key);
         if (value != null) {
            return value;
         }
      }
      return null;
   }

   @Override
   public String getFeatureIdForAssembly(PropertiesSource moduleProperties, String assemblyName, String moduleId) {
      String featureId = moduleProperties.get(assemblyKey(assemblyName, "featureId"));
      if (featureId == null) {
         final String classifier = getAssemblyClassifier(moduleProperties, assemblyName);
         featureId = getFeatureId(moduleProperties, moduleId, classifier, false);
      }
      return featureId;
   }

   @Override
   public String getFeatureIdForFacet(PropertiesSource moduleProperties, String facetName, String moduleId,
      boolean isSource) {
      String featureId = moduleProperties.get(facetKey(facetName, isSource ? "sourceFeatureId" : "featureId"));
      if (featureId == null) {
         final String classifier = getFacetClassifier(moduleProperties, facetName);
         featureId = getFeatureId(moduleProperties, moduleId, classifier, isSource);
      }
      return featureId;
   }

   private String getFeatureId(PropertiesSource properties, String moduleId, String classifier, boolean isSource) {
      final StringBuilder sb = new StringBuilder();
      if (classifier != null) {
         sb.append(classifier);
      }
      if (isSource) {
         if (sb.length() > 0) {
            sb.append('.');
         }
         sb.append(properties.get("b2.featuresSourceClassifier", "sources"));
      }
      return idOfProject(moduleId, sb.toString(), properties.get("b2.featuresAppendix", "feature"));
   }

   public boolean isSkipBrandingPlugins(PropertiesSource properties) {
      return properties.getBoolean("b2.skipBrandingPlugins", false);
   }

   @Override
   public String getBrandingPluginIdForAssembly(PropertiesSource moduleProperties, String assemblyName, String moduleId) {
      String pluginId = moduleProperties.get(assemblyKey(assemblyName, "brandingPluginId"));
      if (pluginId == null) {
         final String classifier = getAssemblyClassifier(moduleProperties, assemblyName);
         pluginId = getBrandingPluginId(moduleProperties, moduleId, classifier, false);
      }
      return pluginId;
   }

   @Override
   public String getBrandingPluginIdForFacet(PropertiesSource moduleProperties, String facetName, String moduleId,
      boolean isSource) {
      String pluginId = moduleProperties.get(facetKey(facetName, isSource
         ? "sourceBrandingPluginId"
         : "brandingPluginId"));
      if (pluginId == null) {
         final String classifier = getFacetClassifier(moduleProperties, facetName);
         pluginId = getBrandingPluginId(moduleProperties, moduleId, classifier, isSource);
      }
      return pluginId;
   }

   private String getBrandingPluginId(PropertiesSource properties, String moduleId, String classifier, boolean isSource) {
      final StringBuilder sb = new StringBuilder();
      if (classifier != null) {
         sb.append(classifier);
      }
      if (isSource) {
         if (sb.length() > 0) {
            sb.append('.');
         }
         sb.append(properties.get("b2.featuresSourceClassifier", "sources"));
      }
      return idOfProject(moduleId, sb.toString(), properties.get("b2.brandingPluginsAppendix", "branding"));
   }

   public String getSourcePluginId(PropertiesSource moduleProperties, String pluginId) {
      final StringBuilder sb = new StringBuilder();
      sb.append(pluginId);
      if (sb.length() > 0) {
         sb.append('.');
         sb.append(moduleProperties.get("b2.pluginsSourceClassifier", "source"));
      }
      return sb.toString();
   }

   public List<String> getAssemblyCategories(PropertiesSource moduleProperties, String assemblyName) {
      final String rawCategories = getRawAssemblyCategories(moduleProperties, assemblyName);
      final List<String> categories = new ArrayList<String>();
      if (rawCategories != null) {
         for (String rawAssembly : rawCategories.split(",")) {
            final String assembly = rawAssembly.trim();
            if (assembly.length() > 0 && !categories.contains(assembly)) {
               categories.add(assembly);
            }
         }
      }
      return categories;
   }

   private String getRawAssemblyCategories(PropertiesSource moduleProperties, String assemblyName) {
      String rawCategories = get(moduleProperties, assemblyKey(assemblyName, "categories"),
         assemblyKey(null, "categories"));
      if (rawCategories == null) {
         rawCategories = "assembled, included";
      }
      return rawCategories;
   }

   public PathMatcher getAssemblySiteFeatureMatcher(PropertiesSource moduleProperties, String assemblyName) {
      String filterPatterns = get(moduleProperties, assemblyKey(assemblyName, "siteFeaturesFilter"),
         assemblyKey(null, "siteFeaturesFilter"));
      if (Strings.isNullOrEmpty(filterPatterns)) {
         filterPatterns = "**";
      }
      return PathMatcher.parsePackagePatterns(filterPatterns);
   }

   public PathMatcher getAssemblyCategoryFeatureMatcher(PropertiesSource moduleProperties, String moduleId,
      String assemblyName, String category) {
      String defaultFilter;
      if ("included".equals(category)) {
         final String assemblyClassifier = getAssemblyClassifier(moduleProperties, assemblyName);
         defaultFilter = "**,!" + getFeatureId(moduleProperties, moduleId, assemblyClassifier, false);
      }
      else if ("assembled".equals(category)) {
         final String assemblyClassifier = getAssemblyClassifier(moduleProperties, assemblyName);
         defaultFilter = getFeatureId(moduleProperties, moduleId, assemblyClassifier, false);
      }
      else {
         defaultFilter = "**";
      }

      final String filter = moduleProperties.get(assemblyKey(assemblyName, "categories." + category + ".filter"),
         defaultFilter);

      return PathMatcher.parsePackagePatterns(filter);
   }

   public String getSiteIdForAssembly(PropertiesSource moduleProperties, String moduleId, String assemblyName) {
      String siteId = moduleProperties.get(assemblyKey(assemblyName, "siteId"));
      if (siteId == null) {
         siteId = idOfProject(moduleId, getAssemblyClassifier(moduleProperties, assemblyName),
            moduleProperties.get("b2.sitesAppendix", "site"));
      }
      return siteId;
   }

   private static String idOfProject(String moduleId, String classifier, String appendix) {
      final StringBuilder sb = new StringBuilder();
      sb.append(moduleId);
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

   protected static String toValidId(String string) {
      // TODO assert not empty
      return toJavaIdentifier(string.toLowerCase());
   }

   /**
    * Converts the specified string into a valid Java identifier. All illegal characters are replaced by underscores.
    * 
    * @param aString <i>(required)</i>. The string must contain at least one character.
    * @return <i>(required)</i>.
    */
   private static String toJavaIdentifier(String aString) {
      if (aString.length() == 0) {
         return "_";
      }

      final StringBuilder res = new StringBuilder();
      int idx = 0;
      char c = aString.charAt(idx);
      if (Character.isJavaIdentifierStart(c)) {
         res.append(c);
         idx++;
      }
      else if (Character.isJavaIdentifierPart(c)) {
         res.append('_');
      }
      while (idx < aString.length()) {
         c = aString.charAt(idx++);
         res.append(Character.isJavaIdentifierPart(c) || c == '.' ? c : '_');
      }
      return res.toString();
   }

   public String getModuleVersion(PropertiesSource moduleProperties) {
      return moduleProperties.get("b2.moduleVersion", "0.1.0.qualifier");
   }

   public String getNameSpace(PropertiesSource moduleProperties) {
      return moduleProperties.get("b2.moduleNameSpace", "b2.module");
   }

   @Override
   public PathMatcher getResourceMatcherForProduct(PropertiesSource moduleProperties, String productId) {
      String patterns = get(moduleProperties, productKey(productId, "resources"), productKey(null, "resources"));
      if (patterns == null) {
         patterns = "!**";
      }
      return PathMatcher.parse(patterns, "/", ",");
   }

   @Override
   public VersionMatchRule getDefaultVersionMatchRuleForProduct(PropertiesSource moduleProperties, String productId) {
      final String key = "defaultVersionMatchRule";
      final String defaultVersionMatchRule = get(moduleProperties, productKey(productId, key), productKey(null, key));
      final VersionMatchRule rule = VersionMatchRule.get(defaultVersionMatchRule);
      checkNotNull(rule);
      return rule;
   }

   @Override
   public List<RuledReference> getIncludedFeaturesForProduct(final PropertiesSource moduleProperties,
      final String productId, final VersionMatchRule defaultVersionMatchRule) {
      final String key = "features";
      final String rawIncludes = get(moduleProperties, productKey(productId, key), productKey(null, key));
      return toRuledReferenceList(rawIncludes, new Function<String, VersionMatchRule>() {
         @Override
         public VersionMatchRule apply(String featureId) {
            return getVersionMatchRuleForProductInclude(moduleProperties, productId, featureId, defaultVersionMatchRule);
         }
      });
   }

   @Override
   public List<RuledReference> getIncludedPluginsForProduct(final PropertiesSource moduleProperties,
      final String productId, final VersionMatchRule defaultVersionMatchRule) {
      final String key = "plugins";
      final String rawIncludes = get(moduleProperties, productKey(productId, key), productKey(null, key));
      return toRuledReferenceList(rawIncludes, new Function<String, VersionMatchRule>() {
         @Override
         public VersionMatchRule apply(String pluginId) {
            return getVersionMatchRuleForProductInclude(moduleProperties, productId, pluginId, defaultVersionMatchRule);
         }
      });
   }

   @Override
   public VersionMatchRule getVersionMatchRuleForProductInclude(PropertiesSource moduleProperties, String productId,
      String featureOrPluginId, VersionMatchRule defaultVersionMatchRule) {
      final String key = "versionMatchRules";
      final String rawRules = get(moduleProperties, productKey(productId, key), productKey(null, key));
      if (rawRules != null) {
         for (String mapping : rawRules.split(";")) {
            final String[] patternToRule = mapping.split("=");
            checkState(patternToRule.length == 2, "Invalid mapping %s", mapping);
            if (parsePackagePatterns(patternToRule[0].trim()).isMatch(featureOrPluginId)) {
               final String literal = patternToRule[1].trim();
               final VersionMatchRule rule = VersionMatchRule.get(literal);
               checkNotNull(rule, "Invalid version match rule %s", literal);
               return rule;
            }
         }
      }
      return defaultVersionMatchRule;
   }

   public List<String> getUpdateSitesForProduct(PropertiesSource moduleProperties, String productId) {
      final String key = "sites";
      final String rawSites = get(moduleProperties, productKey(productId, key), productKey(null, key));

      final List<String> sites = new ArrayList<String>();

      if (rawSites != null) {
         for (String rawInclude : rawSites.split(",")) {
            final String site = rawInclude.trim();
            if (site.length() > 0) {
               sites.add(site);
            }
         }
      }

      return sites;
   }

   private static String productKey(String productId, String key) {
      if (Strings.isNullOrEmpty(productId)) {
         return b2Key("products." + key);
      }
      return b2Key("products." + productId + "." + key);
   }
}
