/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import javax.validation.constraints.NotNull;

import org.sourcepit.common.utils.props.PropertiesSource;

public class DefaultConverter2 implements Converter2
{
   // public String getCategoryId(PropertiesSource properties, String facetName)
   // {
   // if (facetName.length() == 0)
   // {
   // throw new IllegalArgumentException("facetName must not be empty.");
   // }
   //
   // // TODO assert result is valid id
   // return properties.get("b2.facets['" + facetName + "'].categoryId", toValidId(facetName));
   // }

   private static String toValidId(String string)
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

   public String getFacetClassifier(PropertiesSource properties, String facetName)
   {
      if (facetName.length() == 0)
      {
         throw new IllegalArgumentException("facetName must not be empty.");
      }
      // TODO assert result is valid id
      return properties.get("b2.facets['" + facetName + "'].classifier", toValidId(facetName));
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

   public String getSourcePluginId(@NotNull PropertiesSource moduleProperties, @NotNull String pluginId)
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

   private String getSourceClassifer(PropertiesSource properties, final String customKey, final String defaultKey)
   {
      final String value = getPropertyValue(properties, customKey, defaultKey);
      if (value.length() == 0)
      {
         throw new IllegalStateException("Source classifer must not be empty for:" + customKey + ", " + defaultKey);
      }
      return toValidId(value);
   }

   private String getPropertyValue(PropertiesSource properties, final String customKey, final String defaultKey)
   {
      String value = properties.get(customKey);
      if (value == null)
      {
         value = properties.get(defaultKey);
      }
      if (value == null)
      {
         throw new IllegalStateException("No value found for: " + customKey + ", " + defaultKey);
      }
      return value;
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

   private static String spacer(String classifier)
   {
      return classifier.length() == 0 ? "." : "." + classifier + ".";
   }
}
