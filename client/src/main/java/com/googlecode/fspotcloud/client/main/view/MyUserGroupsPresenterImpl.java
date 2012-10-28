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
import com.googlecode.fspotcloud.client.main.view.api.MyUserGroupsView;
import com.googlecode.fspotcloud.client.place.EditUserGroupPlace;
import com.googlecode.fspotcloud.client.place.ManageUsersPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.*;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Logger;


public class MyUserGroupsPresenterImpl extends AbstractActivity implements MyUserGroupsView.MyUserGroupsPresenter {
    private final Logger log = Logger.getLogger(MyUserGroupsPresenterImpl.class.getName());
    private final MyUserGroupsView view;
    private final DispatchAsync dispatch;
    private final PlaceGoTo placeGoTo;

    @Inject
    public MyUserGroupsPresenterImpl(MyUserGroupsView view,
                                     DispatchAsync dispatch, PlaceGoTo placeGoTo) {
        this.view = view;
        this.dispatch = dispatch;
        this.placeGoTo = placeGoTo;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.view.setPresenter(this);
        panel.setWidget(view);

        // view.setData(newArrayList(new UserGroupInfo("foo", "Uh foo")));
        refreshData();
    }

    private void refreshData() {
        dispatch.execute(new GetMyUserGroupsAction(),
                new AsyncCallback<GetMyUserGroupsResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(GetMyUserGroupsResult result) {
                        if (result.getData() != null) {
                            view.setData(result.getData());
                        }
                    }
                });
    }

    @Override
    public void newUserGroup() {
        dispatch.execute(new NewUserGroupAction(),
                new AsyncCallback<GetUserGroupResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(GetUserGroupResult result) {
                        log.info("New User group added");
                        refreshData();
                    }
                });
        log.info("New User group requested");
    }

    @Override
    public void delete() {
        UserGroupInfo info = view.getSelected();

        if (info != null) {
            log.info("Got selected for delete: " + info);
            dispatch.execute(new DeleteUserGroupAction(info.getId()),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            //To change body of implemented methods use File | Settings | File Templates.
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            log.info("Delete call returned from server");
                            refreshData();
                        }
                    });
        } else {
            log.info("Nothing selected");
        }
    }

    @Override
    public void manageUsers() {
        UserGroupInfo info = view.getSelected();

        if (info != null) {
            placeGoTo.goTo(new ManageUsersPlace(info.getId()));
        }
    }

    @Override
    public void edit() {
        UserGroupInfo info = view.getSelected();

        if (info != null) {
            log.info("Got selected: " + info);
            placeGoTo.goTo(new EditUserGroupPlace(info.getId()));
        } else {
            log.info("Nothing selected");
        }
    }
}
