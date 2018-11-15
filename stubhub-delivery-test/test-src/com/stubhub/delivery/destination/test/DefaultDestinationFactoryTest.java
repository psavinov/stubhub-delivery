package com.stubhub.delivery.destination.test;

import com.stubhub.delivery.exception.NoSuchDestinationTypeException;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.DestinationFactory;
import com.stubhub.delivery.model.impl.destination.DefaultDestinationFactory;
import com.stubhub.delivery.model.impl.destination.RandomlyBusyEmployee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Pavel Savinov
 */
public class DefaultDestinationFactoryTest {

	private DestinationFactory _destinationFactory;

	@Before
	public void setUp() {
		_destinationFactory = new DefaultDestinationFactory();
	}

	@Test
	public void testGetDefaultDestination() throws Exception {
		Destination destination = _destinationFactory.getDestination(
			"default", "test");

		Assert.assertNotNull(destination);

		Assert.assertTrue(destination instanceof RandomlyBusyEmployee);
	}

	@Test(expected = NoSuchDestinationTypeException.class)
	public void testGetInvalidDestinatio() throws Exception {
		_destinationFactory.getDestination
			(UUID.randomUUID().toString(), "test");
	}
}
