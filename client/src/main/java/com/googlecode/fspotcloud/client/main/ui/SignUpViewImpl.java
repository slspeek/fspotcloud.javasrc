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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.SignUpView;

import java.util.logging.Logger;


public class SignUpViewImpl extends Composite implements SignUpView {
    private final Logger log = Logger.getLogger(SignUpViewImpl.class.getName());
    private static SignUpViewImplUiBinder uiBinder = GWT.create(SignUpViewImplUiBinder.class);
    private SignUpPresenter presenter;
    @UiField
    TextBox emailTextBox;
    @UiField
    PasswordTextBox passwordTextBox;
    @UiField
    PasswordTextBox passwordAgainTextBox;
    @UiField
    Label statusLabel;
    @UiField
    PushButton signUp;
    @UiField
    PushButton cancel;

    @Inject
    public SignUpViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        emailTextBox.ensureDebugId("email");
        passwordAgainTextBox.ensureDebugId("password-again");
        passwordTextBox.ensureDebugId("password");
        signUp.ensureDebugId("sign-up");
        cancel.ensureDebugId("cancel");
        statusLabel.ensureDebugId("status");
    }

    @Override
    public void setPresenter(SignUpPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getEmailField() {
        return emailTextBox.getText();
    }

    @Override
    public String getPasswordField() {
        return passwordTextBox.getText();
    }

    @Override
    public String getPasswordAgainField() {
        return passwordAgainTextBox.getText();
    }

    @Override
    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    @UiHandler("signUp")
    public void onPush(ClickEvent e) {
        presenter.signUp();
    }

    @UiHandler("cancel")
    public void onCancel(ClickEvent e) {
        presenter.cancel();
    }

    @Override
    public void focusEmailField() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            public void execute() {
                emailTextBox.setFocus(true);
            }
        });
    }

    interface SignUpViewImplUiBinder extends UiBinder<Widget, SignUpViewImpl> {
    }
}
