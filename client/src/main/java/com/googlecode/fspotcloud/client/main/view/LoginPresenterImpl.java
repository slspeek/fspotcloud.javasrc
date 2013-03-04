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

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.view.api.LoginView;
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.UserAccountPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionEvent;
import com.googlecode.fspotcloud.shared.main.AuthenticationAction;
import com.googlecode.fspotcloud.shared.main.AuthenticationResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginPresenterImpl extends AbstractActivity implements LoginView.LoginPresenter {
    private final Logger log = Logger.getLogger(LoginPresenterImpl.class.getName());
    public static final String AN_ERROR_OCCURRED_MAKING_THE_AUTHENTICATION_REQUEST =
            "An error occurred making the authentication request";
    public static final String LOGGED_IN = "Logged in";
    public static final String NOT_A_VALID_USERNAME_AND_PASSWORD_COMBINATION = "Not a valid username and password combination";
    private final LoginView view;
    private final DispatchAsync dispatch;
    private final IPlaceController placeController;
    private final IClientLoginManager clientLoginManager;
    private final EventBus eventBus;
    private final ApplicationActions applicationActions;


    @Inject
    public LoginPresenterImpl(LoginView loginView,
                              DispatchAsync dispatch,
                              IPlaceController placeController,
                              IClientLoginManager clientLoginManager,
                              EventBus eventBus,
                              ApplicationActions applicationActions) {
        this.view = loginView;
        this.dispatch = dispatch;
        this.placeController = placeController;
        this.clientLoginManager = clientLoginManager;
        this.eventBus = eventBus;
        this.applicationActions = applicationActions;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.view.setPresenter(this);
        panel.setWidget(view);
        view.focusUserNameField();
    }

    private String getNextUrl() {
        return ((LoginPlace) placeController.getRawWhere()).getNextUrl();
    }

    @Override
    public void onUserFieldKeyUp(int code) {
        log.log(Level.FINE, "Code: " + code);
        if (code == 13) {
            view.focusPasswordField();
        }
    }

    @Override
    public void onPasswordFieldKeyUp(int code) {
        if (code == 13) {
            submitToServer();
        }
    }

    private void submitToServer() {
        String userName = view.getUserNameField();
        String password = view.getPasswordField();
        AuthenticationAction auth = new AuthenticationAction(userName, password);
        dispatch.execute(auth,
                new AsyncCallback<AuthenticationResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.WARNING, "Auth request could not be made",
                                caught);
                        view.setStatusText(AN_ERROR_OCCURRED_MAKING_THE_AUTHENTICATION_REQUEST);
                    }

                    @Override
                    public void onSuccess(AuthenticationResult result) {
                        log.log(Level.FINE, "Server replied to auth request: " + result.getSuccess());

                        if (result.getSuccess()) {
                            view.setStatusText(LOGGED_IN);
                            view.clearFields();
                            clientLoginManager.resetApplicationData();
                            String nextUrl = getNextUrl();
                            if (!nextUrl.equals("")) {
                                placeController.goTo(nextUrl);
                            } else {
                                placeController.goTo(new UserAccountPlace());
                            }
                            eventBus.fireEvent(new KeyboardActionEvent(applicationActions.reloadTree.getId()));
                            log.log(Level.FINE, "Reached end of login");
                        } else {
                            view.setStatusText(NOT_A_VALID_USERNAME_AND_PASSWORD_COMBINATION);
                        }
                    }
                });
    }

    @Override
    public void performAction(String actionId) {
        submitToServer();
    }
}
