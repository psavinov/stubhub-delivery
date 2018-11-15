package com.stubhub.delivery.destination.test;

import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.impl.destination.RandomlyBusyEmployee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Pavel Savinov
 */
public class RandomlyBusyEmployeeTest {

	Destination _destination;

	@Before
	public void setUp() {
		_destination = new RandomlyBusyEmployee("test");
	}

	@Test
	public void testAbsenseRatio() {
		int absent = 0;

		for (int k = 0; k < 10000; k++) {
			if (_destination.isAbsent()) {
				absent++;
			}
		}

		double ratio = (double) absent / 10000.0;

		Assert.assertTrue((ratio >= 0.05) && (ratio <= 0.20));
	}
}
