package com.googlecode.fspotcloud.client.useraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class UserActions {
    public final ActionUIDef otherLogin;
    public final ActionUIDef goSignUp;
    public final ActionUIDef goResetPassword;
    public final ActionUIDef goResendConfirmation;

    public final ActionUIDef doLogin;



    @Inject
    public UserActions() {
        otherLogin = new ActionUIDef("go-other-login", "Google Login", "Login using Google account", null);
        goSignUp = new ActionUIDef("go-sign-up", "Sign up", "Create an account", null);
        goResetPassword = new ActionUIDef("go-reset-password", "Reset password", "Reset your password in case you lost it", null);
        goResendConfirmation = new ActionUIDef("go-resend-confirmation", "Resend confirmation", "Resend a confirmation email", null);
        doLogin = new ActionUIDef("do-login", "Login", "Let the server decide whether you can go further", null);
    }
}
