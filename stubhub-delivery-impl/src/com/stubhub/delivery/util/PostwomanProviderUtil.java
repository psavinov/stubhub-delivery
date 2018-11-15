package com.stubhub.delivery.util;

import com.stubhub.delivery.model.Postwoman;
import com.stubhub.delivery.model.impl.postwoman.DefaultPostwoman;

/**
 * Utility class to get appropriate postwoman implementation.
 *
 * @author Pavel Savinov
 */
public class PostwomanProviderUtil {

	/**
	 * For now, returns default implementation of the {@link Postwoman}.
	 *
	 * @return Default {@link Postwoman} implementation
	 */
	public static Postwoman getPostwoman() {
		return DefaultPostwoman.POSTWOMAN;
	}

}
