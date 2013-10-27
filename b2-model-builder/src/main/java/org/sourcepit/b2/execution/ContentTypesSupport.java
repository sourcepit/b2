/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Named;

import org.sourcepit.common.utils.content.ContentType;
import org.sourcepit.common.utils.content.ContentTypes;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ContentTypesSupport implements ContentTypes
{
   @Override
   public ContentType detect(String fileName, InputStream content, String targetEncoding, PropertiesSource options)
      throws IOException
   {
      return ContentTypes.DEFAULT.detect(fileName, content, targetEncoding, options);
   }
}
