package com.stubhub.delivery.log;

/**
 * Simplified log factory.
 *
 * @author Pavel Savinov
 */
public interface LogFactory {

	/**
	 * Returns available simplified logger implementation.
	 *
	 * @param name Logger name.
	 * @return Logger instance.
	 */
	public Log getLog(String name);

}
