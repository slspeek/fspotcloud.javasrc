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

package com.googlecode.fspotcloud.client.main.view;

import com.google.common.base.Objects;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.view.api.UserAccountView;
import com.googlecode.fspotcloud.shared.main.UpdateUserAction;
import com.googlecode.fspotcloud.shared.main.UpdateUserResult;
import com.googlecode.fspotcloud.shared.main.UserInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


public class UserAccountActivity extends AbstractActivity implements UserAccountView.UserAccountPresenter {
    private final Logger log = Logger.getLogger(UserAccountActivity.class.getName());
    private final UserAccountView view;
    private final IClientLoginManager clientLoginManager;
    private final DispatchAsync dispatch;
    private final UserActions userActions;

    private static final String YOUR_PASSWORD_WAS_CHANGED = "Your password was changed";

    @Inject
    public UserAccountActivity(UserAccountView view,
                               IClientLoginManager clientLoginManager,
                               DispatchAsync dispatch,
                               UserActions userActions) {
        this.view = view;
        this.clientLoginManager = clientLoginManager;
        this.dispatch = dispatch;
        this.userActions = userActions;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
        clientLoginManager.getUserInfoAsync(
                new AsyncCallback<UserInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                    }

                    @Override
                    public void onSuccess(UserInfo result) {
                        view.setEmail(result.getEmail());

                        String date = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_LONG)
                                .format(result.getLastLoginTime());
                        view.setLastLoginTime(date);
                    }
                });
    }


    private void updateAccount() {
        String oldPw = view.getOldPasswordField();
        String password = verifyPasswords();

        if (password == null) {
            view.setStatusText("Passwords do not match");
        } else {
            UpdateUserAction action = new UpdateUserAction(password, oldPw);
            send(action);
        }

    }

    private void send(UpdateUserAction action) {
        dispatch.execute(action,
                new AsyncCallback<UpdateUserResult>() {
                    public static final String AN_ERROR_PROHIBITED_CHANGING_PASSWORDS = "An error occured, password was not changed.";

                    @Override
                    public void onFailure(Throwable caught) {
                        view.setStatusText(AN_ERROR_PROHIBITED_CHANGING_PASSWORDS);
                        log.log(Level.WARNING, "Changing password failed ", caught);
                    }

                    @Override
                    public void onSuccess(UpdateUserResult result) {
                        if (result.getSuccess()) {
                            view.setStatusText(YOUR_PASSWORD_WAS_CHANGED);
                        }
                    }
                });
    }


    private String verifyPasswords() {
        String password = view.getPasswordField();

        if (Objects.equal(password, view.getPasswordAgainField())) {
            return password;
        } else {
            return null;
        }
    }

    @Override
    public void performAction(String actionId) {
        if (userActions.doChangePassword.getId().equals(actionId)) {
            updateAccount();
        }
    }
}
