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

package com.googlecode.fspotcloud.client.admin.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.view.api.TagApprovalView;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.*;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.Set;
import java.util.logging.Logger;

import static com.google.common.collect.Sets.newHashSet;

public class TagApprovalPresenterImpl extends AbstractActivity implements TagApprovalView.TagApprovalPresenter {
    private final Logger log = Logger.getLogger(TagApprovalPresenterImpl.class.getName());
    private final TagApprovalView view;
    private final DispatchAsync dispatch;
    private final PlaceGoTo placeGoTo;
    private Set<UserGroupInfo> allGroups;
    private TagNode tagNode;
    private String tagId;

    @Inject
    public TagApprovalPresenterImpl(TagApprovalView view,
                                    DispatchAsync dispatch, PlaceGoTo placeGoTo) {
        this.view = view;
        this.dispatch = dispatch;
        this.placeGoTo = placeGoTo;
    }

    private Set<UserGroupInfo> getOthers(Set<UserGroupInfo> all,
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
                            allGroups = newHashSet(result.getData());
                            refreshTagNode(tagId);
                        }
                    }
                });
    }

    @Override
    public void remove() {
        UserGroupInfo info = view.getApprovedSelected();

        if (info != null) {
            dispatch.execute(new RevokeTagAction(tagId, info.getId()),
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
    public void approve() {
        UserGroupInfo info = view.getOtherSelected();

        if (info != null) {
            dispatch.execute(new ApproveTagAction(tagId, info.getId()),
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
    public void setTagId(String tagId) {
        this.tagId = tagId;
        refreshTagNode(tagId);
    }

    @Override
    public void dashboard() {
        placeGoTo.goTo(new TagPlace(tagId));
    }

    private void refreshTagNode(String tagId) {
        dispatch.execute(new GetTagNodeAction(tagId),
                new AsyncCallback<TagNodeResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(TagNodeResult result) {
                        tagNode = result.getInfo();
                        view.setTagName(tagNode.getTagName());

                        final Set<UserGroupInfo> others = getOthers(allGroups,
                                tagNode.getApprovedUserGroups());
                        view.setOtherGroups(others);

                        Set<UserGroupInfo> copyOfAll = newHashSet(allGroups);
                        copyOfAll.removeAll(others);

                        Set<UserGroupInfo> approved = copyOfAll;
                        view.setApprovedGroups(approved);
                    }
                });
    }
}
