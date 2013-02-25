package com.googlecode.fspotcloud.client.enduseraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class UserActions {
    public final ActionUIDef otherLogin;
    public final ActionUIDef goSignUp;
    public final ActionUIDef goAccountPage;
    public final ActionUIDef goResetPassword;
    public final ActionUIDef goResendConfirmation;
    public final ActionUIDef doLogin;
    public final ActionUIDef doMailFullsize;
    public final ActionUIDef doCangePassword;



    @Inject
    public UserActions() {
        otherLogin = new ActionUIDef("go-other-login", "Google Login", "Login using Google account");
        goSignUp = new ActionUIDef("go-sign-up", "Sign up", "Create an account");
        goResetPassword = new ActionUIDef("go-reset-password", "Reset password", "Reset your password in case you lost it");
        goResendConfirmation = new ActionUIDef("go-resend-confirmation", "Resend confirmation", "Resend a confirmation email");
        doLogin = new ActionUIDef("do-login", "Login", "Let the server decide whether you can go further");
        goAccountPage = new ActionUIDef("go-account-page", "Profile", "Go to the profile page to change your password");
        doMailFullsize = new ActionUIDef("do-mail-fullsize", "Mail image", "Request the full size image per email");
        doCangePassword = new ActionUIDef("do-change-password", "Change password", "Change password");
    }
}
