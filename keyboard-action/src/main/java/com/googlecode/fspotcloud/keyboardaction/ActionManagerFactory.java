package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ActionManagerFactory implements Provider<ActionManager> {

    private final ActionImplementationRegister actionImplementationRegister;
    private final EventBus eventBus;

    @Inject
    public ActionManagerFactory(ActionImplementationRegister actionImplementationRegister, EventBus eventBus) {
        this.actionImplementationRegister = actionImplementationRegister;
        this.eventBus = eventBus;
    }

    public ActionManager get() {
        final ActionManager actionManager = new ActionManager(actionImplementationRegister);
        eventBus.addHandler(KeyboardActionEvent.TYPE, actionManager);
        return actionManager;
    }
}
