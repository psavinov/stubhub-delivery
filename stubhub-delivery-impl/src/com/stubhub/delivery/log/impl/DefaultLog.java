package com.stubhub.delivery.log.impl;

import com.stubhub.delivery.log.Log;

import java.util.logging.Logger;

/**
 * Default {@link Log} implementation based on Java Logging API.
 * Logger name cannot be null or empty.
 *
 * @author Pavel Savinov
 */
public class DefaultLog implements Log {

	private final Logger _logger;

	/**
	 * @param name Logger name
	 * @throws IllegalArgumentException When name is null or empty.
	 */
	public DefaultLog(String name) {
		if ((name == null) || name.equals("")) {
			throw new IllegalArgumentException("Logger name must not be empty");
		}

		_logger = Logger.getLogger(name);
	}

	@Override
	public void critical(String message) {
		_logger.log(LogLevelEx.CRITICAL, message);
	}

	@Override
	public void error(String message) {
		_logger.log(LogLevelEx.ERROR, message);
	}

	@Override
	public void info(String message) {
		_logger.log(LogLevelEx.INFO, message);
	}

	@Override
	public void warn(String message) {
		_logger.log(LogLevelEx.WARN, message);
	}
}
