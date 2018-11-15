package com.stubhub.delivery.inject.scanner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Package scanner to get all available classes. Works as helper to find all
 * possible injectable implementations.
 *
 * @author Pavel Savinov
 */
public class PackageScanner {

	private String _packageName;

	public PackageScanner(String packageName) {
		_packageName = packageName;
	}

	/**
	 * Scans current classloader for all available classes.
	 *
	 * @return List of available classes for the current classloader.
	 * @throws Exception Reflection or I/O exception.
	 */
	public List<Class<?>> scan() throws Exception {
		ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();

		ArrayList classes = new ArrayList();

		classes.addAll(_findClasses(classLoader));

		return classes;
	}

	private List<Class<?>> _findClasses(ClassLoader classLoader)
		throws Exception {

		ArrayList classes = new ArrayList();

		String path = _packageName.replace('.', '/');

		Enumeration<URL> resources = classLoader.getResources(path);

		List<File> directories = new ArrayList();

		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();

			directories.add(new File(resource.getFile()));
		}

		for (File directory : directories) {
			classes.addAll(_findClasses(directory, _packageName));
		}

		return classes;
	}

	private List<Class<?>> _findClasses(File directory, String packageName)
		throws ClassNotFoundException {

		List<Class<?>> classes = new ArrayList();

		if (!directory.exists()) {
			return classes;
		}

		File[] files = directory.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(
					_findClasses(file, packageName + "." + file.getName()));
			}
			else if (file.getName().endsWith(".class")) {
				String prefix = _findPrefix(file.getPath(), file.getName());

				classes.add(
					Class.forName(
						prefix + "." +
						file.getName().substring(
							0, file.getName().length() - 6)));
			}
		}

		return classes;
	}

	private String _findPrefix(String path, String className) {
		ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();

		String prefix = null;

		while (path.contains(File.separator) && (prefix == null)) {
			URL url = classLoader.getResource(path);

			if (url == null) {
				path = path.substring(path.indexOf(File.separator) + 1);

				continue;
			}
			else {
				prefix = path.replace(File.separator, ".").substring(
					0, path.lastIndexOf(className) - 1);
			}
		}

		return prefix;
	}

}
