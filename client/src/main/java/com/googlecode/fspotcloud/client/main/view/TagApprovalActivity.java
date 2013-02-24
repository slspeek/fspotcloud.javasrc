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
import com.googlecode.fspotcloud.client.main.view.api.TagApprovalView;
import com.googlecode.fspotcloud.client.enduseraction.group.GroupActions;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.*;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.Set;
import java.util.logging.Logger;

import static com.google.common.collect.Sets.newHashSet;

public class TagApprovalActivity extends AbstractActivity implements TagApprovalView.TagApprovalPresenter {
    private final Logger log = Logger.getLogger(TagApprovalActivity.class.getName());
    private final TagApprovalView view;
    private final DispatchAsync dispatch;
    private final GroupActions groupActions;
    private Set<UserGroupInfo> allGroups;
    private TagNode tagNode;
    private String tagId;

    @Inject
    public TagApprovalActivity(TagApprovalView view,
                               DispatchAsync dispatch,
                               GroupActions groupActions) {
        this.view = view;
        this.dispatch = dispatch;
        this.groupActions = groupActions;
    }

    private Set<UserGroupInfo> getNotGrantedGroups(Set<UserGroupInfo> all,
                                                   Set<Long> approved) {
        Set<UserGroupInfo> result = newHashSet();
        for (UserGroupInfo info : all) {
            if (!approved.contains(info.getId())) {
                result.add(info);
            }
        }
        return result;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
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
                            allGroups = newHashSet(result.getData());
                            refreshTagNode(tagId);
                        }
                    }
                });
    }

    private void revokeGroup() {
        final UserGroupInfo info = view.getApprovedSelected();

        if (info != null) {
            view.setStatusText("Requesting the server to revoke access for group: " + info.getName());
            dispatch.execute(new RevokeTagAction(tagId, info.getId()),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            view.setStatusText("Could not revoke access for group: " + info.getName() +
                                    " due to a server error");
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            refreshData();
                        }
                    });
        }
    }

    private void grantGroup() {
        final UserGroupInfo info = view.getOtherSelected();
        if (info != null) {
            view.setStatusText("Requesting the server to grant access to group: "+ info.getName());
            dispatch.execute(new ApproveTagAction(tagId, info.getId()),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            view.setStatusText("Could not grant access to group: " + info.getName() +
                                    " due to a server error");
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            refreshData();
                        }
                    });
        }
    }

    @Override
    public void setTagId(String tagId) {
        this.tagId = tagId;
        refreshTagNode(tagId);
    }

    private void refreshTagNode(String tagId) {
        view.setStatusText("Loading information on category id=" + tagId);
        dispatch.execute(new GetTagNodeAction(tagId),
                new AsyncCallback<TagNodeResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        view.setStatusText("Loading information failed due to a server error");
                    }

                    @Override
                    public void onSuccess(TagNodeResult result) {
                        tagNode = result.getInfo();
                        view.setTagName(tagNode.getTagName());

                        final Set<UserGroupInfo> others = getNotGrantedGroups(allGroups,
                                tagNode.getApprovedUserGroups());
                        view.setOtherGroups(others);

                        Set<UserGroupInfo> copyOfAll = newHashSet(allGroups);
                        copyOfAll.removeAll(others);

                        Set<UserGroupInfo> approved = copyOfAll;
                        view.setApprovedGroups(approved);
                        view.setStatusText("Updated UI with server data");
                    }
                });
    }

    @Override
    public void performAction(String actionId) {
        if (groupActions.revokeGroup.getId().equals(actionId)) {
            revokeGroup();
        }   else if (groupActions.grantGroup.getId().equals(actionId)) {
            grantGroup();
        } else if (groupActions.focusGrantedTable.getId().equals(actionId)) {
            view.focusGrantedTable();
        } else if (groupActions.focusRevokedTable.getId().equals(actionId)) {
            view.focusRevokedTable();
        }


    }
}
