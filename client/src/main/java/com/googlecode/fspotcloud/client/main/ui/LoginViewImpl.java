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
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.client.main.view.api.LoginView;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.user.UserActions;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.ActionButtonFactory;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginViewImpl extends Composite implements LoginView {
    private final Logger log = Logger.getLogger(LoginViewImpl.class.getName());
    private static final LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);
    private final IScheduler scheduler;
    private LoginPresenter presenter;
    @UiField
    TextBox userNameTextBox;
    @UiField
    PasswordTextBox passwordTextBox;
    @UiField(provided = true)
    ActionButton otherLogin;
    @UiField
    Label statusLabel;
    @UiField(provided = true)
    ActionButton login;
    @UiField(provided = true)
    ActionButton signUp;
    @UiField(provided = true)
    ActionButton cancel;
    @UiField(provided = true)
    ActionButton resend;
    @UiField(provided = true)
    ActionButton passwordReset;

    @Inject
    public LoginViewImpl(IScheduler scheduler, UserActions actions,
                         ActionButtonFactory factory,
                         BigActionButtonResources resources,
                         ApplicationActions applicationActions) {
        this.scheduler = scheduler;
        log.log(Level.FINE, "bigactionbuttonresources.button " + resources.style().button() );
        factory.setButtonResources(resources);
        otherLogin = factory.getButton(actions.otherLogin);
        cancel = factory.getButton(applicationActions.goToLatest);
        signUp = factory.getButton(actions.goSignUp);
        resend = factory.getButton(actions.goResendConfirmation);
        passwordReset = factory.getButton(actions.goResetPassword);
        login = factory.getButton(actions.doLogin);

        initWidget(uiBinder.createAndBindUi(this));

        userNameTextBox.ensureDebugId("username");
        passwordTextBox.ensureDebugId("password");
        statusLabel.ensureDebugId("status");
        log.log(Level.FINE, "Created");
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
    public void focusUserNameField() {
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                userNameTextBox.setFocus(true);
            }
        });
    }

    @Override
    public void focusPasswordField() {
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                passwordTextBox.setFocus(true);
            }
        });

    }

    @Override
    public void clearFields() {
        userNameTextBox.setText("");
        passwordTextBox.setText("");
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
