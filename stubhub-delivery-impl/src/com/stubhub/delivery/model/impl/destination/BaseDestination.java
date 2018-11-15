package com.stubhub.delivery.model.impl.destination;

import com.stubhub.delivery.model.Destination;

/**
 * Abstract destination with the common name attribute.
 * Name cannot be empty or null.
 *
 * @author Pavel Savinov
 */
public abstract class BaseDestination implements Destination {

	private String _name;

	/**
	 * @param name Destination name
	 * @throws IllegalArgumentException When name is null or empty.
	 */
	public BaseDestination(String name) {
		if ((name == null) || name.equals("")) {
			throw new IllegalArgumentException(
				"Destination name must not be empty");
		}

		_name = name;
	}

	@Override
	public String getName() {
		return _name;
	}
}
