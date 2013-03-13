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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.client.main.view.api.EditUserGroupView;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionEvent;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.GetUserGroupAction;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.SaveUserGroupAction;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class EditUserGroupActivity extends AbstractActivity implements EditUserGroupView.EditUserGroupPresenter {
    private final Logger log = Logger.getLogger(EditUserGroupActivity.class.getName());
    private final EditUserGroupView view;
    private final DispatchAsync dispatch;
    private final EventBus eventBus;
    private final DashboardActions dashboardActions;
    private final IScheduler scheduler;
    private UserGroupInfo userGroupInfo;

    @Inject
    public EditUserGroupActivity(EditUserGroupView view,
                                 DispatchAsync dispatch,
                                 DashboardActions dashboardActions,
                                 EventBus eventBus,
                                 IScheduler scheduler) {
        this.view = view;
        this.dispatch = dispatch;
        this.eventBus = eventBus;
        this.dashboardActions = dashboardActions;
        this.scheduler = scheduler;
    }
    @Override
    public void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus) {
        panel.setWidget(view);
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                view.focusNameField();
            }
        });
    }

    private void save() {
        log.log(Level.FINE, "Saved called.");
        userGroupInfo.setName(view.getName());
        userGroupInfo.setDescription(view.getDescription());
        userGroupInfo.setPublic(view.getIsPublic());
        view.setStatusText("Sending data to the server in order to save");
        dispatch.execute(new SaveUserGroupAction(userGroupInfo),
                new AsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        view.setStatusText("Group could not be saved due to a server error");
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        view.setStatusText("A save was successfully performed on the server, redirecting");
                        log.info(
                                "Successfully returned from save user group server call");
                        eventBus.fireEvent(new KeyboardActionEvent(dashboardActions.manageGroups.getId()));
                    }
                });
    }

    @Override
    public void setId(final Long id) {
        log.log(Level.FINE, "Set id: " + id);
        view.setStatusText("Requesting data for group with id=" + id);
        dispatch.execute(new GetUserGroupAction(id),
                new AsyncCallback<GetUserGroupResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        view.setStatusText("Requesting data for group with id=" + id + " failed due to a server error.");
                    }

                    @Override
                    public void onSuccess(GetUserGroupResult result) {
                        view.setStatusText("Loaded group information");
                        userGroupInfo = result.getInfo();
                        view.setName(result.getInfo().getName());
                        view.setDescription(result.getInfo().getDescription());
                        view.setIsPublic(result.getInfo().isPublic());
                    }
                });
    }

    @Override
    public void performAction(String actionId) {
        save();
    }
}
