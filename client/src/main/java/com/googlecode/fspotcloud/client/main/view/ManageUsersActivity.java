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
import com.googlecode.fspotcloud.client.enduseraction.group.GroupActions;
import com.googlecode.fspotcloud.client.main.gin.ManageUsers;
import com.googlecode.fspotcloud.client.main.view.api.ManageUsersView;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.GetUserGroupAction;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.GrantUserAction;
import com.googlecode.fspotcloud.shared.main.RevokeUserAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.Set;
import java.util.logging.Logger;

public class ManageUsersActivity extends AbstractActivity
		implements
			ManageUsersView.ManageUsersPresenter {
	private final Logger log = Logger.getLogger(ManageUsersActivity.class
			.getName());
	private final ManageUsersView view;
	private final StatusView statusView;
	private final DispatchAsync dispatch;
	private final GroupActions groupActions;
	private Long userGroupId;

	@Inject
	public ManageUsersActivity(ManageUsersView view,
			@ManageUsers StatusView statusView, DispatchAsync dispatch,
			GroupActions groupActions) {
		this.view = view;
		this.statusView = statusView;
		this.dispatch = dispatch;
		this.groupActions = groupActions;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view.setPresenter(this);
		panel.setWidget(view);
		refreshData();
	}

	private void addUser() {
		final String newEmail = view.getNewEmail();
		statusView.setStatusText("Requesting to add " + newEmail
				+ " to the group");
		dispatch.execute(new GrantUserAction(newEmail, userGroupId),
				new AsyncCallback<VoidResult>() {
					@Override
					public void onFailure(Throwable caught) {
						statusView.setStatusText("Adding " + newEmail
								+ " failed due to a server error");
					}

					@Override
					public void onSuccess(VoidResult result) {
						refreshData();
						view.clearEmail();
					}
				});
	}

	private void removeUser() {
		final String selectedRow = view.getSelected();

		if (selectedRow != null) {
			statusView.setStatusText("Requesting the server to remove "
					+ selectedRow);
			dispatch.execute(new RevokeUserAction(selectedRow, userGroupId),
					new AsyncCallback<VoidResult>() {
						@Override
						public void onFailure(Throwable caught) {
							statusView
									.setStatusText(selectedRow
											+ " could not be removed due to a server error");
						}

						@Override
						public void onSuccess(VoidResult result) {
							refreshData();
						}
					});
		} else {
			statusView
					.setStatusText("Please make a selection before attempting to remove an user from the group");
		}
	}

	@Override
	public void setId(Long id) {
		userGroupId = id;
		refreshData();
	}

	private void refreshData() {
		statusView.setStatusText("Retrieving users from the server");
		dispatch.execute(new GetUserGroupAction(userGroupId),
				new AsyncCallback<GetUserGroupResult>() {
					@Override
					public void onFailure(Throwable caught) {
						statusView.setStatusText("Data for group with id="
								+ userGroupId
								+ " could not be loaded due to a server error");
					}

					@Override
					public void onSuccess(GetUserGroupResult result) {
						final Set<String> grantedUsers = result.getInfo()
								.getGrantedUsers();
						int rowCount = grantedUsers.size();
						statusView
								.setStatusText("Loaded " + rowCount + " rows");
						view.setData(grantedUsers);
						view.setGroupName(result.getInfo().getName());
						if (rowCount == 0) {
							view.focusEmail();
						} else {
							view.setSelected(grantedUsers.iterator().next(),
									true);
						}
					}
				});
	}

	@Override
	public void performAction(String actionId) {
		if (groupActions.addUser.getId().equals(actionId)) {
			addUser();
		} else if (groupActions.removeUser.getId().equals(actionId)) {
			removeUser();
		} else if (groupActions.focusUserTable.getId().equals(actionId)) {
			view.focusUsers();
		} else if (groupActions.focusEmailField.getId().equals(actionId)) {
			view.focusEmail();
		}

	}
}
