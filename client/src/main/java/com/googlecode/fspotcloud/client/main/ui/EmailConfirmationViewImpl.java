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
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.EmailConfirmationToolbar;
import com.googlecode.fspotcloud.client.main.view.api.EmailConfirmationView;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;

import java.util.logging.Logger;


public class EmailConfirmationViewImpl extends Composite implements EmailConfirmationView {
    private final Logger log = Logger.getLogger(EmailConfirmationViewImpl.class.getName());

    interface EmailConfirmationViewImplUiBinder extends UiBinder<Widget, EmailConfirmationViewImpl> {
    }

    private static final EmailConfirmationViewImplUiBinder uiBinder = GWT.create(EmailConfirmationViewImplUiBinder.class);

    private final ActionToolbar actionToolbar;

    @UiField
    Label statusLabel;

    @Inject
    public EmailConfirmationViewImpl(@EmailConfirmationToolbar ActionToolbar actionToolbar) {
        this.actionToolbar = actionToolbar;
        initWidget(uiBinder.createAndBindUi(this));
        statusLabel.ensureDebugId("status");
    }

    @UiFactory
    ActionToolbar getToolbar() {
        return actionToolbar;
    }

    @Override
    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

}
