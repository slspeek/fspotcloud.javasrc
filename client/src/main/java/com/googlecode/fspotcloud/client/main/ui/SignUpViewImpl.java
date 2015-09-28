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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.gin.BigButtonFactory;
import com.googlecode.fspotcloud.client.main.gin.BigToPhotos;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.client.main.view.api.SignUpView;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButton;

import java.util.logging.Logger;

public class SignUpViewImpl extends Composite implements SignUpView {
	private final Logger log = Logger.getLogger(SignUpViewImpl.class.getName());
	private static final SignUpViewImplUiBinder uiBinder = GWT
			.create(SignUpViewImplUiBinder.class);
	private final IScheduler scheduler;
	@UiField
	TextBox emailTextBox;
	@UiField
	PasswordTextBox passwordTextBox;
	@UiField
	PasswordTextBox passwordAgainTextBox;
	@UiField
	Label statusLabel;
	@UiField(provided = true)
	ActionButton signUp;
	@UiField(provided = true)
	ActionButton cancel;

	@Inject
	public SignUpViewImpl(IScheduler scheduler,
			@BigToPhotos ActionButton cancel, UserActions userActions,
			BigButtonFactory buttonFactory) {
		this.cancel = cancel;
		this.signUp = buttonFactory.getButton(userActions.doSignUp);
		this.scheduler = scheduler;
		initWidget(uiBinder.createAndBindUi(this));
		emailTextBox.ensureDebugId("email");
		passwordAgainTextBox.ensureDebugId("password-again");
		passwordTextBox.ensureDebugId("password");
		signUp.ensureDebugId("sign-up");
		cancel.ensureDebugId("cancel");
		statusLabel.ensureDebugId("status");
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

	@Override
	public void focusEmailField() {
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				emailTextBox.setFocus(true);
			}
		});
	}

	@Override
	public void clearFields() {
		passwordAgainTextBox.setText("");
		passwordTextBox.setText("");
		emailTextBox.setText("");
	}

	interface SignUpViewImplUiBinder extends UiBinder<Widget, SignUpViewImpl> {
	}
}
