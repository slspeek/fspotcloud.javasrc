package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Lists;
import com.google.gwt.safehtml.shared.SafeHtml;
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

@RunWith(JukitoRunner.class)
public class DemoBuilderFactoryTest {

	public static class Module extends JukitoModule {

		@Override
		protected void configureTest() {
			bind(new TypeLiteral<List<ActionCategory>>() {
			}).toInstance(Lists.<ActionCategory> newArrayList());
		}
	}

	@Inject
	private DemoBuilderFactory demoBuilderFactory;
	@Inject
	private Runnable runnable;
	@Inject
	private ConfigBuilder configBuilder;
	@Inject
	private HelpResources helpResources;
	@Inject
	private IHelpContentGenerator generator;

	@Before
	public void setUp() throws Exception {

		configBuilder.register(configBuilder.createCategory("test"),
				MainBuilder.LOGIN_DEF, new Relevance());
	}

	@Test
	public void testGet() throws Exception {

		DemoBuilder demoBuilder = demoBuilderFactory.get(MainBuilder.DEMO_DEF);
		demoBuilder.addStep(new DemoStep() {
			@Override
			public String getActionId() {
				return null;
			}

			@Override
			public Runnable getAction() {
				return runnable;
			}

			@Override
			public int pauseTime() {
				return 0;
			}

			@Override
			public SafeHtml getContent() {
				return null;
			}
		});
		demoBuilder.addStep(MainBuilder.LOGIN, 1000);
		assertEquals(2, demoBuilder.getDemo().getStepList().size());
	}

	@Test
	public void testGetStopDemoHandler() throws Exception {
		testGet();
		IActionHandler stop = demoBuilderFactory.getStopDemoHandler();
		stop.performAction("");

	}
}
