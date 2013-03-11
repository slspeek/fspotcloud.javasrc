package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class Demo implements IActionHandler {

    private final Logger log = Logger.getLogger(Demo.class.getName());
    private final DemoPopup demoPopup;
    private List<DemoStep> stepList = newArrayList();
    private int currentDemoStep = 0;
    private DemoStep currentStep;
    private Timer actionTimer;
    private Timer nextCalltimer;
    private final EventBus eventBus;
    private final IModeController modeController;

    @Inject
    Demo(DemoPopup demoPopup, EventBus eventBus, IModeController modeController) {
        this.demoPopup = demoPopup;
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
            eventBus.fireEvent(new ActionDemoEvent(currentStep.getActionId(), true));
            demoPopup.setSafeHtml(currentStep.getContent());
            demoPopup.setPopupPosition(10, 10);
            demoPopup.show();
            actionTimer = new Timer() {
                @Override
                public void run() {
                    currentStep.getAction().run();
                }
            };
            actionTimer.schedule(1000);
            nextCalltimer = new Timer() {
                @Override
                public void run() {
                    eventBus.fireEvent(new ActionDemoEvent(currentStep.getActionId(), false));
                    performAction(actionId);
                }
            };
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
            eventBus.fireEvent(new ActionDemoEvent(currentStep.getActionId(), false));
            actionTimer.cancel();
            nextCalltimer.cancel();
            cleanUp();
        } catch (NullPointerException npe) {
            log.log(Level.FINEST, "stop called on not running demo", npe);
        }
    }


}
