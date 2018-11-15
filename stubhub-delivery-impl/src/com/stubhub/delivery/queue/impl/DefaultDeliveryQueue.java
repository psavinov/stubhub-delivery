package com.stubhub.delivery.queue.impl;

import com.stubhub.delivery.inject.annotation.Injectable;
import com.stubhub.delivery.log.Log;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.model.Postwoman;
import com.stubhub.delivery.queue.DeliveryPolicy;
import com.stubhub.delivery.queue.DeliveryQueue;
import com.stubhub.delivery.util.LogFactoryProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Default delivery queue using delivery rules and policy as set in the task
 * description.
 *
 * @author Pavel Savinov
 */
@Injectable(type = DeliveryQueue.class)
public final class DefaultDeliveryQueue implements DeliveryQueue {

	private static final Log _log = LogFactoryProvider.getInstance().getLog(
		DefaultDeliveryQueue.class.getSimpleName());
	private boolean _arrivalOpen;
	private Map<Integer, Queue<DeliveryOrder>> _queueMap;
	private List<String> _parcelsArchive;

	public DefaultDeliveryQueue() {

		// By default arrival flow is enabled.

		_arrivalOpen = true;

		// Archived ("dead") parcels codes will be here.

		_parcelsArchive = new ArrayList<String>();

		// Concurrent map since we are going manipulate map's entries inside
		// the map's iteration. Using priotity comparator.

		_queueMap = new ConcurrentSkipListMap<>(
			new Comparator<Integer>() {

				@Override
				public int compare(Integer o1, Integer o2) {
					return o2.compareTo(o1);
				}
			});
	}

	@Override
	public void addParcel(Parcel parcel) {

		// Get or create new priority queue for delivery orders using or
		// DefaultDeliveryQueueComparator to compare number of tries.

		Queue<DeliveryOrder> queue = _queueMap.getOrDefault(
			parcel.getPriority(), new PriorityQueue<DeliveryOrder>(
				DeliveryQueue.MAX_QUEUE_SIZE,
				new DefaultDeliveryQueueComparator()));

		queue.offer(new DeliveryOrder(parcel));

		_queueMap.put(parcel.getPriority(), queue);
	}

	@Override
	public boolean allParcelsProcessed() {

		// We assume that all parcels were processed when all the queues are
		// empty.

		return _queueMap.isEmpty();
	}

	@Override
	public void deliverParcels(Postwoman postwoman) {
		Thread thread = new Thread(
			() -> {

				// Process the queues while arrival is open

				while (_arrivalOpen) {
					for (Integer priority : _queueMap.keySet()) {
						Queue<DeliveryOrder> queue = _queueMap.get(priority);

						if (queue.isEmpty()) {

							// Remove the queue from the map when it is empty
							// and there are no more parcels of this priority.

							_queueMap.remove(priority);

							continue;
						}

						_processQueue(queue, postwoman);
					}
				}
			}
		);

		thread.start();
	}

	@Override
	public List<String> getArchive() {
		return _parcelsArchive;
	}

	@Override
	public DeliveryPolicy getPolicy() {

		// We use default policy for this delivery queue.

		return new DefaultDeliveryPolicy();
	}

	@Override
	public void moveToArchive(Parcel parcel) {
		_parcelsArchive.add(parcel.getCode());
	}

	@Override
	public void stopArrival() {
		_arrivalOpen = false;
	}

	private void _processQueue(
		Queue<DeliveryOrder> deliveryOrders, Postwoman postwoman) {

		DeliveryPolicy policy = getPolicy();

		// Process parcels while the orders queue is not empty.

		while (!deliveryOrders.isEmpty()) {
			DeliveryOrder deliveryOrder = deliveryOrders.poll();

			Parcel parcel = deliveryOrder.getParcel();
			Destination destination = parcel.getDestination();

			// Maximum number of tries exceeded, archive the parcel.

			if (deliveryOrder.getTryNumber() > policy.getMaxTries()) {
				StringBuffer stringBuffer = new StringBuffer();

				stringBuffer.append("Parcel ")
					.append(parcel.getCode())
					.append(" won't be delivered to ")
					.append(destination.getName())
					.append(". Retries: ")
					.append(policy.getMaxTries());

				_log.info(stringBuffer.toString());

				moveToArchive(parcel);

				continue;
			}

			// Postwoman is not available, sleep for 1 second.

			if (!postwoman.isAvailable()) {
				_log.critical("Postwoman not available");

				_sleep(1000);

				deliveryOrders.offer(deliveryOrder);

				continue;
			}

			if (postwoman.deliver(parcel)) {

				// Parcel successfully delivered.

				StringBuffer stringBuffer = new StringBuffer();

				stringBuffer.append("Parcel ")
					.append(parcel.getCode())
					.append(" successfully delivered to ")
					.append(destination.getName())
					.append(". Retries: ")
					.append(deliveryOrder.getTryNumber());

				_log.info(stringBuffer.toString());
			}
			else {

				// Delivery failed, increase the try number and put it again
				// into the queue, it will have higher priority than before
				// because of the comparator used.

				StringBuffer stringBuffer = new StringBuffer();

				stringBuffer.append("Parcel ")
					.append(parcel.getCode())
					.append(" failed to be delivered to ")
					.append(destination.getName())
					.append(". Retries: ")
					.append(deliveryOrder.getTryNumber());

				_log.warn(stringBuffer.toString());

				deliveryOrder.increaseTryNumber();

				_sleep(policy.getTryTimeout(deliveryOrder.getTryNumber()));

				deliveryOrders.offer(deliveryOrder);
			}
		}
	}

	// Sleep helper to avoid try/catch everywhere.

	private void _sleep(long time) {
		try {
			Thread.sleep(time);
		}
		catch (InterruptedException e) {
			_log.error("System exception");

			e.printStackTrace();
		}
	}

}
