package com.googlecode.fspotcloud.testharness;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.keyboardaction.ActionMenu;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.HelpActionsFactory;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

public class ToolbarProvider implements Provider<ActionToolbar> {

    @Inject
    private KeyboardActionFactory factory;
    @Override
    public ActionToolbar get() {
        ActionMenu menu = factory.getMenu("First menu!");
        menu.add(HelpActionsFactory.SHOW_HELP_ACTION);
        menu.add(MainBuilder.OK);
        menu.add(MainBuilder.HOME);
        ActionToolbar toolbar = factory.getToolBar();
        toolbar.add(MainBuilder.OK);
        toolbar.add(MainBuilder.GO_OUT);
        toolbar.add(menu);

        toolbar.add(MainBuilder.HOME);
        toolbar.add(MainBuilder.THREE);
        toolbar.add(factory.getButton(HelpActionsFactory.SHOW_HELP_ACTION));
        toolbar.add(factory.getButton(MainBuilder.DEMO));
        return toolbar;
    }
}