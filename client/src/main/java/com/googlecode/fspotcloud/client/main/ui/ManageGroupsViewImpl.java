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

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.enduseraction.group.GroupActions;
import com.googlecode.fspotcloud.client.main.gin.AdminButtonFactory;
import com.googlecode.fspotcloud.client.main.gin.ManageGroups;
import com.googlecode.fspotcloud.client.main.view.api.ManageGroupsView;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;

import java.util.List;
import java.util.logging.Logger;

@GwtCompatible
public class ManageGroupsViewImpl extends Composite implements ManageGroupsView {
    private final Logger log = Logger.getLogger(ManageGroupsViewImpl.class.getName());
    private static final ManageGroupsViewImplUiBinder uiBinder = GWT.create(ManageGroupsViewImplUiBinder.class);
    private ManageGroupsPresenter presenter;
    private final ListDataProvider<UserGroupInfo> dataProvider;
    private final SingleSelectionModel<UserGroupInfo> selectionModel = new SingleSelectionModel<UserGroupInfo>();

    @UiField(provided = true)
    CellTable<UserGroupInfo> table;
    @UiField(provided = true)
    ActionButton newButton;
    @UiField(provided = true)
    ActionButton editButton;
    @UiField(provided = true)
    ActionButton deleteButton;
    @UiField(provided = true)
    ActionButton manageButton;
    @UiField(provided = true)
    ActionButton dashboardButton;
    @UiField(provided = true)
    StatusViewImpl statusView;


    @Inject
    public ManageGroupsViewImpl(@ManageGroups StatusView statusView,
                                GroupActions actions,
                                ApplicationActions applicationActions,
                                AdminButtonFactory buttonFactory,
                                CellTableResources cellTableResources
    ) {
        this.statusView = (StatusViewImpl) statusView;
        this.table = new CellTable<UserGroupInfo>(15, cellTableResources);

        newButton = buttonFactory.getButton(actions.newUsergroup);
        editButton = buttonFactory.getButton(actions.editUsergroup);
        deleteButton = buttonFactory.getButton(actions.deleteUsergroup);
        manageButton = buttonFactory.getButton(actions.manageUsers);
        dashboardButton = buttonFactory.getButton(applicationActions.dashboard);
        initWidget(uiBinder.createAndBindUi(this));
        newButton.ensureDebugId("new-button");
        editButton.ensureDebugId("edit-button");
        deleteButton.ensureDebugId("delete-button");
        manageButton.ensureDebugId("manage-button");

        // Create name column.
        TextColumn<UserGroupInfo> nameColumn = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getName();
            }
        };
        nameColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        // Create address column.
        TextColumn<UserGroupInfo> descColumn = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo info) {
                return info.getDescription();
            }
        };
        descColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        TextColumn<UserGroupInfo> publicColumn = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo object) {
                return object.isPublic() ? "yes" : "no";
            }
        };
        publicColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        // Add the columns.
        table.addColumn(nameColumn, "Name");
        table.addColumn(descColumn, "Description");
        table.addColumn(publicColumn, "Public");
        // Create a data provider.
        dataProvider = new ListDataProvider<UserGroupInfo>();

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(table);
        table.setSelectionModel(selectionModel);
        table.setPageSize(25);
        table.setWidth("100%");
    }

    @Override
    public void setPresenter(ManageGroupsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(List<UserGroupInfo> data) {
        List<UserGroupInfo> list = dataProvider.getList();
        list.clear();
        for (UserGroupInfo contact : data) {
            list.add(contact);
        }
    }

    @Override
    public UserGroupInfo getSelected() {
        return selectionModel.getSelectedObject();
    }

    @Override
    public void focusTable() {
        table.setFocus(true);
    }

    @Override
    public void setSelected(UserGroupInfo userGroupInfo) {
        selectionModel.setSelected(userGroupInfo, true);
    }

    @Override
    public void setStatusText(String status) {
        statusView.setStatusText(status);
    }

    interface ManageGroupsViewImplUiBinder extends UiBinder<Widget, ManageGroupsViewImpl> {
    }
}
