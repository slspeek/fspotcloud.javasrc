package com.googlecode.fspotcloud.keyboardaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WidgetRegistryEmptyTest {

	WidgetRegistry registry = new WidgetRegistry();

	@Test
	public void testGet() throws Exception {
		assertEquals(0, registry.get("foo").size());
	}
}
