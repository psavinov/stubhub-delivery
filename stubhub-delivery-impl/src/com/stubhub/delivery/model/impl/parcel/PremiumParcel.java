package com.stubhub.delivery.model.impl.parcel;

import com.stubhub.delivery.model.Destination;

/**
 * Premium {@link com.stubhub.delivery.model.Parcel} implementation, with
 * priority 1 as set in the task description.
 *
 * @author Pavel Savinov
 */
public class PremiumParcel extends BaseParcel {

	public PremiumParcel(Destination destination, String code) {
		super(destination, code);
	}

	@Override
	public int getPriority() {
		return 1;
	}

}

