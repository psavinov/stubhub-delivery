package com.stubhub.delivery.model.impl.postwoman;

import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.model.Postwoman;

import java.util.concurrent.TimeUnit;

/**
 * Default {@link Postwoman} implementation, implemented as enum to provide a
 * thread-safe, singleton-like behaviour.
 * <p>
 * This implementation is able to deliver only non-null parcels, otherwise
 * {@link IllegalArgumentException} is thrown.
 *
 * @author Pavel Savinov
 */
public enum DefaultPostwoman implements Postwoman {
	POSTWOMAN;

	// Ratio calculation interval, by default 1 second as defined in the
	// task description.

	public static final long CHECK_INTERVAL = 1;

	// Acceptable fail ratio.

	public static final double FAIL_RATIO = 0.85;

	// Ratio timestamp since we need to calculate the ratio every second.

	private long _lastTimestamp = 0;

	// Success and total processed counters to calculate the ratio.

	private int _successCount = 0;
	private int _totalCount = 0;

	@Override
	public boolean deliver(Parcel parcel) {
		if (parcel == null) {
			throw new IllegalArgumentException("Parcel must not be null");
		}

		Destination destination = parcel.getDestination();

		_totalCount++;

		// Update last timestamp

		_updateTimestamp();

		// If destination is absent it is impossible to deliver the parcel

		if (destination.isAbsent()) {
			return false;
		}

		_successCount++;

		return true;
	}

	@Override
	public boolean isAvailable() {
		double ratio = 1;

		if (_totalCount > 0) {
			ratio = (double) _successCount / (double) _totalCount;
		}

		// Postwoman becomes unavailable when the ratio is lower than the
		// acceptable fail ratio defined in the task description.

		if (ratio < FAIL_RATIO) {
			_successCount = 0;
			_totalCount = 0;

			return false;
		}

		return true;
	}

	private void _updateTimestamp() {
		if (_lastTimestamp == 0) {
			_lastTimestamp = System.currentTimeMillis();
		}

		long difference = System.currentTimeMillis() - _lastTimestamp;

		if (difference > TimeUnit.SECONDS.toMillis(CHECK_INTERVAL)) {
			_totalCount = 0;
			_successCount = 0;

			_lastTimestamp = System.currentTimeMillis();
		}
	}
}
