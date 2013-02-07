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
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.client.main.gin.BasicTreeView;
import com.googlecode.fspotcloud.client.main.view.api.LoginView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.shared.main.AuthenticationAction;
import com.googlecode.fspotcloud.shared.main.AuthenticationResult;
import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.UserInfo;
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
    private final PlaceGoTo placeGoTo;
    private final PlaceWhere placeWhere;
    private final TreeView.TreePresenter treePresenter;
    private final ClientLoginManager clientLoginManager;


    @Inject
    public LoginPresenterImpl(LoginView loginView,
                              DispatchAsync dispatch,
                              PlaceGoTo placeGoTo,
                              PlaceWhere placeWhere,
                              @BasicTreeView TreeView.TreePresenter treePresenter,
                              ClientLoginManager clientLoginManager) {
        this.view = loginView;
        this.dispatch = dispatch;
        this.placeGoTo = placeGoTo;
        this.placeWhere = placeWhere;
        this.treePresenter = treePresenter;
        this.clientLoginManager = clientLoginManager;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.view.setPresenter(this);
        panel.setWidget(view);
        view.focusUserNameField();
        String nextUrl;
        nextUrl = getNextUrl();
        dispatch.execute(new GetUserInfo("post-login?next=" + nextUrl),
                new AsyncCallback<UserInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(UserInfo result) {
                        String loginUrl = result.getLoginUrl();
                        log.info("LoginURL: " + loginUrl);
                        view.setGoogleLoginHref(loginUrl);
                    }
                });
    }

    private String getNextUrl() {

        return ((LoginPlace) placeWhere.getRawWhere()).getNextUrl();
    }

    @Override
    public void onUserFieldKeyUp(int code) {
        log.info("Code: " + code);

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

    @Override
    public void login() {
        submitToServer();
    }

    @Override
    public void signUp() {
        placeGoTo.goTo(new SignUpPlace());
    }

    @Override
    public void resendConfirmation() {
        placeGoTo.goTo(new SendConfirmationPlace());
    }

    @Override
    public void passwordReset() {
        placeGoTo.goTo(new SendResetPasswordPlace());
    }

    @Override
    public void cancel() {
        placeGoTo.goTo(new BasePlace("latest", "latest"));
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
                        log.info("Server replied to auth requ: " + result.getSuccess());

                        if (result.getSuccess()) {
                            view.setStatusText(LOGGED_IN);
                            view.clearFields();
                            clientLoginManager.resetApplicationData();
                            String nextUrl = getNextUrl();
                            if (!nextUrl.equals("")) {
                                placeGoTo.goTo(nextUrl);
                            } else {
                                placeGoTo.goTo(new UserAccountPlace());
                            }
                            treePresenter.reloadTree();
                            log.info("Reached!!!!");
                        } else {
                            view.setStatusText(NOT_A_VALID_USERNAME_AND_PASSWORD_COMBINATION);
                        }
                    }
                });
    }
}
