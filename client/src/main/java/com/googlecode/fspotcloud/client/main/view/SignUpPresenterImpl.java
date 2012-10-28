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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.SignUpView;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.main.SignUpAction;
import com.googlecode.fspotcloud.shared.main.SignUpResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


public class SignUpPresenterImpl extends AbstractActivity implements SignUpView.SignUpPresenter {
    private final Logger log = Logger.getLogger(SignUpPresenterImpl.class.getName());
    public static final String AN_ERROR_PROHIBITED_YOUR_SIGN_UP = "An error prohibited your sign-up.";
    public static final String SIGNED_UP_SUCCESSFULLY = "Signed up successfully, please check your email for the account-confirmation mail.";
    public static final String SIGN_UP_FAILED = "Sign up failed.";
    private final SignUpView view;
    private final DispatchAsync dispatch;
    private final PlaceGoTo placeGoTo;

    @Inject
    public SignUpPresenterImpl(SignUpView view, DispatchAsync dispatch,
                               PlaceGoTo placeGoTo) {
        this.view = view;
        this.dispatch = dispatch;
        this.placeGoTo = placeGoTo;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.view.setPresenter(this);
        panel.setWidget(view);
        view.focusEmailField();
    }

    @Override
    public void signUp() {
        String password = verifyPasswords();

        if (password == null) {
            view.setStatusText("Passwords do not match");
        } else {
            String email = view.getEmailField();
            String nickname = "";
            SignUpAction action = new SignUpAction(email, password, nickname);
            send(action);
        }

        log.info("Sign-up!");
    }

    @Override
    public void cancel() {
        placeGoTo.goTo(new BasePlace("latest", "latest"));
    }

    private void send(SignUpAction action) {
        dispatch.execute(action,
                new AsyncCallback<SignUpResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        view.setStatusText(AN_ERROR_PROHIBITED_YOUR_SIGN_UP);
                        log.log(Level.WARNING, "SignUp failed ", caught);
                    }

                    @Override
                    public void onSuccess(SignUpResult result) {
                        if (result.getSuccess()) {
                            view.setStatusText(SIGNED_UP_SUCCESSFULLY);
                        } else {
                            view.setStatusText(SIGN_UP_FAILED);
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
}
