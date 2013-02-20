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
import com.googlecode.fspotcloud.client.main.view.api.ManageGroupsView;
import com.googlecode.fspotcloud.shared.main.GetMyUserGroupsAction;
import com.googlecode.fspotcloud.shared.main.GetMyUserGroupsResult;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Logger;

@GwtCompatible
public class ManageGroupsPresenterImpl extends AbstractActivity implements ManageGroupsView.ManageGroupsPresenter {
    private final Logger log = Logger.getLogger(ManageGroupsPresenterImpl.class.getName());
    private final ManageGroupsView view;
    private final DispatchAsync dispatch;

    @Inject
    public ManageGroupsPresenterImpl(ManageGroupsView view,
                                     DispatchAsync dispatch
    ) {
        this.view = view;
        this.dispatch = dispatch;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.view.setPresenter(this);
        panel.setWidget(view);
        refreshData();
    }

    @Override
    public void refreshData() {
        view.setStatusText("Requesting all groups from the server");
        dispatch.execute(new GetMyUserGroupsAction(),
                new AsyncCallback<GetMyUserGroupsResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        view.setStatusText("Could not retrieve groups due to a server error");
                    }

                    @Override
                    public void onSuccess(GetMyUserGroupsResult result) {
                        if (result.getData() != null) {
                            view.setData(result.getData());
                            view.setStatusText("Reloaded the table from server data");
                        }
                    }
                });
    }

    @Override
    public UserGroupInfo getSelected() {
        return view.getSelected();
    }
}
