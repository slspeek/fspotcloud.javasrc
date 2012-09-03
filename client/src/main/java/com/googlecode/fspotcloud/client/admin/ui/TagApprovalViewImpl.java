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

package com.googlecode.fspotcloud.client.admin.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.view.api.TagApprovalView;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class TagApprovalViewImpl extends Composite implements TagApprovalView {
    private final Logger log = Logger.getLogger(TagApprovalViewImpl.class.getName());
    private static TagApprovalViewImplUiBinder uiBinder = GWT.create(TagApprovalViewImplUiBinder.class);
    private TagApprovalPresenter presenter;
    private ListDataProvider<UserGroupInfo> approvedDataProvider;
    private ListDataProvider<UserGroupInfo> otherDataProvider;
    private SingleSelectionModel<UserGroupInfo> approvedSelectionModel = new SingleSelectionModel<UserGroupInfo>();
    private SingleSelectionModel<UserGroupInfo> otherSelectionModel = new SingleSelectionModel<UserGroupInfo>();
    @UiField
    CellTable<UserGroupInfo> approved;
    @UiField
    CellTable<UserGroupInfo> other;
    @UiField
    PushButton removeButton;
    @UiField
    PushButton approveButton;
    @UiField
    PushButton dashboardButton;
    @UiField
    Label tagName;

    @Inject
    public TagApprovalViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        tagName.ensureDebugId("tag-name");
        removeButton.ensureDebugId("remove-button");
        approveButton.ensureDebugId("approve-button");

        // Create name column.
        TextColumn<UserGroupInfo> column;
        column = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getName();
            }
        };
        approved.addColumn(column, "Name");
        // Create address column.
        column = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getDescription();
            }
        };
        // Add the columns.
        approved.addColumn(column, "Description");
        // Create a data provider.
        approvedDataProvider = new ListDataProvider<UserGroupInfo>();

        // Connect the table to the data provider.
        approvedDataProvider.addDataDisplay(approved);
        approved.setSelectionModel(approvedSelectionModel);
        column = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getName();
            }
        };
        other.addColumn(column, "Name");
        // Create address column.
        column = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getDescription();
            }
        };
        // Add the columns.
        other.addColumn(column, "Description");
        // Create a data provider.
        otherDataProvider = new ListDataProvider<UserGroupInfo>();

        // Connect the table to the data provider.
        otherDataProvider.addDataDisplay(other);
        other.setSelectionModel(otherSelectionModel);

        log.info("Created " + this);
    }

    @UiHandler("approveButton")
    public void approveButton(ClickEvent event) {
        presenter.approve();
    }

    @UiHandler("removeButton")
    public void removeButton(ClickEvent event) {
        presenter.remove();
    }

    @UiHandler("dashboardButton")
    public void dashboardButton(ClickEvent event) {
        presenter.dashboard();
    }

    @Override
    public void setPresenter(TagApprovalPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public UserGroupInfo getApprovedSelected() {
        return approvedSelectionModel.getSelectedObject();
    }

    @Override
    public UserGroupInfo getOtherSelected() {
        return otherSelectionModel.getSelectedObject();
    }

    @Override
    public void setApprovedGroups(Set<UserGroupInfo> approvedGroups) {
        setGroups(approvedGroups, approvedDataProvider);
    }

    @Override
    public void setOtherGroups(Set<UserGroupInfo> approvedGroups) {
        setGroups(approvedGroups, otherDataProvider);
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
