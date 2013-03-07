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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.gin.BigButtonFactory;
import com.googlecode.fspotcloud.client.main.view.api.SendPasswordResetView;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;

import java.util.logging.Level;
import java.util.logging.Logger;


public class SendPasswordResetViewImpl extends Composite implements SendPasswordResetView {
    private final Logger log = Logger.getLogger(SendPasswordResetViewImpl.class.getName());
    private static final SendResetPasswordViewImplUiBinder uiBinder = GWT.create(SendResetPasswordViewImplUiBinder.class);

    @UiField
    TextBox emailTextBox;
    @UiField
    Label statusLabel;
    @UiField(provided = true)
    ActionButton send;
    @UiField(provided = true)
    ActionButton cancel;

    @Inject
    public SendPasswordResetViewImpl(DashboardActions dashboardActions,
                                     UserActions userActions,
                                     BigButtonFactory buttonFactory) {
        cancel = buttonFactory.getButton(dashboardActions.toPhotos);
        send = buttonFactory.getButton(userActions.doRequestPasswordReset);
        initWidget(uiBinder.createAndBindUi(this));
        cancel.ensureDebugId("cancel");
        statusLabel.ensureDebugId("status");
        emailTextBox.ensureDebugId("email");
        log.log(Level.FINE, "Created ");
    }

    @Override
    public String getEmailField() {
        return emailTextBox.getText();
    }

    @Override
    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    @Override
    public void clearEmailField() {
        emailTextBox.setText("");
    }


    interface SendResetPasswordViewImplUiBinder extends UiBinder<Widget, SendPasswordResetViewImpl> {
    }
}
