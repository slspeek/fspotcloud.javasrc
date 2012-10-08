package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.useraction.navigation.NavigationActions;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.HelpActions;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

public class ToolbarProvider implements Provider<ActionToolbar> {


    private final KeyboardActionFactory keyboardActionFactory;
    private final CategoryDef categoryDef;

    @Inject
    public ToolbarProvider(UserActionFactory actionFactory, KeyboardActionFactory keyboardActionFactory,

                           CategoryDef categoryDef) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.categoryDef = categoryDef;
    }


    @Override
    public ActionToolbar get() {
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();
        toolbar.addCategory(categoryDef.APPLICATION);
        toolbar.addCategory(categoryDef.NAVIGATION);
        toolbar.addCategory(categoryDef.SLIDESHOW);
        toolbar.add(keyboardActionFactory.getButton(HelpActions.SHOW_HELP_ACTION));
        toolbar.add(keyboardActionFactory.getButton(HelpActions.HIDE_HELP_ACTION));
        return toolbar;
    }
}
