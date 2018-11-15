package com.stubhub.delivery.model.impl.parcel;

import com.stubhub.delivery.exception.NoSuchParcelTypeException;
import com.stubhub.delivery.inject.annotation.Injectable;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.model.ParcelFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Default {@link ParcelFactory} implementation, provides a {@link Parcel}
 * instance based on the type given.
 * <p>
 * Types registry is represented as a Map with type name and implementation
 * class. New object is being instantiated using reflection.
 *
 * @author Pavel Savinov
 */
@Injectable(type = ParcelFactory.class)
public final class DefaultParcelFactory implements ParcelFactory {

	private Map<String, Class<? extends Parcel>> _registry;

	public DefaultParcelFactory() {
		_registry = new HashMap<>();

		// Register known destination types

		_registry.put("0", RegularParcel.class);
		_registry.put("1", PremiumParcel.class);
	}

	@Override
	public Parcel getParcel(String type, Destination destination, String code)
		throws NoSuchParcelTypeException {

		Class<? extends Parcel> parcelClass = _registry.get(type);

		if (parcelClass == null) {
			throw new NoSuchParcelTypeException();
		}

		Parcel parcel = null;

		// Instantiating destination using a class from registry

		try {
			Constructor<? extends Parcel> constructor =
				parcelClass.getConstructor(Destination.class, String.class);

			parcel = constructor.newInstance(destination, code);
		}
		catch (Exception e) {
			throw new NoSuchParcelTypeException(e);
		}

		return parcel;
	}
}
