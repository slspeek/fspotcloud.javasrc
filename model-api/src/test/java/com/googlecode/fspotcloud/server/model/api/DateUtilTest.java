package com.googlecode.fspotcloud.server.model.api;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.*;

public class DateUtilTest {
	@Test
	public void testCloneDate() throws Exception {
		Date date = new Date(0);
		Date date2 = DateUtil.cloneDate(date);
		assertNotSame(date, date2);
		assertEquals(date, date2);
	}

	@Test
	public void testNullCase() throws Exception {
		Date date = DateUtil.cloneDate(null);
		assertNull(date);

	}
}
