package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class Demo implements IActionHandler {

    private final Logger log = Logger.getLogger(Demo.class.getName());
    private final DemoPopup demoPopup;
    private final ActionDef actionDef;
    private List<DemoStep> stepList = newArrayList();
    private int currentDemoStep = 0;
    private DemoStep currentStep;
    private Timer actionTimer;
    private Timer nextCalltimer;
    private final EventBus eventBus;
    private boolean stopped = false;


    Demo(DemoPopup demoPopup, ActionDef actionDef, EventBus eventBus) {
        this.demoPopup = demoPopup;
        this.actionDef = actionDef;
        this.eventBus = eventBus;
        demoPopup.setTitle("Demo");
        demoPopup.setDemo(this);
    }

    public List<DemoStep> getStepList() {
        return stepList;
    }

    @Override
    public void performAction(final String actionId) {
        //check for the end
        if (!stopped && currentDemoStep < stepList.size()) {
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
            demoPopup.hide();
            stopped = false;
            currentDemoStep = 0;
        }
    }


    public void stop() {
        stopped = true;
        try {
            eventBus.fireEvent(new ActionDemoEvent(currentStep.getActionId(), false));
            actionTimer.cancel();
            nextCalltimer.cancel();
        } catch (NullPointerException npe) {
            log.log(Level.FINEST, "stop called on not running demo", npe);
        }
    }
}
