package com.googlecode.fspotcloud.client.enduseraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.user.handler.*;
import com.googlecode.fspotcloud.client.main.view.api.*;
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
                   SendConfirmationView.SendConfirmationPresenter sendConfirmationPresenter,
                   SendPasswordResetView.SendPasswordResetPresenter sendPasswordResetPresenter,
                   ChangePasswordView.ChangePasswordPresenter changePasswordPresenter,
                   SignUpView.SignUpPresenter signUpPresenter,
                   UserActions actions) {
        this.actions = actions;
        configBuilder.bindHandler(actions.otherLogin, go3rdPartyLoginAction);
        configBuilder.bindHandler(actions.goSignUp, goSignUpHandler);
        configBuilder.bindHandler(actions.goResetPassword, goResetPasswordHandler);
        configBuilder.bindHandler(actions.goResendConfirmation, goResendConfirmationHandler);
        configBuilder.bindHandler(actions.doLogin, doLoginHandler);
        configBuilder.bindHandler(actions.goAccountPage, goAccountPageHandler);
        configBuilder.bindHandler(actions.doMailFullsize, mailFullsizePresenter);
        configBuilder.bindHandler(actions.doSendEmailConfirmation, sendConfirmationPresenter);
        configBuilder.bindHandler(actions.doRequestPasswordReset, sendPasswordResetPresenter);
        configBuilder.bindHandler(actions.doPasswordReset, changePasswordPresenter);
        configBuilder.bindHandler(actions.doSignUp, signUpPresenter);
    }
}
