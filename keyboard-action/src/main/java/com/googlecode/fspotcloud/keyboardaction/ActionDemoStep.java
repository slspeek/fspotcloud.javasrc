package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtml;

public class ActionDemoStep implements DemoStep {

    private final ActionDef actionDef;
    private final EventBus eventBus;
    private final SafeHtml content;
    private final int pauseMillis;


    public ActionDemoStep(ActionDef actionDef, EventBus eventBus, int pauseMillis, SafeHtml content) {
        this.actionDef = actionDef;
        this.eventBus = eventBus;
        this.content = content;
        this.pauseMillis = pauseMillis;
    }

    @Override
    public String getActionId() {
        return actionDef.getId();
    }

    @Override
    public Runnable getAction() {
        return new Runnable() {
            @Override
            public void run() {
                eventBus.fireEvent(new KeyboardActionEvent(actionDef.getId()));
            }
        };
    }

    @Override
    public int pauseTime() {
        return pauseMillis;
    }

    @Override
    public SafeHtml getContent() {
        return content;
    }
}
