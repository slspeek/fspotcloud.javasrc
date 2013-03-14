package com.googlecode.fspotcloud.client.enduseraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.keyboardaction.HelpActionsFactory;
import com.googlecode.fspotcloud.keyboardaction.HelpConfig;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class UsersHelpHandler implements IActionHandler {

    private final IActionHandler delegate;

    @Inject
    public UsersHelpHandler(HelpActionsFactory helpActionsFactory, CategoryDef categoryDef) {
        final HelpConfig helpConfig = new HelpConfig("Keyboard help");
        helpConfig.addToFirstColumn(categoryDef.APPLICATION,  categoryDef.RASTER);
        helpConfig.addToSecondColumn(categoryDef.NAVIGATION, categoryDef.SLIDESHOW, categoryDef.USER);
        delegate = helpActionsFactory.getHelpAction(helpConfig);
    }

    @Override
        public void performAction(String actionId) {
        delegate.performAction(actionId);
    }
}
