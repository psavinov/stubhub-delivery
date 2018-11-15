package com.stubhub.delivery.log.test;

import com.stubhub.delivery.log.Log;
import com.stubhub.delivery.log.LogFactory;
import com.stubhub.delivery.log.impl.DefaultLog;
import com.stubhub.delivery.log.impl.DefaultLogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Pavel Savinov
 */
public class DefaultLogFactoryTest {

	private LogFactory _logFactory;

	@Before
	public void setUp() {
		_logFactory = new DefaultLogFactory();
	}

	@Test
	public void testGetLog() {
		Log log = _logFactory.getLog("test");

		Assert.assertTrue(log instanceof DefaultLog);
	}

}
