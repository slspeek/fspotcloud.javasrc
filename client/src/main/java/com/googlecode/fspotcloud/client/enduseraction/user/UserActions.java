package com.googlecode.fspotcloud.client.enduseraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class UserActions {
	private final Resources RESOURCES;
	public final ActionUIDef otherLogin;
	public final ActionUIDef goSignUp;
	public final ActionUIDef goAccountPage;
	public final ActionUIDef goResetPassword;
	public final ActionUIDef goResendConfirmation;
	public final ActionUIDef doLogin;
	public final ActionUIDef doMailFullsize;
	public final ActionUIDef doChangePassword;
	public final ActionUIDef doPasswordReset;
	public final ActionUIDef doSendEmailConfirmation;
	public final ActionUIDef doRequestPasswordReset;
	public final ActionUIDef doSignUp;

	@Inject
	public UserActions(Resources resources) {
		RESOURCES = resources;
		otherLogin = new ActionUIDef("go-other-login",
				"Login using your Google account", "Login using Google account");
		goSignUp = new ActionUIDef("go-sign-up", "Sign up", "Create an account");
		goResetPassword = new ActionUIDef("go-reset-password",
				"Reset password", "Reset your password in case you lost it");
		goResendConfirmation = new ActionUIDef("go-resend-confirmation",
				"Resend confirmation", "Resend a confirmation email");
		doLogin = new ActionUIDef("do-login", "Login",
				"Let the server decide whether you can go further");
		goAccountPage = new ActionUIDef("go-account-page", "Profile",
				"Go to the profile page to change your password",
				RESOURCES.userIcon());
		doMailFullsize = new ActionUIDef("do-mail-fullsize", "Mail image",
				"Request the full size image per email");
		doChangePassword = new ActionUIDef("do-change-password",
				"Change password", "Change password");
		doPasswordReset = new ActionUIDef("do-password-reset",
				"Change password", "Change password");
		doSendEmailConfirmation = new ActionUIDef("do-send-confirmation",
				"Send", "Resend a confirmation email");
		doRequestPasswordReset = new ActionUIDef("request-password-reset",
				"Reset password", "Request a password reset email");
		doSignUp = new ActionUIDef("do-sign-up", "Sign up",
				"Sign up for a user account");
	}
}
