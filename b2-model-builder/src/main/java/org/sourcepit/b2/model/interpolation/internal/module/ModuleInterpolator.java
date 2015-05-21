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

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.execution.LifecyclePhase;
import org.sourcepit.b2.model.interpolation.module.IModuleInterpolationRequest;
import org.sourcepit.b2.model.interpolation.module.IModuleInterpolator;
import org.sourcepit.b2.model.interpolation.module.ModuleInterpolatorLifecycleParticipant;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ModuleInterpolator implements IModuleInterpolator {
   @Inject
   private List<ModuleInterpolatorLifecycleParticipant> lifecycleParticipants;

   @Inject
   private FeaturesInterpolator featuresInterpolator;

   @Inject
   private SitesInterpolator sitesInterpolator;

   public void interpolate(IModuleInterpolationRequest request) {
      checkRequest(request);
      newLifecycleMethod().execute(request);
   }

   private LifecyclePhase<Void, IModuleInterpolationRequest, ModuleInterpolatorLifecycleParticipant> newLifecycleMethod() {
      return new LifecyclePhase<Void, IModuleInterpolationRequest, ModuleInterpolatorLifecycleParticipant>(
         lifecycleParticipants) {
         @Override
         protected void pre(ModuleInterpolatorLifecycleParticipant participant, IModuleInterpolationRequest input) {
            participant.preInterpolation(input.getModule(), input.getModuleProperties());
         }

         @Override
         protected Void doExecute(IModuleInterpolationRequest input) {
            doInterpolate(input);
            return null;
         }

         @Override
         protected void post(ModuleInterpolatorLifecycleParticipant participant, IModuleInterpolationRequest input,
            Void result, ThrowablePipe errors) {
            participant.postInterpolation(input.getModule(), input.getModuleProperties(), errors);
         }
      };
   }

   private void checkRequest(IModuleInterpolationRequest request) {
      if (request == null) {
         throw new IllegalArgumentException("Request must not be null.");
      }

      if (request.getModule() == null) {
         throw new IllegalArgumentException("Module must not be null.");
      }

      final PropertiesSource properties = request.getModuleProperties();
      if (properties == null) {
         throw new IllegalArgumentException("properties must not be null.");
      }
   }

   private void doInterpolate(IModuleInterpolationRequest request) {
      final AbstractModule module = request.getModule();
      final PropertiesSource properties = request.getModuleProperties();
      featuresInterpolator.interpolate(module, properties);
      sitesInterpolator.interpolate(module, properties);
   }

}
