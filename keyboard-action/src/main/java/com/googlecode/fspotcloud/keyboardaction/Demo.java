package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.Timer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class Demo implements IActionHandler {

    private final HelpPopup helpPopup;
    private final ActionDef actionDef;
    private List<DemoStep> stepList = newArrayList();
    private int currentDemoStep = 0;
    private Timer timer;

    public Demo(HelpPopup helpPopup, ActionDef actionDef) {
        this.helpPopup = helpPopup;
        this.actionDef = actionDef;
    }

    public List<DemoStep> getStepList() {
        return stepList;
    }

    @Override
    public void performAction(final String actionId) {
        //check for the end
        if (currentDemoStep < stepList.size()) {
            final DemoStep step = stepList.get(currentDemoStep);
            helpPopup.setSafeHtml(step.getContent());
            helpPopup.setPopupPosition(10,10);
            helpPopup.show();
            timer = new Timer() {
                @Override
                public void run() {
                    step.getAction().run();
                }
            };
            timer.schedule(500);
            timer = new Timer() {
                @Override
                public void run() {
                    performAction(actionId);
                }
            };
            currentDemoStep++;
            timer.schedule(step.pauseTime());

        }   else {
            helpPopup.hide();
            currentDemoStep = 0;
        }
    }


}
