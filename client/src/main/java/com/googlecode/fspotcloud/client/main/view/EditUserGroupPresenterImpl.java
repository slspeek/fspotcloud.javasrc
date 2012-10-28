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
import com.googlecode.fspotcloud.client.main.view.api.EditUserGroupView;
import com.googlecode.fspotcloud.client.place.MyUserGroupsPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.GetUserGroupAction;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.SaveUserGroupAction;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Logger;


public class EditUserGroupPresenterImpl extends AbstractActivity implements EditUserGroupView.EditUserGroupPresenter {
    private final Logger log = Logger.getLogger(EditUserGroupPresenterImpl.class.getName());
    private final EditUserGroupView view;
    private final DispatchAsync dispatch;
    private UserGroupInfo userGroupInfo;
    private final PlaceGoTo placeGoTo;

    @Inject
    public EditUserGroupPresenterImpl(EditUserGroupView view,
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
    public void save() {
        log.info("Saved called!");
        userGroupInfo.setName(view.getName());
        userGroupInfo.setDescription(view.getDescription());
        userGroupInfo.setPublic(view.getIsPublic());
        dispatch.execute(new SaveUserGroupAction(userGroupInfo),
                new AsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        log.info(
                                "Successfull return from save user group server call");
                        placeGoTo.goTo(new MyUserGroupsPlace());
                    }
                });
    }

    @Override
    public void setId(Long id) {
        log.info("Set id: " + id);
        dispatch.execute(new GetUserGroupAction(id),
                new AsyncCallback<GetUserGroupResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(GetUserGroupResult result) {
                        userGroupInfo = result.getInfo();
                        view.setName(result.getInfo().getName());
                        view.setDescription(result.getInfo().getDescription());
                        view.setIsPublic(result.getInfo().isPublic());
                    }
                });
    }

    @Override
    public void cancel() {
        placeGoTo.goTo(new MyUserGroupsPlace());
    }
}
