package com.googlecode.fspotcloud.client.useraction.user.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.LoginView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class DoLoginHandler implements IActionHandler {
    private final LoginView.LoginPresenter loginPresenter;

    @Inject
    public DoLoginHandler(LoginView.LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void performAction(String actionId) {
        loginPresenter.login();
    }
}
