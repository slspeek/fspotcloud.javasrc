package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class ActionManagerFactoryTest {

	public static class Module extends JukitoModule {

		@Override
		protected void configureTest() {
			bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
			bind(ActionHandlerRegistry.class).in(Singleton.class);
		}
	}

	@Inject
	private ActionManagerFactory factory;

	@Inject
	private EventBus eventBus;
	@Inject
	private ActionHandlerRegistry registry;
	@Inject
	private IActionHandler iActionHandler;

	@Test
	public void testIsConnected() throws Exception {
		IActionManager manager = factory.get();
		assertNotNull(manager);
		registry.putAction("1", iActionHandler);
		eventBus.fireEvent(new KeyboardActionEvent("1"));
		verify(iActionHandler).performAction("1");
	}
}
