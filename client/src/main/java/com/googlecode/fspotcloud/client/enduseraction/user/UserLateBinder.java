package com.googlecode.fspotcloud.client.enduseraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.user.handler.*;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class UserLateBinder {

	private final UserActions actions;

	@Inject
	UserLateBinder(
			ConfigBuilder configBuilder,
			Go3rdPartyLoginHandler go3rdPartyLoginHandler,
			GoResetPasswordHandler goResetPasswordHandler,
			GoResendConfirmationHandler goResendConfirmationHandler,
			GoSignUpHandler goSignUpHandler,
			LoginView.LoginPresenter loginPresenter,
			GoAccountPageHandler goAccountPageHandler,
			MailFullsizeView.MailFullsizePresenter mailFullsizePresenter,
			SendConfirmationView.SendConfirmationPresenter sendConfirmationPresenter,
			SendPasswordResetView.SendPasswordResetPresenter sendPasswordResetPresenter,
			ChangePasswordView.ChangePasswordPresenter changePasswordPresenter,
			SignUpView.SignUpPresenter signUpPresenter,
			UserAccountView.UserAccountPresenter userAccountPresenter,
			UserActions actions) {
		this.actions = actions;
		configBuilder.bindHandler(actions.otherLogin, go3rdPartyLoginHandler);
		configBuilder.bindHandler(actions.goSignUp, goSignUpHandler);
		configBuilder.bindHandler(actions.goResetPassword,
				goResetPasswordHandler);
		configBuilder.bindHandler(actions.goResendConfirmation,
				goResendConfirmationHandler);
		configBuilder.bindHandler(actions.doLogin, loginPresenter);
		configBuilder.bindHandler(actions.goAccountPage, goAccountPageHandler);
		configBuilder
				.bindHandler(actions.doMailFullsize, mailFullsizePresenter);
		configBuilder.bindHandler(actions.doSendEmailConfirmation,
				sendConfirmationPresenter);
		configBuilder.bindHandler(actions.doRequestPasswordReset,
				sendPasswordResetPresenter);
		configBuilder.bindHandler(actions.doPasswordReset,
				changePasswordPresenter);
		configBuilder.bindHandler(actions.doSignUp, signUpPresenter);
		configBuilder.bindHandler(actions.doChangePassword,
				userAccountPresenter);
	}
}
