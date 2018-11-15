package com.stubhub.delivery.parcel.test;

import com.stubhub.delivery.exception.NoSuchParcelTypeException;
import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.DestinationFactory;
import com.stubhub.delivery.model.Parcel;
import com.stubhub.delivery.model.ParcelFactory;
import com.stubhub.delivery.model.impl.destination.DefaultDestinationFactory;
import com.stubhub.delivery.model.impl.parcel.DefaultParcelFactory;
import com.stubhub.delivery.model.impl.parcel.PremiumParcel;
import com.stubhub.delivery.model.impl.parcel.RegularParcel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Pavel Savinov
 */
public class DefaultParcelFactoryTest {

	private DestinationFactory _destinationFactory;
	private ParcelFactory _parcelFactory;

	@Before
	public void setUp() {
		_destinationFactory = new DefaultDestinationFactory();
		_parcelFactory = new DefaultParcelFactory();
	}

	@Test
	public void testGetRegularParcel() throws Exception {
		Destination destination = _destinationFactory.getDestination(
			"default", UUID.randomUUID().toString());

		Parcel parcel = _parcelFactory.getParcel(
			"0", destination, String.valueOf(System.currentTimeMillis()));

		Assert.assertNotNull(parcel);

		Assert.assertTrue(parcel instanceof RegularParcel);
	}

	@Test
	public void testGetPremiumParcel() throws Exception {
		Destination destination = _destinationFactory.getDestination(
			"default", UUID.randomUUID().toString());

		Parcel parcel = _parcelFactory.getParcel(
			"1", destination, String.valueOf(System.currentTimeMillis()));

		Assert.assertNotNull(parcel);

		Assert.assertTrue(parcel instanceof PremiumParcel);
	}

	@Test(expected = NoSuchParcelTypeException.class)
	public void testGetInvalidParcel() throws Exception {
		Destination destination = _destinationFactory.getDestination(
			"default", UUID.randomUUID().toString());

		_parcelFactory.getParcel(
			UUID.randomUUID().toString(), destination,
			String.valueOf(System.currentTimeMillis()));
	}
}
