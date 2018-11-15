package com.stubhub.delivery.queue.test;

import com.stubhub.delivery.queue.DeliveryPolicy;
import com.stubhub.delivery.queue.impl.DefaultDeliveryPolicy;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pavel Savinov
 */
public class DefaultDeliveryPolicyTest {

	@Test
	public void testDefaultPolicyTimeouts() {
		DeliveryPolicy policy = new DefaultDeliveryPolicy();

		Assert.assertEquals(
			policy.getTryTimeout(1), DefaultDeliveryPolicy.FIRST_TRY_TIMEOUT);
		Assert.assertEquals(
			policy.getTryTimeout(2), DefaultDeliveryPolicy.SECOND_TRY_TIMEOUT);
		Assert.assertEquals(
			policy.getTryTimeout(3), DefaultDeliveryPolicy.THIRD_TRY_TIMEOUT);
	}

	@Test
	public void testDefaultPolicyTries() {
		DeliveryPolicy policy = new DefaultDeliveryPolicy();

		Assert.assertEquals(policy.getMaxTries(), 3);
	}

}
