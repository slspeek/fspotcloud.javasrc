package com.googlecode.fspotcloud.client.useraction.handler.application;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogoutHandler implements IActionHandler

{
    private final Logger log = Logger.getLogger(LogoutHandler.class.getName());
    private final ClientLoginManager clientLoginManager;

    public LogoutHandler(ClientLoginManager clientLoginManager) {
        this.clientLoginManager = clientLoginManager;
    }

    @Override
    public void performAction(String actionId) {
        clientLoginManager.getUserInfoAsync(new GetUserInfo("post-login"),
                new AsyncCallback<UserInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "No user info ", caught);
                    }

                    @Override
                    public void onSuccess(final UserInfo result) {
                        clientLoginManager.logout(new AsyncCallback<VoidResult>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                log.log(Level.SEVERE,
                                        "An error prevented the server from logging of the user",
                                        caught);
                            }

                            @Override
                            public void onSuccess(VoidResult result2) {
                                if ("GAE_LOGIN".equals(
                                        result.getLoginType())) {
                                    Window.Location.replace(result.getLogoutUrl());
                                }
                            }
                        });
                    }
                });


    }
}
