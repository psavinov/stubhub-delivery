package com.stubhub.delivery.util;

import com.stubhub.delivery.inject.annotation.Inject;
import com.stubhub.delivery.inject.injector.Injector;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.queue.DeliveryQueue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to instantiate default delivery queue using input stream as
 * a data source.
 *
 * @author Pavel Savinov
 */
public class DeliveryQueueProvider {

	private static DeliveryQueueProvider
		ourInstance = new DeliveryQueueProvider();
	@Inject(type = DeliveryQueue.class)
	private DeliveryQueue _deliveryQueue;

	private DeliveryQueueProvider() {
		Injector.getInstance().inject(this);
	}

	public static DeliveryQueueProvider getInstance() {
		return ourInstance;
	}

	/**
	 * Reads data from the input stream and instantiates {@link DeliveryQueue}
	 * with the parcels from the stream.
	 *
	 * @param inputStream Input stream to read the data from.
	 * @return Ready-to-use delivery queue.
	 * @throws Exception
	 */
	public DeliveryQueue createDeliveryQueue(InputStream inputStream)
		throws Exception {

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(inputStream));

		String line = null;

		Map<String, Destination> destinations = new HashMap<>();

		// Read the input stream while it has non-empty data

		while ((line = bufferedReader.readLine()) != null) {
			if (line.equalsIgnoreCase("")) {
				break;
			}

			String[] parts = line.split(",");

			// As defined, each line has to have exactly 3 parts

			if (parts.length != 3) {
				throw new IllegalArgumentException("Incorrect input: " + line);
			}

			// Instantiate default destination

			Destination destination = destinations.getOrDefault(
				parts[1], DestinationFactoryProvider.getInstance().
					getDestination("default", parts[1]));

			destinations.putIfAbsent(parts[1], destination);

			// Instantiate parcel using types registry

			Parcel parcel = ParcelFactoryProvider.getInstance().getParcel(
				parts[2], destination, parts[0]);

			_deliveryQueue.addParcel(parcel);
		}

		return _deliveryQueue;
	}
}
