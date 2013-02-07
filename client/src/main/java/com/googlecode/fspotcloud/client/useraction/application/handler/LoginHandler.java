package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;

public class LoginHandler implements IActionHandler

{
    private final Logger log = Logger.getLogger(LoginHandler.class.getName());
    private final ClientLoginManager clientLoginManager;


    @Inject
    public LoginHandler(ClientLoginManager clientLoginManager) {
        this.clientLoginManager = clientLoginManager;
    }

    @Override
    public void performAction(String actionId) {
        clientLoginManager.redirectToLogin();
    }
}
