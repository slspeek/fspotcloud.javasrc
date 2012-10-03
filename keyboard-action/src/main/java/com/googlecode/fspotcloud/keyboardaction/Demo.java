package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class Demo implements IActionHandler {

    private final DemoPopup demoPopup;
    private final ActionDef actionDef;
    private List<DemoStep> stepList = newArrayList();
    private int currentDemoStep = 0;
    private Timer timer;
    private final EventBus eventBus;
    private boolean stopped = false;


    public Demo(DemoPopup demoPopup, ActionDef actionDef, EventBus eventBus) {
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
            final DemoStep step = stepList.get(currentDemoStep);
            eventBus.fireEvent(new ActionDemoEvent(step.getActionId(), true));
            demoPopup.setSafeHtml(step.getContent());
            demoPopup.setPopupPosition(10, 10);
            demoPopup.show();
            timer = new Timer() {
                @Override
                public void run() {
                    step.getAction().run();
                }
            };
            timer.schedule(1000);
            timer = new Timer() {
                @Override
                public void run() {
                    eventBus.fireEvent(new ActionDemoEvent(step.getActionId(), false));
                    performAction(actionId);
                }
            };
            currentDemoStep++;
            timer.schedule(step.pauseTime());

        }   else {
                demoPopup.hide();
                stopped = false;
                currentDemoStep = 0;
        }
    }


    public void stop() {
        stopped = true;
    }
}
