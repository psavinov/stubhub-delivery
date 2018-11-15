package com.stubhub.delivery.queue;

/**
 * Delivery policy - number of tries, timeout before tries.
 *
 * @author Pavel Savinov
 */
public interface DeliveryPolicy {

	/**
	 * Maximum number of tries.
	 *
	 * @return Maximum number of tries.
	 */
	public int getMaxTries();

	/**
	 * Timeout for the n-th try, in milliseconds.
	 *
	 * @param tryNumber Number of try
	 * @return Milliseconds of timeout for the try.
	 */
	public long getTryTimeout(int tryNumber);

}
