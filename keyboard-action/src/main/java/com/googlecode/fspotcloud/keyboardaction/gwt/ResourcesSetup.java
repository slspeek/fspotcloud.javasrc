package com.googlecode.fspotcloud.keyboardaction.gwt;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionMenuResources;
import com.googlecode.fspotcloud.keyboardaction.HelpResources;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionResources;

public class ResourcesSetup {

    private final ActionButtonResources actionButtonResources;
    private final ActionMenuResources actionMenuResources;
    private final HelpResources helpResources;
    private final KeyboardActionResources keyboardActionResources;

    @Inject
    public ResourcesSetup(ActionButtonResources actionButtonResources,
                          ActionMenuResources actionMenuResources,
                          HelpResources helpResources,
                          KeyboardActionResources keyboardActionResources) {
        this.actionButtonResources = actionButtonResources;
        this.actionMenuResources = actionMenuResources;
        this.helpResources = helpResources;
        this.keyboardActionResources = keyboardActionResources;
    }

    public void ensureInjected() {
        actionButtonResources.style().ensureInjected();
        actionMenuResources.style().ensureInjected();
        helpResources.style().ensureInjected();
        keyboardActionResources.style().ensureInjected();
    }
}
