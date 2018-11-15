package com.stubhub.delivery.util;

import com.stubhub.delivery.exception.NoSuchParcelTypeException;
import com.stubhub.delivery.inject.annotation.Inject;
import com.stubhub.delivery.inject.injector.Injector;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.model.ParcelFactory;

/**
 * Utility class to get parcel using available parcel factory implementation.
 *
 * @author Pavel Savinov
 */
public class ParcelFactoryProvider {

	private static ParcelFactoryProvider
		ourInstance = new ParcelFactoryProvider();
	@Inject(type = ParcelFactory.class)
	private ParcelFactory _parcelFactory;

	private ParcelFactoryProvider() {
		Injector.getInstance().inject(this);
	}

	public static ParcelFactoryProvider getInstance() {
		return ourInstance;
	}

	public Parcel getParcel(
		String type, Destination destination, String name)
		throws NoSuchParcelTypeException {

		return _parcelFactory.getParcel(type, destination, name);
	}
}
