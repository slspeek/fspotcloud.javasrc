package com.googlecode.fspotcloud.testharness;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionMenu;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.HelpActionsFactory;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

public class ToolbarProvider implements Provider<ActionToolbar> {

    @Inject
    private KeyboardActionFactory factory;
    @Override
    public ActionToolbar get() {
//        ActionMenu menu = factory.getMenu("First menu!");
//        menu.add(HelpActionsFactory.SHOW_HELP_ACTION);
//        menu.add(MainBuilder.LOGIN);
//        menu.add(MainBuilder.HOME);
        ActionToolbar toolbar = factory.getToolBar();
        toolbar.add(MainBuilder.LOGIN);
        toolbar.add(MainBuilder.GO_OUT);
        //toolbar.add(menu);

        toolbar.add(MainBuilder.HOME);
        toolbar.add(MainBuilder.LOGOUT);
        toolbar.add(factory.getButton(HelpActionsFactory.SHOW_HELP_ACTION));
        toolbar.add(factory.getButton(MainBuilder.DEMO));
        return toolbar;
    }
}
