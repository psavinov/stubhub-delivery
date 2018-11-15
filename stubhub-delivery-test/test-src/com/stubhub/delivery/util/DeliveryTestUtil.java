package com.stubhub.delivery.util;

import com.stubhub.delivery.model.Destination;
import com.stubhub.delivery.model.Parcel;

import java.util.UUID;

/**
 * @author Pavel Savinov
 */
public class DeliveryTestUtil {

	public static Parcel getSuccessfulParcel() throws Exception {
		Destination destination = new Destination() {

			@Override
			public String getName() {
				return UUID.randomUUID().toString();
			}

			@Override
			public boolean isAbsent() {
				return false;
			}

		};

		return ParcelFactoryProvider.getInstance().getParcel(
			"0", destination, String.valueOf(System.currentTimeMillis()));
	}

	public static Parcel getFailedParcel() throws Exception {
		Destination destination = new Destination() {

			@Override
			public String getName() {
				return UUID.randomUUID().toString();
			}

			@Override
			public boolean isAbsent() {
				return true;
			}

		};

		return ParcelFactoryProvider.getInstance().getParcel(
			"0", destination, String.valueOf(System.currentTimeMillis()));
	}

}
