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
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.view.api.SendPasswordResetView;
import com.googlecode.fspotcloud.shared.main.SendPasswordResetAction;
import com.googlecode.fspotcloud.shared.main.SendPasswordResetResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Logger;

public class SendPasswordResetActivity extends AbstractActivity
		implements
			SendPasswordResetView.SendPasswordResetPresenter {
	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(SendPasswordResetActivity.class
			.getName());
	private final SendPasswordResetView view;
	private final DispatchAsync dispatchAsync;
	private final UserActions userActions;

	@Inject
	public SendPasswordResetActivity(SendPasswordResetView view,
			DispatchAsync dispatchAsync, UserActions userActions) {
		this.view = view;
		this.dispatchAsync = dispatchAsync;
		this.userActions = userActions;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		containerWidget.setWidget(view);
	}

	private void resetPassword() {
		String email = view.getEmailField();
		SendPasswordResetAction action = new SendPasswordResetAction(email);
		dispatchAsync.execute(action,
				new AsyncCallback<SendPasswordResetResult>() {
					@Override
					public void onFailure(Throwable caught) {
						view.setStatusText("Failed. Maybe you should sign-up first.");
					}

					@Override
					public void onSuccess(SendPasswordResetResult result) {
						switch (result.getCode()) {
							case SUCCESS :
								view.setStatusText("Success. Check your email.");
								break;
							case NOT_VERIFIED :
								view.setStatusText("Failed. Please verify your account first.");
								break;
							case NOT_REGISTERED :
								view.setStatusText("Failed. Please register first.");
								break;

						}
					}
				});
	}

	@Override
	public void onStop() {
		super.onStop();
		view.clearEmailField();
	}

	@Override
	public void performAction(String actionId) {
		if (userActions.doRequestPasswordReset.getId().equals(actionId)) {
			resetPassword();
		}
	}
}
