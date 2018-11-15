package com.stubhub.delivery.queue;

import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.model.Postwoman;

import java.util.List;

/**
 * Parcels delivery queue.
 *
 * @author Pavel Savinov
 */
public interface DeliveryQueue {

	/**
	 * Maximum queue size allowed per one priority.
	 */
	public static final int MAX_QUEUE_SIZE = 10000;

	/**
	 * Adds parcel to the delivery queue.
	 *
	 * @param parcel Parcel to add.
	 */
	public void addParcel(Parcel parcel);

	/**
	 * Are all available parsers processed?
	 *
	 * @return True if the queue is empty now, False otherwise
	 */
	public boolean allParcelsProcessed();

	/**
	 * Delivers existing parcels using selected postwoman till the end of
	 * arrival time(flag rise).
	 *
	 * @param postwoman Postwoman to assign the delivery.
	 */
	public void deliverParcels(Postwoman postwoman);

	/**
	 * Archived parcels codes list. "Dead" shipoments go here.
	 *
	 * @return The list of archived parcels codes.
	 */
	public List<String> getArchive();

	/**
	 * Delivery queue policy.
	 *
	 * @return Delivery policy
	 */
	public DeliveryPolicy getPolicy();

	/**
	 * Moves parcel to archive
	 *
	 * @param parcel Parcel to add.
	 */
	public void moveToArchive(Parcel parcel);

	/**
	 * Stops new parcels arrival. In the real use-case means that arrival of
	 * shipments is usually time-bounded, provides the way to stop the
	 * processing.
	 */
	public void stopArrival();

}
