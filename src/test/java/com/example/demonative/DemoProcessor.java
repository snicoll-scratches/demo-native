package com.example.demonative;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import com.squareup.javapoet.JavaFile;

import org.springframework.aot.context.bootstrap.generator.ApplicationContextAotProcessor;
import org.springframework.aot.context.bootstrap.generator.infrastructure.DefaultBootstrapWriterContext;
import org.springframework.aot.test.context.bootstrap.generator.TestContextAotProcessor;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mock.env.MockEnvironment;

public class DemoProcessor {

	public static void main(String[] args) throws IOException {
		DemoProcessor processor = new DemoProcessor();
		processor.generateMainContext();
	}

	public void generateMainContext() throws IOException {
		GenericApplicationContext context = prepareContext();
		ApplicationContextAotProcessor processor = new ApplicationContextAotProcessor(context.getClassLoader());
		DefaultBootstrapWriterContext writerContext = new DefaultBootstrapWriterContext(
				"org.springframework.aot", "ContextBootstrapInitializer");
		processor.process(context, writerContext);
		Path srcDirectory = FileSystems.getDefault().getPath(".").resolve("target/generated/main");
		for (JavaFile javaFile : writerContext.toJavaFiles()) {
			javaFile.writeTo(srcDirectory);
		}
	}

	public void generateTestContexts() throws IOException {
		TestContextAotProcessor processor = new TestContextAotProcessor(getClass().getClassLoader());
		DefaultBootstrapWriterContext writerContext = new DefaultBootstrapWriterContext(
				"org.springframework.aot", "ContextBootstrapInitializer");
		List<Class<?>> testClasses = List.of(DemoNativeApplicationTests.class, SimpleSpringTest.class,
				DemoNativeAnotherApplicationTests.class, DemoControllerTests.class);
		processor.generateTestContexts(testClasses, writerContext);
		Path srcDirectory = FileSystems.getDefault().getPath(".").resolve("target/generated/test");
		for (JavaFile javaFile : writerContext.toJavaFiles()) {
			javaFile.writeTo(srcDirectory);
		}
	}

	private GenericApplicationContext prepareContext() {
		GenericApplicationContext context = new AnnotationConfigServletWebApplicationContext();
		MockEnvironment environment = new MockEnvironment();
		environment.setProperty("management.server.port", "8081");
		context.setEnvironment(environment);
		context.registerBean(DemoNativeApplication.class);
		return context;
	}

}
