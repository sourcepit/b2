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

package org.sourcepit.b2.validation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.directory.parser.module.ModuleParserLifecycleParticipant;
import org.sourcepit.b2.model.builder.util.ModuleWalker;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.common.utils.props.PropertiesSource;

/**
 * @author Bernd Vogt <bernd.vogt@sourcepit.org>
 */
@Named
public class ModuleValidation implements ModuleParserLifecycleParticipant {
   @Inject
   private Logger logger;

   @Inject
   private Map<String, ModuleValidationConstraint> constraintMap;

   public void preParse(IModuleParsingRequest request) { // noop
   }

   public void postParse(IModuleParsingRequest request, AbstractModule module, ThrowablePipe errors) {
      logger.info("Validating " + module.getId() + ".");
      if (module != null && errors.isEmpty()) {
         final PropertiesSource properties = request.getModuleProperties();

         final Map<String, ModuleValidationConstraint> enabledConstraintsMap = getEnabledConstraints(properties);

         validate(enabledConstraintsMap, module, properties);
      }
   }

   private Map<String, ModuleValidationConstraint> getEnabledConstraints(final PropertiesSource properties) {
      final Map<String, ModuleValidationConstraint> enabledConstraintsMap = new LinkedHashMap<String, ModuleValidationConstraint>();
      for (Entry<String, ModuleValidationConstraint> entry : constraintMap.entrySet()) {
         final String constraintId = entry.getKey();
         if (isConstraintEnabled(properties, constraintId)) {
            enabledConstraintsMap.put(constraintId, entry.getValue());
         }
      }
      return enabledConstraintsMap;
   }

   private void validate(final Map<String, ModuleValidationConstraint> enabledConstraintsMap, AbstractModule module,
      final PropertiesSource properties) {
      final boolean quickFixesEnabled = properties.getBoolean("b2.validation.quickFixes.enabled", false);
      if (quickFixesEnabled) {
         logger.info("Quick fixes are enabled.");
      }
      else {
         logger.info("Quick fixes are disabled. You can enable it by setting the propety b2.validation.quickFixes.enabled=true.");
      }

      final ModuleWalker walker = new ModuleWalker(false, true) {
         @Override
         protected boolean doVisit(EObject eObject) {
            for (Entry<String, ModuleValidationConstraint> entry : enabledConstraintsMap.entrySet()) {
               final String constraintId = entry.getKey();
               final ModuleValidationConstraint constraint = entry.getValue();
               constraint.validate(eObject, properties,
                  quickFixesEnabled ? isQuickFixesEnabled(properties, constraintId) : false);
            }
            return true;
         }
      };
      walker.walk(module);
   }

   private boolean isConstraintEnabled(PropertiesSource properties, String constraintId) {
      return properties.getBoolean("b2.validation.constraints." + constraintId + ".enabled", true);
   }

   private boolean isQuickFixesEnabled(PropertiesSource properties, String constraintId) {
      return properties.getBoolean("b2.validation.quickFixes." + constraintId + ".enabled", true);
   }
}
