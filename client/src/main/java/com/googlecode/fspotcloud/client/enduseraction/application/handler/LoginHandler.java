package com.googlecode.fspotcloud.client.enduseraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;

public class LoginHandler implements IActionHandler

{
	private final Logger log = Logger.getLogger(LoginHandler.class.getName());
	private final IClientLoginManager clientLoginManager;

	@Inject
	public LoginHandler(IClientLoginManager clientLoginManager) {
		this.clientLoginManager = clientLoginManager;
	}

	@Override
	public void performAction(String actionId) {
		clientLoginManager.redirectToLogin();
	}
}
