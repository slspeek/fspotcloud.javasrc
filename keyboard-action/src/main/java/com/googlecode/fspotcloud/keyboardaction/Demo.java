package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.fspotcloud.keyboardaction.gwt.DemoPopupView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class Demo implements IActionHandler {

	private final Logger log = Logger.getLogger(Demo.class.getName());
	private final DemoPopupView demoPopup;
	private List<DemoStep> stepList = newArrayList();
	private int currentDemoStep = 0;
	private DemoStep currentStep;
	private final TimerInterface actionTimer;
	private final TimerInterface nextCalltimer;
	private final EventBus eventBus;
	private final IModeController modeController;

	@Inject
	Demo(DemoPopupView demoPopup, @ActionTimer TimerInterface actionTimer,
			@NextTimer TimerInterface nextCalltimer, EventBus eventBus,
			IModeController modeController) {
		this.demoPopup = demoPopup;
		this.actionTimer = actionTimer;
		this.nextCalltimer = nextCalltimer;
		this.eventBus = eventBus;
		this.modeController = modeController;
		demoPopup.setTitle("Demo");
		demoPopup.setDemo(this);
	}

	public Demo withActionUIDef(ActionUIDef actionUIDef) {
		demoPopup.setTitle(actionUIDef.getName());
		return this;
	}

	public List<DemoStep> getStepList() {
		return stepList;
	}

	@Override
	public void performAction(final String actionId) {
		modeController.setFlag(BuiltinFlags.DEMOING);
		//check for the end
		if (currentDemoStep < stepList.size()) {
			currentStep = stepList.get(currentDemoStep);
			eventBus.fireEvent(new ActionDemoEvent(currentStep.getActionId(),
					true));
			demoPopup.setSafeHtml(currentStep.getContent());
			demoPopup.setPopupPosition(10, 10);
			demoPopup.show();
			actionTimer.setRunnable(new Runnable() {
				@Override
				public void run() {
					currentStep.getAction().run();
				}
			});
			actionTimer.schedule(1000);
			nextCalltimer.setRunnable(new Runnable() {
				@Override
				public void run() {
					eventBus.fireEvent(new ActionDemoEvent(currentStep
							.getActionId(), false));
					performAction(actionId);
				}
			});
			currentDemoStep++;
			nextCalltimer.schedule(currentStep.pauseTime());
		} else {
			cleanUp();
		}
	}

	private void cleanUp() {
		modeController.unsetFlag(BuiltinFlags.DEMOING);
		demoPopup.hide();
		currentDemoStep = 0;
	}

	public void stop() {
		try {
			eventBus.fireEvent(new ActionDemoEvent(currentStep.getActionId(),
					false));
			actionTimer.cancel();
			nextCalltimer.cancel();
			cleanUp();
		} catch (NullPointerException npe) {
			log.log(Level.FINEST, "stop called on not running demo", npe);
		}
	}

}
