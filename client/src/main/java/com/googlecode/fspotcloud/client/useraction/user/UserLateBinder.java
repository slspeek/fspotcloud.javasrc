package com.googlecode.fspotcloud.client.useraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.user.handler.*;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class UserLateBinder {

    private final UserActions actions;

    @Inject
    UserLateBinder(ConfigBuilder configBuilder,
                   Go3rdPartyLoginAction go3rdPartyLoginAction,
                   GoResetPasswordHandler goResetPasswordHandler,
                   GoResendConfirmationHandler goResendConfirmationHandler,
                   DoLoginHandler doLoginHandler,
                   GoSignUpHandler goSignUpHandler,
                   UserActions actions) {
        this.actions = actions;
        configBuilder.bindHandler(actions.otherLogin, go3rdPartyLoginAction);
        configBuilder.bindHandler(actions.goSignUp, goSignUpHandler);
        configBuilder.bindHandler(actions.goResetPassword, goResetPasswordHandler);
        configBuilder.bindHandler(actions.goResendConfirmation, goResendConfirmationHandler);
        configBuilder.bindHandler(actions.doLogin, doLoginHandler);
    }
}
