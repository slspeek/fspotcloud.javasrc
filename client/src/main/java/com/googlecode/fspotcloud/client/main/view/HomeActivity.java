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
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.HomeView;
import com.googlecode.fspotcloud.client.place.api.Navigator;

import java.util.logging.Logger;

@GwtCompatible
public class HomeActivity extends AbstractActivity
		implements
			HomeView.HomePresenter {
	public static final String SERVER_ERROR = "Could not navigate from this page due to a server error: ";
	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(HomeActivity.class.getName());
	private final HomeView homeView;
	private final Navigator navigator;

	@Inject
	public HomeActivity(HomeView homeView, Navigator navigator) {
		this.homeView = homeView;
		this.navigator = navigator;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		containerWidget.setWidget(homeView);
		navigator.goToLatestTag(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				homeView.setStatusText(SERVER_ERROR
						+ caught.getLocalizedMessage());
			}

			@Override
			public void onSuccess(String result) {
				homeView.setStatusText(result);
			}
		});
	}
}
