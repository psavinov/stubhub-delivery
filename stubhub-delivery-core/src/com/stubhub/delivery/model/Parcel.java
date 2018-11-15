package com.stubhub.delivery.model;

/**
 * Represents the parcel to deliver.
 *
 * @author Pavel Savinov
 */
public interface Parcel {

	/**
	 * Destination to deliver the parcel to.
	 *
	 * @return Parcel's destination
	 */
	public Destination getDestination();

	/**
	 * Parcel's code.
	 *
	 * @return Parcel's code
	 */
	public String getCode();

	/**
	 * Parcel's priority.
	 *
	 * @return Parcel's priority
	 */
	public int getPriority();

}
