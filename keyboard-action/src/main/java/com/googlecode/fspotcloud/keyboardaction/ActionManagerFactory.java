package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ActionManagerFactory implements Provider<ActionManager> {

    private final ActionHandlerRegistry actionHandlerRegistry;
    private final EventBus eventBus;

    @Inject
    private ActionManagerFactory(ActionHandlerRegistry actionHandlerRegistry, EventBus eventBus) {
        this.actionHandlerRegistry = actionHandlerRegistry;
        this.eventBus = eventBus;
    }

    public ActionManager get() {
        final ActionManager actionManager = new ActionManager(actionHandlerRegistry);
        eventBus.addHandler(KeyboardActionEvent.TYPE, actionManager);
        return actionManager;
    }
}
