/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.execution;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import org.apache.commons.exec.OS;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.directory.parser.module.ModuleParserLifecycleParticipant;
import org.sourcepit.b2.internal.cleaner.ModuleCleanerLifecycleParticipant;
import org.sourcepit.b2.internal.generator.B2GeneratorLifecycleParticipant;
import org.sourcepit.b2.internal.generator.DefaultTemplateCopier;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.b2.model.builder.util.DecouplingModelCache;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.interpolation.module.ModuleInterpolatorLifecycleParticipant;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.lang.ThrowablePipe;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class LifecycleParticipantsTest extends AbstractB2SessionWorkspaceTest
{
   @Inject
   private B2SessionRunner sessionRunner;

   @Inject
   private LayoutManager layoutManager;

   private LifecycleParticipant participant;

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);

      participant = new LifecycleParticipant();
      binder.bind(newKey(B2SessionLifecycleParticipant.class, participant)).toInstance(participant);
      binder.bind(newKey(ModuleCleanerLifecycleParticipant.class, participant)).toInstance(participant);
      binder.bind(newKey(ModuleParserLifecycleParticipant.class, participant)).toInstance(participant);
      binder.bind(newKey(ModuleInterpolatorLifecycleParticipant.class, participant)).toInstance(participant);
      binder.bind(newKey(B2GeneratorLifecycleParticipant.class, participant)).toInstance(participant);
   }

   private <T> Key<T> newKey(Class<T> type, LifecycleParticipant participant)
   {
      return Key.get(type, Names.named(participant.getClass().getName()));
   }

   @Override
   protected String setUpModulePath()
   {
      return "composed-component";
   }

   public void testAll() throws Exception
   {
      final ResourceSetImpl resourceSet = new ResourceSetImpl();
      final DecouplingModelCache modelCache = new DecouplingModelCache(resourceSet, layoutManager);

      final B2RequestFactory requestFactory = new B2RequestFactory()
      {
         public B2Request newRequest(B2Session session)
         {
            PropertiesMap properties = new LinkedPropertiesMap();
            IConverter converter = ConverterUtils.newDefaultTestConverter(properties);
            B2Request request = new B2Request();
            request.setModuleDirectory(session.getCurrentProject().getDirectory());
            request.setConverter(converter);
            request.setInterpolate(true);
            request.setTemplates(new DefaultTemplateCopier());
            request.setModelCache(modelCache);
            return request;
         }
      };

      final B2Session session = getCurrentSession();

      while (sessionRunner.prepareNext(session, requestFactory))
      { // noop
      }

      session.setCurrentProject(session.getProjects().get(0));
      while (sessionRunner.finalizeNext(session))
      { // noop
      }

      final boolean isLinux = OS.isFamilyUnix();

      List<String> excpectedCalls = new ArrayList<String>();
      excpectedCalls.add("prePrepareProjects ( session )");

      if (isLinux)
      {
         addPrepareCalls(excpectedCalls, "structured-layout", "structured-layout",
            "org.sourcepit.b2.test.resources.structured.layout");
         addPrepareCalls(excpectedCalls, "simple-layout", "simple-layout",
            "org.sourcepit.b2.test.resources.simple.layout");
      }
      else
      {
         addPrepareCalls(excpectedCalls, "simple-layout", "simple-layout",
            "org.sourcepit.b2.test.resources.simple.layout");
         addPrepareCalls(excpectedCalls, "structured-layout", "structured-layout",
            "org.sourcepit.b2.test.resources.structured.layout");
      }

      // project composite-layout
      addPrepareCalls(excpectedCalls, "testAll", "composite-layout", "org.sourcepit.b2.test.resources.composite.layout");

      excpectedCalls.add("postPrepareProjects ( session, null )");

      // finalize
      excpectedCalls.add("preFinalizeProjects ( session )");
      if (isLinux)
      {
         // project structured-layout
         addFinalizeCalls(excpectedCalls, "structured-layout", "structured-layout",
            "org.sourcepit.b2.test.resources.structured.layout");

         // project simple-layout
         addFinalizeCalls(excpectedCalls, "simple-layout", "simple-layout",
            "org.sourcepit.b2.test.resources.simple.layout");
      }
      else
      {
         // project simple-layout
         addFinalizeCalls(excpectedCalls, "simple-layout", "simple-layout",
            "org.sourcepit.b2.test.resources.simple.layout");

         // project structured-layout
         addFinalizeCalls(excpectedCalls, "structured-layout", "structured-layout",
            "org.sourcepit.b2.test.resources.structured.layout");
      }

      // project composite-layout
      addFinalizeCalls(excpectedCalls, "composite-layout", "composite-layout",
         "org.sourcepit.b2.test.resources.composite.layout");

      excpectedCalls.add("postFinalizeProjects ( session, null )");

      List<String> actualCalls = new ArrayList<String>();
      for (MethodCall call : participant.getCalls())
      {
         actualCalls.add(toString(call));
      }

      StringBuilder message = new StringBuilder();

      Iterator<String> actual = actualCalls.iterator();
      Iterator<String> expected = excpectedCalls.iterator();
      while (expected.hasNext())
      {
         String expectedCall = expected.next();
         assertEquals(message.toString(), expectedCall, actual.next());
         message.append(expectedCall);
         message.append("\n");
      }

      assertSame(excpectedCalls.size(), actualCalls.size());
   }

   private void addFinalizeCalls(List<String> excpectedCalls, String folderName, String artifactId, String moduleId)
   {
      // project composite-layout
      excpectedCalls.add("preFinalizeProject ( session, " + artifactId + " )");
      excpectedCalls.add("postFinalizeProject ( session, " + artifactId + ", null )");
   }

   private void addPrepareCalls(List<String> excpectedCalls, String folderName, String artifactId, String moduleId)
   {
      excpectedCalls.add("prePrepareProject ( session, " + artifactId + ", request )");
      excpectedCalls.add("preClean ( " + folderName + " )");
      excpectedCalls.add("postClean ( " + folderName + ", null )");
      excpectedCalls.add("preParse ( " + folderName + " )");
      excpectedCalls.add("postParse ( " + folderName + ", " + moduleId + ", null )");
      excpectedCalls.add("preInterpolation ( " + moduleId + " )");
      excpectedCalls.add("postInterpolation ( " + moduleId + ", null )");
      excpectedCalls.add("preGenerate ( " + moduleId + " )");
      excpectedCalls.add("postGenerate ( " + moduleId + ", null )");
      excpectedCalls.add("postPrepareProject ( session, " + artifactId + ", request, " + moduleId + ", null )");
   }

   private String toString(MethodCall call)
   {
      StringBuilder sb = new StringBuilder();
      sb.append(call.getMethodName());
      sb.append(" ( ");
      Object[] args = call.getArgs();
      for (Object arg : args)
      {
         sb.append(arg);
         sb.append(", ");
      }

      if (args.length > 0)
      {
         sb.deleteCharAt(sb.length() - 1);
         sb.deleteCharAt(sb.length() - 1);
      }
      sb.append(" )");
      return sb.toString();
   }

   public static class MethodCall
   {
      private String className;
      private String methodName;
      private Object[] args;

      public MethodCall(String className, String methodName, Object[] args)
      {
         this.className = className;
         this.methodName = methodName;
         this.args = args;
      }

      public String getClassName()
      {
         return className;
      }

      public String getMethodName()
      {
         return methodName;
      }

      public Object[] getArgs()
      {
         return args;
      }
   }

   private static class LifecycleParticipant
      implements
         B2SessionLifecycleParticipant,
         ModuleCleanerLifecycleParticipant,
         ModuleParserLifecycleParticipant,
         ModuleInterpolatorLifecycleParticipant,
         B2GeneratorLifecycleParticipant
   {
      private List<MethodCall> calls = new ArrayList<MethodCall>();

      private Stack<File> barrier = new Stack<File>();

      public List<MethodCall> getCalls()
      {
         return calls;
      }

      private void recordMethodCall(Object... args)
      {
         StackTraceElement[] elements = Thread.currentThread().getStackTrace();
         StackTraceElement element = elements[2];
         String className = element.getClassName();
         String methodName = element.getMethodName();
         calls.add(new MethodCall(className, methodName, args));
      }

      public void prePrepareProjects(B2Session session)
      {
         recordMethodCall("session");
      }

      public void prePrepareProject(B2Session session, ModuleProject project, B2Request request)
      {
         recordMethodCall("session", project.getArtifactId(), "request");
      }

      public void preClean(File moduleDir)
      {
         recordMethodCall(moduleDir.getName());
      }

      public void postClean(File moduleDir, ThrowablePipe errors)
      {
         recordMethodCall(moduleDir.getName(), errors.isEmpty() ? null : errors);
      }

      public void preParse(IModuleParsingRequest request)
      {
         if (!barrier.isEmpty())
         {
            throw new IllegalStateException("Nested module parsing detected");
         }
         final File moduleDir = request.getModuleDirectory();
         barrier.push(moduleDir);

         assertNotNull(moduleDir);
         assertTrue(moduleDir.exists());
         recordMethodCall(moduleDir.getName());
      }

      public void postParse(IModuleParsingRequest request, AbstractModule module, ThrowablePipe errors)
      {
         barrier.pop();

         if (!errors.isEmpty())
         {
            return;
         }
         final File moduleDir = request.getModuleDirectory();
         assertNotNull(moduleDir);
         assertTrue(moduleDir.exists());
         assertNotNull(module);
         assertEquals(moduleDir, module.getDirectory());
         assertNotNull(errors);
         recordMethodCall(moduleDir.getName(), module.getId(), errors.isEmpty() ? null : errors);
      }

      public void preInterpolation(AbstractModule module)
      {
         assertNotNull(module);
         recordMethodCall(module.getId());
      }

      public void postInterpolation(AbstractModule module, ThrowablePipe errors)
      {
         assertNotNull(module);
         assertNotNull(errors);
         assertTrue(errors.isEmpty());
         recordMethodCall(module.getId(), errors.isEmpty() ? null : errors);
      }

      public void preGenerate(AbstractModule module)
      {
         assertNotNull(module);
         recordMethodCall(module.getId());
      }

      public void postGenerate(AbstractModule module, ThrowablePipe errors)
      {
         assertNotNull(module);
         assertNotNull(errors);
         assertTrue(errors.isEmpty());
         recordMethodCall(module.getId(), errors.isEmpty() ? null : errors);
      }

      public void postPrepareProject(B2Session session, ModuleProject project, B2Request request,
         AbstractModule module, ThrowablePipe errors)
      {
         recordMethodCall("session", project.getArtifactId(), "request", module.getId(), errors.isEmpty()
            ? null
            : errors);
      }

      public void postPrepareProjects(B2Session session, ThrowablePipe errors)
      {
         recordMethodCall("session", errors.isEmpty() ? null : errors);
      }

      public void preFinalizeProjects(B2Session session)
      {
         recordMethodCall("session");

      }

      public void preFinalizeProject(B2Session session, ModuleProject project)
      {
         recordMethodCall("session", project.getArtifactId());
      }

      public void postFinalizeProject(B2Session session, ModuleProject project, ThrowablePipe errors)
      {
         recordMethodCall("session", project.getArtifactId(), errors.isEmpty() ? null : errors);
      }

      public void postFinalizeProjects(B2Session session, ThrowablePipe errors)
      {
         recordMethodCall("session", errors.isEmpty() ? null : errors);
      }
   }
}
