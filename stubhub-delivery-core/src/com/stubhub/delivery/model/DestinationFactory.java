package com.stubhub.delivery.model;

import com.stubhub.delivery.exception.NoSuchDestinationTypeException;

/**
 * Provides concrete {@link Destination} implementation based on the type given.
 *
 * @author Pavel Savinov
 */
public interface DestinationFactory {

	/**
	 * Destination corresponding to the type provided.
	 *
	 * @param type Destination type
	 * @param name Destination name
	 * @return Destionation of provided type and name
	 * @throws NoSuchDestinationTypeException When there is no such destination
	 *                                        type available.
	 * @throws IllegalArgumentException       When name is null or empty.
	 */
	public Destination getDestination(String type, String name)
		throws NoSuchDestinationTypeException;

}
