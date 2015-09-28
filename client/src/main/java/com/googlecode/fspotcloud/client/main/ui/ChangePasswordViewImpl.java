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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.gin.BigButtonFactory;
import com.googlecode.fspotcloud.client.main.gin.BigToPhotos;
import com.googlecode.fspotcloud.client.main.view.api.ChangePasswordView;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButton;

import java.util.logging.Logger;

public class ChangePasswordViewImpl extends Composite
		implements
			ChangePasswordView {
	private final Logger log = Logger.getLogger(ChangePasswordViewImpl.class
			.getName());
	private static final ChangePasswordViewImplUiBinder uiBinder = GWT
			.create(ChangePasswordViewImplUiBinder.class);
	@UiField
	PasswordTextBox passwordTextBox;
	@UiField
	PasswordTextBox passwordAgainTextBox;
	@UiField
	Label statusLabel;
	@UiField(provided = true)
	ActionButton change;
	@UiField(provided = true)
	ActionButton cancel;

	@Inject
	public ChangePasswordViewImpl(BigButtonFactory buttonFactory,
			@BigToPhotos ActionButton cancel, UserActions userActions) {
		change = buttonFactory.getButton(userActions.doPasswordReset);
		this.cancel = cancel;
		initWidget(uiBinder.createAndBindUi(this));
		passwordAgainTextBox.ensureDebugId("password-again");
		passwordTextBox.ensureDebugId("password");
		statusLabel.ensureDebugId("status");
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
	public void clearFields() {
		passwordAgainTextBox.setText("");
		passwordTextBox.setText("");
	}

	interface ChangePasswordViewImplUiBinder
			extends
				UiBinder<Widget, ChangePasswordViewImpl> {
	}
}
