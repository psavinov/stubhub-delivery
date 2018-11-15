package com.stubhub.delivery.queue.impl;

import com.stubhub.delivery.model.Parcel;

/**
 * Helper class to simplify processing of {@link DefaultDeliveryQueue}
 *
 * @author Pavel Savinov
 */
public class DeliveryOrder {

	private int _tryNumber;
	private Parcel _parcel;

	public DeliveryOrder(Parcel parcel) {
		if (parcel == null) {
			throw new IllegalArgumentException("Parcel must not be null");
		}

		_parcel = parcel;
		_tryNumber = 0;
	}

	public int getTryNumber() {
		return _tryNumber;
	}

	public void increaseTryNumber() {
		_tryNumber++;
	}

	public Parcel getParcel() {
		return _parcel;
	}

}
