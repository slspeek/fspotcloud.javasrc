package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.testharness.MainBuilder;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class ActionUIRegistryTest {

	@Inject
	private ActionUIRegistry registry;

	@Test
	public void testGetAction() throws Exception {
		registry.putAction(MainBuilder.LOGIN_DEF);
		assertEquals(MainBuilder.LOGIN_DEF,
				registry.getAction(MainBuilder.LOGIN_DEF.getId()));
	}
}
