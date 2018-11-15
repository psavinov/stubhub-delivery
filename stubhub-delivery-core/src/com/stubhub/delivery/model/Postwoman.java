package com.stubhub.delivery.model;

/**
 * Represents a postwoman.
 *
 * @author Pavel Savinov
 */
public interface Postwoman {

	/**
	 * Delivers the parcel.
	 *
	 * @param parcel Parcel to deliver
	 * @return True if parcel was delivered successfully, False otherwise.
	 */
	public boolean deliver(Parcel parcel);

	/**
	 * Is postwoman available right now?
	 *
	 * @return True if postwoman is available, False otherwise.
	 */
	public boolean isAvailable();

}
