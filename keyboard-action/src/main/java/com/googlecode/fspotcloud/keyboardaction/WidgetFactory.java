package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class WidgetFactory {

    private final EventBus eventBus;
    private final ActionUIRegistry actionUIRegistry;
    private final ActionMenuResources menuResources;
    private final ActionMenuItemSafeHtml actionMenuItemSafeHtml;
    private final WidgetRegistry widgetRegistry;
    private final KeyboardActionResources keyboardActionResources;
    private ActionButtonResources buttonResources;


    @Inject
    private WidgetFactory(EventBus eventBus,
                          ActionUIRegistry actionUIRegistry,
                          ActionButtonResources buttonResources,
                          ActionMenuResources menuResources,
                          ActionMenuItemSafeHtml actionMenuItemSafeHtml,
                          WidgetRegistry widgetRegistry,
                          KeyboardActionResources keyboardActionResources
    ) {
        this.eventBus = eventBus;
        this.actionUIRegistry = actionUIRegistry;
        this.buttonResources = buttonResources;
        this.menuResources = menuResources;
        this.actionMenuItemSafeHtml = actionMenuItemSafeHtml;
        this.widgetRegistry = widgetRegistry;
        this.keyboardActionResources = keyboardActionResources;
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
        final ActionButton actionButton = new ActionButton(actionUIDef, eventBus, buttonResources);
        widgetRegistry.add(actionUIDef.getId(), actionButton);
        return actionButton;
    }

    public ActionButton getButton(String actionId) {
        return getButton(actionId, buttonResources);
    }

    public ActionMenu getMenu(String caption) {
        final ActionMenu actionMenu = new ActionMenu(caption, actionUIRegistry, eventBus, menuResources, actionMenuItemSafeHtml);
        return actionMenu;
    }

    public ActionButton getButton(String actionId, ActionButtonResources actionButtonResources) {
        ActionUIDef actionUIDef = actionUIRegistry.getAction(actionId);
        return getButton(actionUIDef, actionButtonResources);
    }
}
