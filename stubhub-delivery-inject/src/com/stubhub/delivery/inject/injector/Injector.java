package com.stubhub.delivery.inject.injector;

import com.stubhub.delivery.inject.annotation.Inject;
import com.stubhub.delivery.inject.annotation.Injectable;
import com.stubhub.delivery.inject.scanner.PackageScanner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Injector singleton, scans all available classes in the current context
 * classloader and injects @Inject-annotated fields with @Injectable objects.
 *
 * @author Pavel Savinov
 */
public class Injector {

	private static Injector ourInstance = new Injector();
	private Map<Object, Map<Class<?>, Object>> _instances;

	private Injector() {
		_instances = new ConcurrentHashMap<>();
	}

	public static Injector getInstance() {
		return ourInstance;
	}

	/**
	 * Inject all @Inject-anotated services on the host object.
	 *
	 * @param host Object to inject fields.
	 * @throws IllegalStateException When there are no @Injectable
	 * implementations for corresponding fields.
	 */
	public void inject(Object host) {
		try {
			Class<?> hostClass = host.getClass();

			Field[] fields = hostClass.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);

				// Find all fields ready to inject

				Inject injectAnnotation = field.getAnnotation(Inject.class);

				if (injectAnnotation == null) {
					continue;
				}

				Class<?> injectType = injectAnnotation.type();

				// Find appropriate injectable object

				Object objectToInject = _findInjectable(injectType, host);

				if (objectToInject == null) {
					throw new IllegalStateException(
						"No injectable dependency found: " +
						injectType.getName());
				}

				// Set corresponding injectable implementation

				field.set(host, objectToInject);

				field.setAccessible(false);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Dependency injection error", e);
		}
	}

	/**
	 * Invalidates current injected assignements and inject fields once again.
	 *
	 * @param host Host to invalidate and re-inject.
	 */
	public void invalidate(Object host) {
		_instances.remove(host);

		inject(host);
	}

	private Object _findInjectable(Class<?> injectType, Object host)
		throws Exception {

		// Injected instances cache

		Map<Class<?>, Object> hostInjections = _instances.getOrDefault(
			host, new HashMap<>());

		Object injectableObject = hostInjections.get(injectType);

		// If there is an injectable instance for this host - use it from cache

		if (injectableObject != null) {
			return injectableObject;
		}

		// Initialize package scanner and get available classes

		PackageScanner scanner = new PackageScanner("");

		List<Class<?>> classes = scanner.scan();

		List<Class<?>> injectableClasses = new ArrayList<>();

		// Find all injectable classes for current injectable type

		for (Class<?> clazz : classes) {
			Injectable injectableAnnotation = clazz.getAnnotation(
				Injectable.class);

			if (injectableAnnotation == null) {
				continue;
			}

			if (injectableAnnotation.type().equals(injectType)) {
				injectableClasses.add(clazz);
			}
		}

		// Use ranking comparator to get injectable implementation with the
		// highest priority

		if (!injectableClasses.isEmpty()) {
			Class<?> injectableType =
				injectableClasses.get(0).getAnnotation(
					Injectable.class).type();

			Collections.sort(injectableClasses, new Comparator<Class<?>>() {

				@Override
				public int compare(Class<?> o1, Class<?> o2) {
					Injectable injectableAnnotation1 = o1.getAnnotation(
						Injectable.class);

					Injectable injectableAnnotation2 = o2.getAnnotation(
						Injectable.class);

					Integer ranking1 = injectableAnnotation1.ranking();
					Integer ranking2 = injectableAnnotation2.ranking();

					return ranking2.compareTo(ranking1);
				}

			});

			// Instantiate selected injectable object and save it in cache

			Class<?> injectableClass = injectableClasses.get(0);

			Object newInstance = injectableClass.newInstance();

			hostInjections.put(injectableType, newInstance);

			_instances.put(host, hostInjections);

			return newInstance;
		}

		return null;
	}
}
