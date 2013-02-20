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

package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.AdminButtonFactory;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.client.main.view.api.TagApprovalView;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.client.useraction.group.GroupActions;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class TagApprovalViewImpl extends Composite implements TagApprovalView {
    private final Logger log = Logger.getLogger(TagApprovalViewImpl.class.getName());
    private static final TagApprovalViewImplUiBinder uiBinder = GWT.create(TagApprovalViewImplUiBinder.class);
    private final ListDataProvider<UserGroupInfo> grantedDataProvider;
    private final ListDataProvider<UserGroupInfo> revokedDataProvider;
    private final SingleSelectionModel<UserGroupInfo> grantedSelectionModel = new SingleSelectionModel<UserGroupInfo>();
    private final SingleSelectionModel<UserGroupInfo> revokedSelectionModel = new SingleSelectionModel<UserGroupInfo>();
    @UiField(provided = true)
    CellTable<UserGroupInfo> grantedTable;
    @UiField(provided = true)
    CellTable<UserGroupInfo> revokedTable;
    @UiField(provided = true)
    ActionButton revokeButton;
    @UiField(provided = true)
    ActionButton grantButton;
    @UiField(provided = true)
    ActionButton dashboardButton;
    @UiField(provided = true)
    ActionButton manageGroupsButton;
    @UiField(provided = true)
    StatusViewImpl statusView;
    @UiField
    Label tagName;

    @Inject
    public TagApprovalViewImpl(GroupActions groupActions,
                               ApplicationActions applicationActions,
                               DashboardActions dashboardActions,
                               AdminButtonFactory factory,
                               StatusView statusView,
                               CellTableResources cellTableResources

    ) {
        this.grantedTable = new CellTable<UserGroupInfo>(15, cellTableResources);
        this.revokedTable = new CellTable<UserGroupInfo>(15, cellTableResources);
        this.statusView = (StatusViewImpl) statusView;
        revokeButton = factory.getButton(groupActions.revokeGroup);
        grantButton = factory.getButton(groupActions.grantGroup);
        dashboardButton = factory.getButton(applicationActions.dashboard);
        manageGroupsButton = factory.getButton(dashboardActions.manageGroups);
        initWidget(uiBinder.createAndBindUi(this));
        tagName.ensureDebugId("tag-name");
        revokeButton.ensureDebugId("remove-button");
        grantButton.ensureDebugId("approve-button");

        TextColumn<UserGroupInfo> column;
        column = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getName();
            }
        };
        grantedTable.addColumn(column, "Name");
        column = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getDescription();
            }
        };
        grantedTable.addColumn(column, "Description");
        grantedDataProvider = new ListDataProvider<UserGroupInfo>();

        grantedDataProvider.addDataDisplay(grantedTable);
        grantedTable.setSelectionModel(grantedSelectionModel);
        column = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getName();
            }
        };
        revokedTable.addColumn(column, "Name");
        column = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getDescription();
            }
        };
        revokedTable.addColumn(column, "Description");
        revokedDataProvider = new ListDataProvider<UserGroupInfo>();

        revokedDataProvider.addDataDisplay(revokedTable);
        revokedTable.setSelectionModel(revokedSelectionModel);

        log.info("Created. ");
    }
    @Override
    public UserGroupInfo getApprovedSelected() {
        return grantedSelectionModel.getSelectedObject();
    }

    @Override
    public UserGroupInfo getOtherSelected() {
        return revokedSelectionModel.getSelectedObject();
    }

    @Override
    public void focusGrantedTable() {
        grantedTable.setFocus(true);
    }

    @Override
    public void focusRevokedTable() {
        revokedTable.setFocus(true);
    }

    @Override
    public void setStatusText(String status) {
        statusView.setStatusText(status);
    }

    @Override
    public void setApprovedGroups(Set<UserGroupInfo> approvedGroups) {
        setGroups(approvedGroups, grantedDataProvider);
    }

    @Override
    public void setOtherGroups(Set<UserGroupInfo> approvedGroups) {
        setGroups(approvedGroups, revokedDataProvider);
    }

    private void setGroups(Set<UserGroupInfo> approvedGroups,
                           ListDataProvider<UserGroupInfo> provider) {
        List<UserGroupInfo> list = provider.getList();
        list.clear();

        for (UserGroupInfo contact : approvedGroups) {
            list.add(contact);
        }
    }

    @Override
    public void setTagName(String name) {
        tagName.setText(name);
    }

    interface TagApprovalViewImplUiBinder extends UiBinder<Widget, TagApprovalViewImpl> {
    }
}
