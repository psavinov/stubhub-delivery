package com.stubhub.delivery.model;

/**
 * Parcel destination, as set in the task description, each destination can
 * be absent(employee not at the desk) and have a unique name.
 *
 * @author Pavel Savinov
 */
public interface Destination {

	/**
	 * Unique destination name.
	 *
	 * @return Unique destination name
	 */
	public String getName();

	/**
	 * Is destination absent right now?
	 *
	 * @return True if the destination is absent, False otherwise.
	 */
	public boolean isAbsent();

}
