package com.stubhub.delivery.model.impl.destination;

import com.stubhub.delivery.exception.NoSuchDestinationTypeException;
import com.stubhub.delivery.inject.annotation.Injectable;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.DestinationFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Default {@link DestinationFactory} implementation, provides
 * {@link Destination} instances based on the type given.
 * <p>
 * Types registry is represented as a Map with type name and implementation
 * class. New object is being instantiated using reflection.
 *
 * @author Pavel Savinov
 */
@Injectable(type = DestinationFactory.class)
public final class DefaultDestinationFactory implements DestinationFactory {

	private Map<String, Class<? extends Destination>> _registry;

	public DefaultDestinationFactory() {
		_registry = new HashMap<>();

		// Register known destination types

		_registry.put("default", RandomlyBusyEmployee.class);
	}

	@Override
	public Destination getDestination(String type, String name)
		throws NoSuchDestinationTypeException {

		Class<? extends Destination> destinationClass = _registry.get(
			type);

		if (destinationClass == null) {
			throw new NoSuchDestinationTypeException();
		}

		Destination destination = null;

		// Instantiating destination using a class from registry

		try {
			Constructor<? extends Destination> constructor =
				destinationClass.getConstructor(String.class);

			destination = constructor.newInstance(name);
		}
		catch (Exception e) {
			throw new NoSuchDestinationTypeException(e);
		}

		return destination;
	}
}
