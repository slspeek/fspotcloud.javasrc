package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.fspotcloud.testharness.HomePlace;
import com.googlecode.fspotcloud.testharness.OutPlace;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class ModeControllerProviderIsConnectedTest {

	public static final String ACTION_ID = "1";

	public static class Module extends JukitoModule {
		@Override
		protected void configureTest() {
			bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		}
	}

	@Inject
	private ModeControllerProvider provider;
	@Inject
	private KeyboardPreferences keyboardPreferences;
	@Inject
	private WidgetRegistry widgetRegistry;
	@Inject
	private ActionWidget actionWidget;
	@Inject
	private EventBus eventBus;
	@Inject
	private IPlaceController placeController;

	@Before
	public void setUp() throws Exception {
		widgetRegistry.add(ACTION_ID, actionWidget);
		keyboardPreferences.bind(ACTION_ID,
				new Relevance().addDefaultKeys(KeyStroke.ESC));
		when(placeController.getWhere()).thenReturn(new OutPlace());
	}

	@Test
	public void testGet() throws Exception {
		IModeController controller = provider.get();
		eventBus.fireEvent(new PlaceChangeEvent(new HomePlace()));
		verify(actionWidget).onEvent(
				new ActionStateEvent(ACTION_ID, true, "Esc"));
	}
}
