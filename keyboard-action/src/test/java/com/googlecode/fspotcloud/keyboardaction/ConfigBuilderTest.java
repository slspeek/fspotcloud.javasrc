package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.googlecode.fspotcloud.testharness.MainBuilder;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(JukitoRunner.class)
public class ConfigBuilderTest {

	public static class Module extends JukitoModule {

		@Override
		protected void configureTest() {
			bind(new TypeLiteral<List<ActionCategory>>() {
			}).toInstance(Lists.<ActionCategory> newArrayList());
		}
	}

	@Inject
	private ConfigBuilder configBuilder;
	@Inject
	private ActionHandlerRegistry actionHandlerRegistry;
	@Inject
	private KeyboardPreferences keyboardPreferences;
	@Inject
	private ActionUIRegistry actionUIRegistry;

	private ActionCategory category;
	private IActionHandler dummy = new IActionHandler() {
		@Override
		public void performAction(String actionId) {

		}
	};
	private Relevance relevance = new Relevance().addDefaultKeys(KeyStroke.ESC);

	@Before
	public void setUp() throws Exception {
		category = configBuilder.createCategory("test");
	}

	@Test
	public void testAddBinding() throws Exception {
		configBuilder.addBinding(category, MainBuilder.LOGIN_DEF, dummy,
				relevance);
		assertSame(dummy, actionHandlerRegistry.getAction(MainBuilder.LOGIN));
		doVerification();
	}

	private void doVerification() {
		assertEquals(MainBuilder.LOGIN_DEF,
				actionUIRegistry.getAction(MainBuilder.LOGIN));
		final List<KeyStroke> defaultKeysForAction = keyboardPreferences
				.getDefaultKeysForAction(MainBuilder.LOGIN);
		assertEquals(1, defaultKeysForAction.size());
	}

	@Test
	public void testRegister() throws Exception {
		configBuilder.register(category, MainBuilder.LOGIN_DEF, relevance);
		doVerification();
	}
}
