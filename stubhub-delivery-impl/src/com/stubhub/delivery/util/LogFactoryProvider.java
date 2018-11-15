package com.stubhub.delivery.util;

import com.stubhub.delivery.inject.annotation.Inject;
import com.stubhub.delivery.inject.injector.Injector;
import com.stubhub.delivery.log.Log;
import com.stubhub.delivery.log.LogFactory;

/**
 * Utility class to get logger using available log factory implementation.
 *
 * @author Pavel Savinov
 */
public class LogFactoryProvider {

	private static LogFactoryProvider
		ourInstance = new LogFactoryProvider();
	@Inject(type = LogFactory.class)
	private LogFactory _logFactory;

	private LogFactoryProvider() {
		Injector.getInstance().inject(this);
	}

	public static LogFactoryProvider getInstance() {
		return ourInstance;
	}

	public Log getLog(String name) {
		return _logFactory.getLog(name);
	}
}
