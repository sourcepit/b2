/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;
public interface TychoConstants
{
   String TYCHO_GROUP_ID = "org.eclipse.tycho";
   String TYCHO_VERSION_PROPERTY = "${tycho.version}";

   String TYCHO_TPC_PLUGIN_ARTIFACT_ID = "target-platform-configuration";
   
   String TYPE_ECLIPSE_PLUGIN = "eclipse-plugin";
   String TYPE_ECLIPSE_TEST_PLUGIN = "eclipse-test-plugin";
   String TYPE_ECLIPSE_FEATURE = "eclipse-feature";
}
