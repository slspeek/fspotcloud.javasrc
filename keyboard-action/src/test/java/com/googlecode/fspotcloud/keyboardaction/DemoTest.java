package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.testharness.MainBuilder;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class DemoTest {
	@Inject
	@NextTimer
	private TimerInterface nextTimerInterface;
	@Inject
	@ActionTimer
	private TimerInterface actionTimer;
	@Inject
	private Demo demo;
	@Inject
	private ArgumentCaptor<Runnable> demoAction;
	@Inject
	private ArgumentCaptor<Runnable> nextStepAction;
	@Inject
	private Runnable runnable;

	@Test
	public void testWithActionUIDef() throws Exception {
		Demo d = demo.withActionUIDef(MainBuilder.DEMO_DEF);
		demo.getStepList().add(new DemoStep() {
			@Override
			public String getActionId() {
				return "Action";
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
		demo.performAction("1");
		verify(actionTimer).setRunnable(demoAction.capture());
		demoAction.getValue().run();
		verify(nextTimerInterface).setRunnable(nextStepAction.capture());
		nextStepAction.getValue().run();
	}

	@Test
	public void testStop() throws Exception {
		demo.stop();
	}

	@Test
	public void testStopRunning() throws Exception {
		testWithActionUIDef();
		demo.stop();
	}
}
