package com.googlecode.fspotcloud.client.enduseraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.user.handler.*;
import com.googlecode.fspotcloud.client.main.view.api.MailFullsizeView;
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
                   GoAccountPageHandler goAccountPageHandler,
                   MailFullsizeView.MailFullsizePresenter mailFullsizePresenter,
                   UserActions actions) {
        this.actions = actions;
        configBuilder.bindHandler(actions.otherLogin, go3rdPartyLoginAction);
        configBuilder.bindHandler(actions.goSignUp, goSignUpHandler);
        configBuilder.bindHandler(actions.goResetPassword, goResetPasswordHandler);
        configBuilder.bindHandler(actions.goResendConfirmation, goResendConfirmationHandler);
        configBuilder.bindHandler(actions.doLogin, doLoginHandler);
        configBuilder.bindHandler(actions.goAccountPage, goAccountPageHandler);
        configBuilder.bindHandler(actions.doMailFullsize, mailFullsizePresenter);
    }
}
