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
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.view.api.DashboardView;
import com.googlecode.fspotcloud.client.main.view.api.PeerActionsView;
import com.googlecode.fspotcloud.client.main.view.api.TagDetailsView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.DashboardPlace;
import com.googlecode.fspotcloud.client.place.HomePlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import java.util.logging.Logger;

public class DashboardActivity extends AbstractActivity
		implements
			DashboardView.DashboardPresenter {
	private final Logger log = Logger.getLogger(DashboardActivity.class
			.getName());
	private final DashboardView dashboardView;
	private final TreeView.TreePresenter treePresenter;
	private final PeerActionsView.PeerActionsPresenter peerActionsPresenter;
	private TagDetailsView.TagDetailsPresenter activity;
	private final IClientLoginManager clientLoginManager;
	private final IPlaceController placeController;

	@Inject
	public DashboardActivity(DashboardView dashboardView,
			@AdminTreeView TreeView.TreePresenter treePresenter,
			PeerActionsView.PeerActionsPresenter peerActionsPresenter,
			TagDetailsView.TagDetailsPresenter tagDetailsActivity,
			IClientLoginManager clientLoginManager,
			IPlaceController placeController) {
		this.dashboardView = dashboardView;
		this.treePresenter = treePresenter;
		this.peerActionsPresenter = peerActionsPresenter;
		this.activity = tagDetailsActivity;
		this.clientLoginManager = clientLoginManager;
		this.placeController = placeController;
	}

	@Override
	public void init() {
		clientLoginManager.getUserInfoAsync(new AsyncCallback<UserInfo>() {
			@Override
			public void onFailure(Throwable caught) {
				placeController.goTo(new HomePlace());
			}

			@Override
			public void onSuccess(UserInfo result) {
				if (result.isLoggedIn() && result.isAdmin()) {
					treePresenter.init();
					peerActionsPresenter.init();
				} else if (result.isLoggedIn()) {
					placeController.goTo(new HomePlace());
				} else {
					clientLoginManager.redirectToLogin();
				}
			}
		});
	}

	@Override
	public DashboardView.DashboardPresenter withPlace(DashboardPlace place) {
		((AdminTreePresenterImpl) treePresenter).setPlace(place);
		activity.init();
		return this;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		dashboardView.setPresenter(this);
		panel.setWidget(dashboardView);
		init();
	}

	@Override
	public void onStop() {
		peerActionsPresenter.stop();
		super.onStop();
	}
}
