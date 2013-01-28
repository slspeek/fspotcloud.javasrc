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
import com.googlecode.fspotcloud.client.main.view.api.SendConfirmationView;
import com.googlecode.fspotcloud.client.main.view.api.SendResetPasswordView;
import com.googlecode.fspotcloud.client.place.SendResetPasswordPlace;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.SendConfirmationEmailAction;
import com.googlecode.fspotcloud.shared.main.SendPasswordResetAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Logger;


public class SendPasswordResetActivity extends AbstractActivity implements SendResetPasswordView.ResetPasswordPresenter {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(SendPasswordResetActivity.class.getName());
    private final SendResetPasswordView view;
    private final DispatchAsync dispatchAsync;
    private final Navigator navigator;


    @Inject
    public SendPasswordResetActivity(SendResetPasswordView view,
                                     DispatchAsync dispatchAsync,
                                     Navigator navigator) {
        this.view = view;
        this.dispatchAsync = dispatchAsync;
        this.navigator = navigator;
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        view.setPresenter(this);
        containerWidget.setWidget(view);
    }

    @Override
    public void resetPassword() {
        String email = view.getEmailField();
        SendPasswordResetAction action = new SendPasswordResetAction(email);
        dispatchAsync.execute(action, new AsyncCallback<VoidResult>() {
            @Override
            public void onFailure(Throwable caught) {
                view.setStatusText("Failed. Maybe you should sign-up first.");
            }

            @Override
            public void onSuccess(VoidResult result) {
                view.setStatusText("Success. Check your email.");
            }
        });
    }

    @Override
    public void cancel() {
        view.clearEmailField();
        navigator.goToLatestTag();
    }
}
