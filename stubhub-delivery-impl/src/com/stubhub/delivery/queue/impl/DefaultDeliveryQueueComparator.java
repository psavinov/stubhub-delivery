package com.stubhub.delivery.queue.impl;

import java.util.Comparator;

/**
 * Comparator to compare delivery orders of the same priority.
 *
 * @author Pavel Savinov
 */
public class DefaultDeliveryQueueComparator
	implements Comparator<DeliveryOrder> {

	@Override
	public int compare(DeliveryOrder o1, DeliveryOrder o2) {
		if ((o1 == null) && (o2 != null)) {
			return -1;
		}
		else if ((o2 == null) && (o1 != null)) {
			return 1;
		}
		else if ((o1 == null) && (o2 == null)) {
			return 0;
		}

		return o2.getTryNumber() - o1.getTryNumber();
	}

}
