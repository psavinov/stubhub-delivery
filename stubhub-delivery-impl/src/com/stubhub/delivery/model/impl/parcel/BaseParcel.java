package com.stubhub.delivery.model.impl.parcel;

import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.Parcel;

/**
 * Abstract {@link Parcel} implementation with common attributes - destination,
 * code. Destination and code cannot be empty or null.
 *
 * @author Pavel Savinov
 */
public abstract class BaseParcel implements Parcel {

	protected Destination _destination;
	protected String _code;

	public BaseParcel(Destination destination, String code) {
		if (destination == null) {
			throw new IllegalArgumentException("Destination must not be null");
		}

		if ((code == null) || code.equals("")) {
			throw new IllegalArgumentException(("Code must not be empty"));
		}

		_destination = destination;
		_code = code;
	}

	@Override
	public Destination getDestination() {
		return _destination;
	}

	@Override
	public String getCode() {
		return _code;
	}
}
