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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.EmailConfirmationToolbar;
import com.googlecode.fspotcloud.client.main.view.api.EmailConfirmationView;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionToolbar;

import java.util.logging.Logger;

public class ConfirmationDialogBox extends DialogBox {
	private final Logger log = Logger.getLogger(ConfirmationDialogBox.class
			.getName());
	private Runnable confirmAction;

	interface ConfirmationDialogBoxUiBinder
			extends
				UiBinder<Widget, ConfirmationDialogBox> {
	}

	private static final ConfirmationDialogBoxUiBinder uiBinder = GWT
			.create(ConfirmationDialogBoxUiBinder.class);

	@UiField
	Button okButton;

	@UiField
	Button cancelButton;

	@Inject
	public ConfirmationDialogBox() {
		setWidget(uiBinder.createAndBindUi(this));
		okButton.ensureDebugId("confirm-ok");
		cancelButton.ensureDebugId("confirm-cancel");
	}

	@UiHandler("cancelButton")
	public void onCancel(ClickEvent e) {
		hide();
	}

	public void setConfirmAction(Runnable confirmAction) {
		this.confirmAction = confirmAction;
	}

	@UiHandler("okButton")
	public void onOkey(ClickEvent e) {
		hide();
		confirmAction.run();
	}
}
