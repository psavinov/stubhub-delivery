package com.stubhub.delivery.model.impl.parcel;

import com.stubhub.delivery.model.Destination;

/**
 * Regular {@link com.stubhub.delivery.model.Parcel} implementation with
 * priority 0 as set in the task description.
 *
 * @author Pavel Savinov
 */
public class RegularParcel extends BaseParcel {

	public RegularParcel(Destination destination, String code) {
		super(destination, code);
	}

	@Override
	public int getPriority() {
		return 0;
	}

}

