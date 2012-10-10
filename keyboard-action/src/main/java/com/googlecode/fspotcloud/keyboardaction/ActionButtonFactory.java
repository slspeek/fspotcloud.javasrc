package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class ActionButtonFactory {

    private final EventBus eventBus;
    private final ButtonDefinitions buttonDefinitions;
    private final KeyboardActionResources keyboardActionResources;

    @Inject
    private ActionButtonFactory(EventBus eventBus,
                               ButtonDefinitions buttonDefinitions,
                               KeyboardActionResources keyboardActionResources) {
        this.eventBus = eventBus;
        this.buttonDefinitions = buttonDefinitions;
        this.keyboardActionResources = keyboardActionResources;
    }

    ActionButton get(ActionDef actionDef){
        return new ActionButton(actionDef, eventBus, keyboardActionResources);
    }

    ActionButton get(String actionId) {
        ActionDef actionDef = buttonDefinitions.getAction(actionId);
        return get(actionDef);
    }

}
