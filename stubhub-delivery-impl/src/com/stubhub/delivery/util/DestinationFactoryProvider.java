package com.stubhub.delivery.util;

import com.stubhub.delivery.exception.NoSuchDestinationTypeException;
import com.stubhub.delivery.inject.annotation.Inject;
import com.stubhub.delivery.inject.injector.Injector;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.DestinationFactory;

/**
 * Utility class to get destination using available destination factory
 * implementation.
 *
 * @author Pavel Savinov
 */
public class DestinationFactoryProvider {

	private static DestinationFactoryProvider
		ourInstance = new DestinationFactoryProvider();
	@Inject(type = DestinationFactory.class)
	private DestinationFactory _destinationFactory;

	private DestinationFactoryProvider() {
		Injector.getInstance().inject(this);
	}

	public static DestinationFactoryProvider getInstance() {
		return ourInstance;
	}

	public Destination getDestination(String type, String name)
		throws NoSuchDestinationTypeException {

		return _destinationFactory.getDestination(type, name);
	}

}
