package com.stubhub.delivery.queue.impl;

import com.stubhub.delivery.queue.DeliveryPolicy;

/**
 * Default delivery policy implementation, as set in the task description.
 *
 * @author Pavel Savinov
 */
public class DefaultDeliveryPolicy implements DeliveryPolicy {

	public static final long FIRST_TRY_TIMEOUT = 100;
	public static final long SECOND_TRY_TIMEOUT = 300;
	public static final long THIRD_TRY_TIMEOUT = 1000;

	@Override
	public int getMaxTries() {
		return 3;
	}

	@Override
	public long getTryTimeout(int tryNumber) {
		switch (tryNumber) {
			case 1:
				return FIRST_TRY_TIMEOUT;
			case 2:
				return SECOND_TRY_TIMEOUT;
			case 3:
				return THIRD_TRY_TIMEOUT;
			default:
				return 0;
		}
	}

}
