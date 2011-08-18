/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.common.internal.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public interface PropertiesMap extends Map<String, String>
{
   Map<String, String> getDefaultProperties();

   String get(String key, String defaultValue);
   
   boolean getBoolean(String key, boolean defaultValue);

   Properties toJavaProperties();

   <K extends Object, V extends Object> void putMap(Map<K, V> map);

   /**
    * @throws IllegalArgumentException if the input stream contains a malformed Unicode escape sequence.
    * @throws IllegalStateException if an error occurred when reading from the input stream.
    */
   void load(InputStream inputStream);
   
   void load(ClassLoader classLoader, String resourcePath);

   /**
    * @throws IllegalArgumentException if the input stream contains a malformed Unicode escape sequence, or the file
    *            cannot be foud
    * @throws IllegalStateException if an error occurred when reading from the input stream.
    */
   void load(File file);
}
