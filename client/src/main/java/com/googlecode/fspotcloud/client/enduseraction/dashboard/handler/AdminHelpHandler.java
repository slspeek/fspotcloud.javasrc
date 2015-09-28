package com.googlecode.fspotcloud.client.enduseraction.dashboard.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.keyboardaction.HelpActionsFactory;
import com.googlecode.fspotcloud.keyboardaction.HelpConfig;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class AdminHelpHandler implements IActionHandler {

	private final IActionHandler delegate;

	@Inject
	public AdminHelpHandler(HelpActionsFactory helpActionsFactory,
			CategoryDef categoryDef) {
		final HelpConfig helpConfig = new HelpConfig("Dashboard keyboard help");
		helpConfig.addToFirstColumn(categoryDef.DASHBOARD);
		helpConfig.addToSecondColumn(categoryDef.USERGROUP);
		delegate = helpActionsFactory.getHelpAction(helpConfig);
	}

	@Override
	public void performAction(String actionId) {
		delegate.performAction(actionId);
	}
}
