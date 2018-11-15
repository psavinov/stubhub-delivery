package com.stubhub.delivery.simulation;

import com.stubhub.delivery.exception.NoSuchParcelTypeException;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.queue.DeliveryQueue;
import com.stubhub.delivery.util.DestinationFactoryProvider;
import com.stubhub.delivery.util.ParcelFactoryProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Runnable to simulate arrival of the new parcels.
 *
 * @author Pavel Savinov
 */
public class SimulationWorker implements Runnable {

	private long _duration;
	private DeliveryQueue _queue;
	private long _startTimestamp;
	private List<Destination> _destinations;

	public SimulationWorker(
		DeliveryQueue queue, long duration, int destinationsCount)
		throws Exception {

		_queue = queue;
		_duration = duration;

		_destinations = new ArrayList<>();

		// Initialize given number of default destinations

		for (int k = 0; k < destinationsCount; k++) {
			_destinations.add(
				DestinationFactoryProvider.getInstance().getDestination(
					"default", UUID.randomUUID().toString()));
		}
	}

	@Override
	public void run() {
		_startTimestamp = System.currentTimeMillis();

		Random random = new Random();

		while ((System.currentTimeMillis() - _startTimestamp) < _duration) {
			Destination destination = _destinations.get(
				random.nextInt(_destinations.size()));

			Parcel parcel = null;

			// Generate random number of parcels and add them to the queue

			try {
				parcel = ParcelFactoryProvider.getInstance().getParcel(
					String.valueOf(random.nextInt(2)), destination,
					String.valueOf(System.currentTimeMillis()));
			}
			catch (NoSuchParcelTypeException e) {
				e.printStackTrace();

				break;
			}

			_queue.addParcel(parcel);

			// Sleep random period (up to 1sec)

			try {
				Thread.sleep(random.nextInt(1000));
			}
			catch (InterruptedException e) {
				e.printStackTrace();

				break;
			}
		}
	}
}
