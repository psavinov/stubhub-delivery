package com.stubhub.delivery.queue.test;

import com.stubhub.delivery.inject.injector.Injector;
import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.model.Postwoman;
import com.stubhub.delivery.model.impl.postwoman.DefaultPostwoman;
import com.stubhub.delivery.queue.DeliveryQueue;
import com.stubhub.delivery.util.DeliveryQueueProvider;
import com.stubhub.delivery.util.DeliveryTestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Pavel Savinov
 */
public class DefaultDeliveryQueueTest {

	private DeliveryQueue _queue;
	private Parcel _failedParcel;
	private Postwoman _postwoman;

	@Before
	public void setUp() throws Exception {
		_postwoman = DefaultPostwoman.POSTWOMAN;

		_failedParcel = DeliveryTestUtil.getFailedParcel();
	}

	@Test
	public void testNormalFunction() throws Exception {
		Injector.getInstance().invalidate(DeliveryQueueProvider.getInstance());

		DeliveryQueue queue =
			DeliveryQueueProvider.getInstance().createDeliveryQueue(
				getClass().getResourceAsStream(
					"/com/stubhub/delivery/queue/test/queue.txt"));

		queue.deliverParcels(_postwoman);

		boolean allProcessed = false;

		while (!allProcessed) {
			allProcessed = queue.allParcelsProcessed();
		}

		queue.stopArrival();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectInput() throws Exception {
		Injector.getInstance().invalidate(DeliveryQueueProvider.getInstance());

		DeliveryQueue queue =
			DeliveryQueueProvider.getInstance().createDeliveryQueue(
				getClass().getResourceAsStream(
					"/com/stubhub/delivery/queue/test/bad_queue.txt"));

		queue.deliverParcels(_postwoman);
	}

	@Test
	public void testParcelArchive() throws Exception {
		Injector.getInstance().invalidate(DeliveryQueueProvider.getInstance());

		DeliveryQueue queue =
			DeliveryQueueProvider.getInstance().createDeliveryQueue(
				getClass().getResourceAsStream(
					"/com/stubhub/delivery/queue/test/good_queue.txt"));

		queue.deliverParcels(_postwoman);

		Assert.assertTrue(queue.getArchive().isEmpty());

		queue.addParcel(_failedParcel);

		boolean allProcessed = false;

		while (!allProcessed) {
			allProcessed = queue.allParcelsProcessed();
		}

		queue.stopArrival();

		Assert.assertFalse(queue.getArchive().isEmpty());

		Assert.assertEquals(
			_failedParcel.getCode(), queue.getArchive().get(0));
	}
}
