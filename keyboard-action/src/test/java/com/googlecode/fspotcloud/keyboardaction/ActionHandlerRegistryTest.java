package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertSame;

@RunWith(JukitoRunner.class)
public class ActionHandlerRegistryTest {

	@Inject
	private ActionHandlerRegistry registry;
	private IActionHandler dummy = new IActionHandler() {
		@Override
		public void performAction(String actionId) {
		}
	};

	@Test(expected = IllegalStateException.class)
	public void testPutAction() throws Exception {
		registry.putAction("1", dummy);
		registry.putAction("1", dummy);
	}

	@Test
	public void testGetAction() throws Exception {
		registry.putAction("1", dummy);
		assertSame(dummy, registry.getAction("1"));
	}
}
