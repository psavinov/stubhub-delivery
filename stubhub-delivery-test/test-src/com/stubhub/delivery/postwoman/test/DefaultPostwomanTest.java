package com.stubhub.delivery.postwoman.test;

import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.model.Postwoman;
import com.stubhub.delivery.model.impl.postwoman.DefaultPostwoman;
import com.stubhub.delivery.util.DeliveryTestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Pavel Savinov
 */
public class DefaultPostwomanTest {

	private Parcel _failedParcel;
	private Parcel _successfulParcel;
	private Postwoman _postwoman;

	@Before
	public void setUp() throws Exception {
		_postwoman = DefaultPostwoman.POSTWOMAN;

		_successfulParcel = DeliveryTestUtil.getSuccessfulParcel();
		_failedParcel = DeliveryTestUtil.getFailedParcel();
	}

	@Test
	public void testPostwomanSuccessfulDelivery() {
		Assert.assertTrue(_postwoman.isAvailable());

		Assert.assertTrue(_postwoman.deliver(_successfulParcel));
	}

	@Test
	public void testPostwomanFailedDelivery() {
		Assert.assertTrue(_postwoman.isAvailable());

		Assert.assertFalse(_postwoman.deliver(_failedParcel));
	}

	@Test
	public void testPostwomanUnavailable() throws Exception {
		Assert.assertTrue(_postwoman.deliver(_successfulParcel));
		Assert.assertTrue(_postwoman.deliver(_successfulParcel));
		Assert.assertTrue(_postwoman.deliver(_successfulParcel));
		Assert.assertTrue(_postwoman.deliver(_successfulParcel));
		Assert.assertTrue(_postwoman.deliver(_successfulParcel));
		Assert.assertTrue(_postwoman.deliver(_successfulParcel));

		Assert.assertTrue(_postwoman.isAvailable());

		Assert.assertFalse(_postwoman.deliver(_failedParcel));
		Assert.assertFalse(_postwoman.deliver(_failedParcel));
		Assert.assertFalse(_postwoman.deliver(_failedParcel));

		Assert.assertFalse(_postwoman.isAvailable());

		Thread.sleep(1000);

		Assert.assertTrue(_postwoman.isAvailable());
	}
}
