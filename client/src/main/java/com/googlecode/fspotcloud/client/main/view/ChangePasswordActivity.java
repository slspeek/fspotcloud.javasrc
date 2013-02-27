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

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.view.api.ChangePasswordView;
import com.googlecode.fspotcloud.client.place.ChangePasswordPlace;
import com.googlecode.fspotcloud.shared.main.ResetPasswordAction;
import com.googlecode.fspotcloud.shared.main.ResetPasswordResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class ChangePasswordActivity extends AbstractActivity implements ChangePasswordView.ChangePasswordPresenter {
    public static final String YOUR_PASSWORD_WAS_CHANGED = "Your password was changed";
    public static final String AN_ERROR_PROHIBITED_CHANGING_PASSWORDS = "An error occured, password was not changed.";
    private final Logger log = Logger.getLogger(ChangePasswordActivity.class.getName());
    private final ChangePasswordView view;
    private final UserActions userActions;
    private final DispatchAsync dispatchAsync;
    private ChangePasswordPlace place;

    @Inject
    public ChangePasswordActivity(ChangePasswordView view,
                                  UserActions userActions,
                                  DispatchAsync dispatchAsync) {
        this.view = view;
        this.userActions = userActions;
        this.dispatchAsync = dispatchAsync;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }

    private void updateAccount() {
        String password = verifyPasswords();
        if (password == null) {
            view.setStatusText("Passwords do not match");
        } else {
            ResetPasswordAction action = new ResetPasswordAction(place.getEmail(),
                    place.getSecret(), password);
            send(action);
        }
    }

    private void send(ResetPasswordAction action) {
        dispatchAsync.execute(action,
                new AsyncCallback<ResetPasswordResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        view.setStatusText(AN_ERROR_PROHIBITED_CHANGING_PASSWORDS);
                        log.log(Level.WARNING, "Reset password failed ", caught);
                    }

                    @Override
                    public void onSuccess(ResetPasswordResult result) {
                        switch (result.getCode()) {
                            case SUCCESS:
                                view.setStatusText(YOUR_PASSWORD_WAS_CHANGED);
                                break;
                            case NOT_REGISTERED:
                                view.setStatusText("Failed. Please register first.");
                                break;
                            case NOT_VERIFIED:
                                view.setStatusText("Failed. Please verify your account first.");
                                break;
                            case WRONG_CODE:
                                view.setStatusText("Failed; you had the wrong code, try it again.");
                                break;
                        }
                    }
                });
    }


    @Override
    public void onStop() {
        super.onStop();
        view.clearFields();
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
        if(userActions.doChangePassword.getId().equals(actionId)) {
            updateAccount();
        }
    }

    @Override
    public ChangePasswordView.ChangePasswordPresenter withPlace(ChangePasswordPlace place) {
        this.place = place;
        return this;
    }
}
