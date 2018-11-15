package com.stubhub.delivery.queue.test;

import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.impl.parcel.RegularParcel;
import com.stubhub.delivery.queue.DeliveryQueue;
import com.stubhub.delivery.queue.impl.DefaultDeliveryQueueComparator;
import com.stubhub.delivery.queue.impl.DeliveryOrder;
import com.stubhub.delivery.util.DestinationFactoryProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

/**
 * @author Pavel Savinov
 */
public class DefaultDeliveryQueueComparatorTest {

	private Destination _destination;

	@Before
	public void setUp() throws Exception {
		_destination = DestinationFactoryProvider.getInstance().getDestination(
			"default", UUID.randomUUID().toString());
	}

	@Test
	public void testDeliveryOrderComparator() {
		DeliveryOrder order1 = new DeliveryOrder(
			new RegularParcel(_destination, "1"));
		DeliveryOrder order2 = new DeliveryOrder(
			new RegularParcel(_destination, "1"));

		Queue<DeliveryOrder> queue = new PriorityQueue<DeliveryOrder>(
			DeliveryQueue.MAX_QUEUE_SIZE,
			new DefaultDeliveryQueueComparator());

		queue.offer(order1);
		queue.offer(order2);

		Assert.assertEquals(order1, queue.peek());

		queue.clear();

		queue.offer(order1);

		order2.increaseTryNumber();

		queue.offer(order2);

		Assert.assertEquals(order2, queue.peek());
	}

}
