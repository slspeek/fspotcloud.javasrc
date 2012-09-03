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
import com.googlecode.fspotcloud.client.main.view.api.ManageUsersView;
import com.googlecode.fspotcloud.client.place.MyUserGroupsPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.GetUserGroupAction;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.GrantUserAction;
import com.googlecode.fspotcloud.shared.main.RevokeUserAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Logger;


public class ManageUsersPresenterImpl extends AbstractActivity implements ManageUsersView.ManageUsersPresenter {
    private final Logger log = Logger.getLogger(ManageUsersPresenterImpl.class.getName());
    private final ManageUsersView view;
    private final DispatchAsync dispatch;
    private Long userGroupId;
    private final PlaceGoTo placeGoTo;

    @Inject
    public ManageUsersPresenterImpl(ManageUsersView view,
                                    DispatchAsync dispatch, PlaceGoTo placeGoTo) {
        this.view = view;
        this.dispatch = dispatch;
        this.placeGoTo = placeGoTo;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.view.setPresenter(this);
        panel.setWidget(view);
    }

    @Override
    public void newUser() {
        String newEmail = view.getNewEmail();
        dispatch.execute(new GrantUserAction(newEmail, userGroupId),
                new AsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        refreshData();
                    }
                });
    }

    @Override
    public void delete() {
        String selectedRow = view.getSelected();

        if (selectedRow != null) {
            dispatch.execute(new RevokeUserAction(selectedRow, userGroupId),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            //To change body of implemented methods use File | Settings | File Templates.
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            refreshData();
                        }
                    });
        }
    }

    @Override
    public void setId(Long id) {
        userGroupId = id;

        refreshData();
    }

    @Override
    public void myUsergroupsButton() {
        placeGoTo.goTo(new MyUserGroupsPlace());
    }

    private void refreshData() {
        dispatch.execute(new GetUserGroupAction(userGroupId),
                new AsyncCallback<GetUserGroupResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(GetUserGroupResult result) {
                        view.setData(result.getInfo().getGrantedUsers());
                        view.setUserGroupName(result.getInfo().getName());
                    }
                });
    }
}
