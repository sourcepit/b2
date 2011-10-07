
package org.sourcepit.b2.common.internal.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class PathUtils
{
   public static String trimFileExtension(String fileName)
   {
      if (fileName == null)
      {
         throw new IllegalArgumentException("File name must not be null.");
      }
      int idx = fileName.lastIndexOf('.');
      if (idx > -1)
      {
         return fileName.substring(0, idx);
      }
      return fileName;
   }

   /**
    * Get the relative path from one file to another, specifying the directory separator. If one of the provided
    * resources does not exist, it is assumed to be a file unless it ends with '/' or '\'.
    * 
    * @param target targetPath is calculated to this file
    * @param base basePath is calculated from this file
    * @param separator directory separator. The platform default is not assumed so that we can test Unix behaviour when
    *           running on Windows (for example)
    * @return
    */
   public static String getRelativePath(File targetPath, File basePath, String pathSeparator)
   {
      return getRelativePath(targetPath.toString(), basePath.toString(), pathSeparator);
   }

   /**
    * Get the relative path from one file to another, specifying the directory separator. If one of the provided
    * resources does not exist, it is assumed to be a file unless it ends with '/' or '\'.
    * 
    * @param target targetPath is calculated to this file
    * @param base basePath is calculated from this file
    * @param separator directory separator. The platform default is not assumed so that we can test Unix behaviour when
    *           running on Windows (for example)
    * @return
    */
   public static String getRelativePath(String targetPath, String basePath, String pathSeparator)
   {
      // Normalize the paths
      String normalizedTargetPath = FilenameUtils.normalizeNoEndSeparator(targetPath);
      String normalizedBasePath = FilenameUtils.normalizeNoEndSeparator(basePath);

      // Undo the changes to the separators made by normalization
      if (pathSeparator.equals("/"))
      {
         normalizedTargetPath = FilenameUtils.separatorsToUnix(normalizedTargetPath);
         normalizedBasePath = FilenameUtils.separatorsToUnix(normalizedBasePath);

      }
      else if (pathSeparator.equals("\\"))
      {
         normalizedTargetPath = FilenameUtils.separatorsToWindows(normalizedTargetPath);
         normalizedBasePath = FilenameUtils.separatorsToWindows(normalizedBasePath);

      }
      else
      {
         throw new IllegalArgumentException("Unrecognised dir separator '" + pathSeparator + "'");
      }

      String[] base = normalizedBasePath.split(Pattern.quote(pathSeparator));
      String[] target = normalizedTargetPath.split(Pattern.quote(pathSeparator));

      // First get all the common elements. Store them as a string,
      // and also count how many of them there are.
      StringBuffer common = new StringBuffer();

      int commonIndex = 0;
      while (commonIndex < target.length && commonIndex < base.length && target[commonIndex].equals(base[commonIndex]))
      {
         common.append(target[commonIndex] + pathSeparator);
         commonIndex++;
      }

      if (commonIndex == 0)
      {
         // No single common path element. This most
         // likely indicates differing drive letters, like C: and D:.
         // These paths cannot be relativized.
         throw new PathResolutionException("No common path element found for '" + normalizedTargetPath + "' and '"
            + normalizedBasePath + "'");
      }

      // The number of directories we have to backtrack depends on whether the base is a file or a dir
      // For example, the relative path from
      //
      // /foo/bar/baz/gg/ff to /foo/bar/baz
      //
      // ".." if ff is a file
      // "../.." if ff is a directory
      //
      // The following is a heuristic to figure out if the base refers to a file or dir. It's not perfect, because
      // the resource referred to by this path may not actually exist, but it's the best I can do
      boolean baseIsFile = true;

      File baseResource = new File(normalizedBasePath);

      if (baseResource.exists())
      {
         baseIsFile = baseResource.isFile();

      }
      else if (basePath.endsWith(pathSeparator))
      {
         baseIsFile = false;
      }

      StringBuffer relative = new StringBuffer();

      if (base.length != commonIndex)
      {
         int numDirsUp = baseIsFile ? base.length - commonIndex - 1 : base.length - commonIndex;

         for (int i = 0; i < numDirsUp; i++)
         {
            relative.append(".." + pathSeparator);
         }
      }
      relative.append(normalizedTargetPath.substring(common.length()));
      return relative.toString();
   }

   public static class PathResolutionException extends RuntimeException
   {
      private static final long serialVersionUID = 1L;

      PathResolutionException(String msg)
      {
         super(msg);
      }
   }

   public static List<String> toJavaList(String string, String separator)
   {
      if (string != null)
      {
         final List<String> list = new ArrayList<String>();
         for (String segment : string.split(separator))
         {
            list.add(segment.trim());
         }
         return list;
      }
      return null;
   }

   public static String toStringList(String[] strings, String separator)
   {
      if (strings != null)
      {
         return toStringList(Arrays.asList(strings), separator);
      }
      return null;
   }

   public static String toStringList(Collection<String> strings, String separator)
   {
      if (strings != null)
      {
         final StringBuilder sb = new StringBuilder();
         for (String string : strings)
         {
            sb.append(separator);
            sb.append(string);
         }
         if (sb.length() > 0)
         {
            sb.delete(0, separator.length());
         }
         return sb.toString();
      }
      return null;
   }
}