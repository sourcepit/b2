/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.execution.LifecyclePhase;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.interpolation.module.IModuleInterpolationRequest;
import org.sourcepit.b2.model.interpolation.module.IModuleInterpolator;
import org.sourcepit.b2.model.interpolation.module.ModuleInterpolatorLifecycleParticipant;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.common.utils.props.PropertiesMap;

@Named
public class ModuleInterpolator implements IModuleInterpolator
{
   @Inject
   private List<ModuleInterpolatorLifecycleParticipant> lifecycleParticipants;

   @Inject
   private FeaturesInterpolator featuresInterpolator;

   @Inject
   private SitesInterpolator sitesInterpolator;

   public void interpolate(IModuleInterpolationRequest request)
   {
      checkRequest(request);
      newLifecycleMethod().execute(request);
   }

   private LifecyclePhase<Void, IModuleInterpolationRequest, ModuleInterpolatorLifecycleParticipant> newLifecycleMethod()
   {
      return new LifecyclePhase<Void, IModuleInterpolationRequest, ModuleInterpolatorLifecycleParticipant>(
         lifecycleParticipants)
      {
         @Override
         protected void pre(ModuleInterpolatorLifecycleParticipant participant, IModuleInterpolationRequest input)
         {
            participant.preInterpolation(input.getModule());
         }

         @Override
         protected Void doExecute(IModuleInterpolationRequest input)
         {
            doInterpolate(input);
            return null;
         }

         @Override
         protected void post(ModuleInterpolatorLifecycleParticipant participant, IModuleInterpolationRequest input,
            Void result, ThrowablePipe errors)
         {
            participant.postInterpolation(input.getModule(), errors);
         }
      };
   }

   private void checkRequest(IModuleInterpolationRequest request)
   {
      if (request == null)
      {
         throw new IllegalArgumentException("Request must not be null.");
      }

      if (request.getModule() == null)
      {
         throw new IllegalArgumentException("Module must not be null.");
      }

      final IConverter converter = request.getConverter();
      if (converter == null)
      {
         throw new IllegalArgumentException("converter must not be null.");
      }
   }

   private void doInterpolate(IModuleInterpolationRequest request)
   {
      final AbstractModule module = request.getModule();
      final PropertiesMap properties = request.getConverter().getProperties();
      featuresInterpolator.interpolate(module, properties);
      sitesInterpolator.interpolate(module, properties);
   }

}
