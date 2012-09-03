/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.LoginView;

import java.util.logging.Logger;


public class LoginViewImpl extends Composite implements LoginView {
    private final Logger log = Logger.getLogger(LoginViewImpl.class.getName());
    private static LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);
    private LoginPresenter presenter;
    @UiField
    TextBox userNameTextBox;
    @UiField
    PasswordTextBox passwordTextBox;
    @UiField
    Anchor googleLoginLink;
    @UiField
    Label statusLabel;
    @UiField
    PushButton login;
    @UiField
    PushButton signUp;
    @UiField
    PushButton cancel;

    @Inject
    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        userNameTextBox.ensureDebugId("username");
        googleLoginLink.ensureDebugId("google-login");
        passwordTextBox.ensureDebugId("password");
        login.ensureDebugId("login");
        cancel.ensureDebugId("cancel");
        statusLabel.ensureDebugId("status");
        log.info("Created " + this);
    }

    @Override
    public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getUserNameField() {
        return userNameTextBox.getText();
    }

    @Override
    public String getPasswordField() {
        return passwordTextBox.getText();
    }

    @Override
    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    @Override
    public void setGoogleLoginHref(String href) {
        googleLoginLink.setHref(href);
    }

    @Override
    public void focusUserNameField() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            public void execute() {
                userNameTextBox.setFocus(true);
            }
        });
    }

    @Override
    public void focusPasswordField() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            public void execute() {
                passwordTextBox.setFocus(true);
            }
        });
    }

    @UiHandler("login")
    public void loginClicked(ClickEvent e) {
        presenter.login();
    }

    @UiHandler("signUp")
    public void signUpClicked(ClickEvent e) {
        presenter.signUp();
    }

    @UiHandler("cancel")
    public void onCancel(ClickEvent e) {
        presenter.cancel();
    }

    @UiHandler("userNameTextBox")
    public void onUserNameKey(KeyUpEvent e) {
        presenter.onUserFieldKeyUp(e.getNativeKeyCode());
    }

    @UiHandler("passwordTextBox")
    public void onPasswordKey(KeyUpEvent e) {
        presenter.onPasswordFieldKeyUp(e.getNativeKeyCode());
    }

    interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
    }
}
