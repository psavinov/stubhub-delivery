package com.stubhub.delivery.model.impl.destination;

import java.util.Random;

/**
 * Randomly busy employee {@link com.stubhub.delivery.model.Destination}
 * implementation. Absence ratio - 5%...20%.
 *
 * @author Pavel Savinov
 */
public class RandomlyBusyEmployee extends BaseDestination {

	public RandomlyBusyEmployee(String name) {
		super(name);
	}

	@Override
	public boolean isAbsent() {
		Random random = new Random();

		int chance = random.nextInt(101);

		return chance > (random.nextInt(15) + 80);
	}

}
