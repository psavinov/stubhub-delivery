package com.stubhub.delivery.log;

/**
 * Simplified system log, supports four levels of messages as set in the
 * task description.
 *
 * @author Pavel Savinov
 */
public interface Log {

	/**
	 * Sends CRITICAL level message.
	 *
	 * @param message Message to send.
	 */
	public void critical(String message);

	/**
	 * Sends ERROR level message.
	 *
	 * @param message Message to send.
	 */
	public void error(String message);

	/**
	 * Sends INFO level message.
	 *
	 * @param message Message to send.
	 */
	public void info(String message);

	/**
	 * Sends WARN level message.
	 *
	 * @param message Message to send.
	 */
	public void warn(String message);

}
