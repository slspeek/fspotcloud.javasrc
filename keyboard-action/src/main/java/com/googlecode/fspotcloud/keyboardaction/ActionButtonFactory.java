package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class ActionButtonFactory {

    private final EventBus eventBus;
    private final ActionUIRegistry actionUIRegistry;
    private final KeyboardActionResources keyboardActionResources;
    private final ActionMenuItemSafeHtml actionMenuItemSafeHtml;

    @Inject
    private ActionButtonFactory(EventBus eventBus,
                                ActionUIRegistry actionUIRegistry,
                                KeyboardActionResources keyboardActionResources,
                                ActionMenuItemSafeHtml actionMenuItemSafeHtml) {
        this.eventBus = eventBus;
        this.actionUIRegistry = actionUIRegistry;
        this.keyboardActionResources = keyboardActionResources;
        this.actionMenuItemSafeHtml = actionMenuItemSafeHtml;
    }

    ActionButton get(ActionUIDef actionUIDef) {
        return new ActionButton(actionUIDef, eventBus, keyboardActionResources);
    }

    ActionButton get(String actionId) {
        ActionUIDef actionUIDef = actionUIRegistry.getAction(actionId);
        return get(actionUIDef);
    }

    ActionMenu getMenu(String caption) {
        return new ActionMenu(caption, actionUIRegistry, eventBus, keyboardActionResources, actionMenuItemSafeHtml);
    }

}
