package com.stubhub.delivery.log.impl;

import com.stubhub.delivery.inject.annotation.Injectable;
import com.stubhub.delivery.log.Log;
import com.stubhub.delivery.log.LogFactory;

/**
 * Default {@link LogFactory} implementation which basically provides a
 * {@link DefaultLog} instance.
 *
 * @author Pavel Savinov
 */
@Injectable(type = LogFactory.class)
public final class DefaultLogFactory implements LogFactory {

	@Override
	public Log getLog(String name) {
		return new DefaultLog(name);
	}

}
