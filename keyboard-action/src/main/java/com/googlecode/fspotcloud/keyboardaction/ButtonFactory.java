package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class ButtonFactory {

    private final EventBus eventBus;
    private final ActionUIRegistry actionUIRegistry;
    private ActionButtonResources buttonResources;
    private final ActionMenuResources menuResources;
    private final ActionMenuItemSafeHtml actionMenuItemSafeHtml;

    @Inject
    private ButtonFactory(EventBus eventBus,
                          ActionUIRegistry actionUIRegistry,
                          ActionButtonResources buttonResources,
                          ActionMenuResources menuResources,
                          ActionMenuItemSafeHtml actionMenuItemSafeHtml) {
        this.eventBus = eventBus;
        this.actionUIRegistry = actionUIRegistry;
        this.buttonResources = buttonResources;
        this.menuResources = menuResources;
        this.actionMenuItemSafeHtml = actionMenuItemSafeHtml;
        menuResources.style().ensureInjected();
        buttonResources.style().ensureInjected();
    }

    public void setButtonResources(ActionButtonResources buttonResources) {
        this.buttonResources = buttonResources;
    }

    public ActionButton getButton(ActionUIDef actionUIDef) {
        return getButton(actionUIDef, buttonResources);
    }

    public ActionButton getButton(ActionUIDef actionUIDef, ActionButtonResources buttonResources
    ) {
        return new ActionButton(actionUIDef, eventBus, buttonResources);
    }

    public ActionButton getButton(String actionId) {
        ActionUIDef actionUIDef = actionUIRegistry.getAction(actionId);
        return getButton(actionUIDef);
    }

    public ActionMenu getMenu(String caption) {
        return new ActionMenu(caption, actionUIRegistry, eventBus, menuResources, actionMenuItemSafeHtml);
    }

}
