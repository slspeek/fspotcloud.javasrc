package com.googlecode.fspotcloud.client.main.gin;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.main.ui.*;
import com.googlecode.fspotcloud.client.main.view.*;
import com.googlecode.fspotcloud.client.main.view.api.*;

@GwtCompatible
public class UserMVPModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);
        bind(LoginView.LoginPresenter.class).to(LoginActivity.class).in(Singleton.class);
        bind(SignUpView.class).to(SignUpViewImpl.class).in(Singleton.class);
        bind(SignUpView.SignUpPresenter.class).to(SignUpActivity.class).in(Singleton.class);
        bind(UserAccountView.class).to(UserAccountViewImpl.class).in(Singleton.class);
        bind(UserAccountView.UserAccountPresenter.class)
                .to(UserAccountActivity.class).in(Singleton.class);
        bind(EmailConfirmationView.class).to(EmailConfirmationViewImpl.class).in(Singleton.class);
        bind(EmailConfirmationView.EmailConfirmationPresenter.class)
                .to(EmailConfirmationActivity.class).in(Singleton.class);
        bind(SendConfirmationView.class).to(SendConfirmationViewImpl.class).in(Singleton.class);
        bind(SendConfirmationView.SendConfirmationPresenter.class).to(SendConfirmationActivity.class).in(Singleton.class);
        bind(ChangePasswordView.class).to(ChangePasswordViewImpl.class).in(Singleton.class);
        bind(ChangePasswordView.ChangePasswordPresenter.class).to(ChangePasswordActivity.class).in(Singleton.class);
        bind(SendPasswordResetView.class).to(SendPasswordResetViewImpl.class).in(Singleton.class);
        bind(SendPasswordResetView.SendPasswordResetPresenter.class).to(SendPasswordResetActivity.class).in(Singleton.class);
    }
}
