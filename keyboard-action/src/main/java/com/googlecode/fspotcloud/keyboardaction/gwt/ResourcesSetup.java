package com.googlecode.fspotcloud.keyboardaction.gwt;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionMenuResources;
import com.googlecode.fspotcloud.keyboardaction.HelpResources;

public class ResourcesSetup {

    private final ActionButtonResources actionButtonResources;
    private final ActionMenuResources actionMenuResources;
    private final HelpResources helpResources;
    private final ActionToolbarResources actionToolbarResources;

    @Inject
    public ResourcesSetup(ActionButtonResources actionButtonResources,
                          ActionMenuResources actionMenuResources,
                          HelpResources helpResources,
                          ActionToolbarResources actionToolbarResources) {
        this.actionButtonResources = actionButtonResources;
        this.actionMenuResources = actionMenuResources;
        this.helpResources = helpResources;
        this.actionToolbarResources = actionToolbarResources;
    }

    public void ensureInjected() {
        actionButtonResources.style().ensureInjected();
        actionMenuResources.style().ensureInjected();
        helpResources.style().ensureInjected();
        actionToolbarResources.style().ensureInjected();
    }
}
