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
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.UserAccountView;

import java.util.logging.Logger;


public class UserAccountViewImpl extends Composite implements UserAccountView {
    private final Logger log = Logger.getLogger(UserAccountViewImpl.class.getName());
    private static UserAccountViewImplUiBinder uiBinder = GWT.create(UserAccountViewImplUiBinder.class);
    private UserAccountView.UserAccountPresenter presenter;
    @UiField
    Label emailValueLabel;
    @UiField
    Label nicknameValueLabel;
    @UiField
    Label lastLoginValueLabel;

    @Inject
    public UserAccountViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        emailValueLabel.ensureDebugId("email");
        nicknameValueLabel.ensureDebugId("nickname");
        lastLoginValueLabel.ensureDebugId("last-login");
        log.info("Created " + this);
    }

    @Override
    public void setEmail(String email) {
        emailValueLabel.setText(email);
    }

    @Override
    public void setLastLoginTime(String date) {
        lastLoginValueLabel.setText(date);
    }

    @Override
    public void setNickname(String nickname) {
        nicknameValueLabel.setText(nickname);
    }

    @Override
    public void setPresenter(UserAccountView.UserAccountPresenter presenter) {
        this.presenter = presenter;
    }

    interface UserAccountViewImplUiBinder extends UiBinder<Widget, UserAccountViewImpl> {
    }
}
