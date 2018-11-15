package com.stubhub.delivery.log.impl;

import java.util.logging.Level;

/**
 * Extended {@link Level} representation, to support log levels as set in the
 * task description.
 *
 * @author Pavel Savinov
 */
public class LogLevelEx extends Level {

	public static final Level CRITICAL = new LogLevelEx("CRITICAL", 1100);
	public static final Level ERROR = new LogLevelEx("ERROR", 1000);
	public static final Level WARN = new LogLevelEx("WARN", 900);

	protected LogLevelEx(String name, int value) {
		super(name, value);
	}

}
