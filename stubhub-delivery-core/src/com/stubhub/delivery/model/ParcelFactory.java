package com.stubhub.delivery.model;

import com.stubhub.delivery.exception.NoSuchParcelTypeException;

/**
 * Provides concrete {@link Parcel} implementation based on the type given.
 *
 * @author Pavel Savinov
 */
public interface ParcelFactory {

	/**
	 * Parcel corresponding to the type, destination and code provided.
	 *
	 * @param type        Parcel type
	 * @param destination Parcel destination
	 * @param code        Parcel code
	 * @return Destionation of provided type, destination and code
	 * @throws NoSuchParcelTypeException When there is no such parcel type
	 *                                   available.
	 * @throws IllegalArgumentException  When destination or code is null or
	 *                                   emptry.
	 */
	public Parcel getParcel(String type, Destination destination, String code)
		throws NoSuchParcelTypeException;

}
