package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

public class EmailConfirmationToolbarProvider implements Provider<ActionToolbar> {


    private final KeyboardActionFactory keyboardActionFactory;
    private final ApplicationActions actions;

    @Inject
    public EmailConfirmationToolbarProvider(KeyboardActionFactory keyboardActionFactory,
                                            ApplicationActions actions) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.actions = actions;
    }


    @Override
    public ActionToolbar get() {
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();
        toolbar.add(actions.login);
        return toolbar;
    }
}
