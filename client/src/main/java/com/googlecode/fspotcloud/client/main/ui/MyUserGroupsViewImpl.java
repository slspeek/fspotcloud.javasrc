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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageUserGroupsView;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;

import java.util.List;
import java.util.logging.Logger;


public class MyUserGroupsViewImpl extends Composite implements ManageUserGroupsView {
    private final Logger log = Logger.getLogger(MyUserGroupsViewImpl.class.getName());
    private static final MyUserGroupsViewImplUiBinder uiBinder = GWT.create(MyUserGroupsViewImplUiBinder.class);
    private MyUserGroupsPresenter presenter;
    private final ListDataProvider<UserGroupInfo> dataProvider;
    private final SingleSelectionModel<UserGroupInfo> selectionModel = new SingleSelectionModel<UserGroupInfo>();
    private final PlaceGoTo placeGoTo;
    @UiField
    CellTable<UserGroupInfo> table;
    @UiField
    PushButtonExt newButton;
    @UiField
    PushButtonExt editButton;
    @UiField
    PushButtonExt deleteButton;
    @UiField
    PushButtonExt manageButton;
    @UiField
    PushButtonExt dashboardButton;

    @Inject
    public MyUserGroupsViewImpl(PlaceGoTo placeGoTo) {
        this.placeGoTo = placeGoTo;
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

    @UiHandler("newButton")
    public void onNewButton(ClickEvent event) {
        presenter.newUserGroup();
    }

    @UiHandler("editButton")
    public void onEditButton(ClickEvent event) {
        presenter.edit();
    }

    @UiHandler("deleteButton")
    public void onDeleteButton(ClickEvent event) {
        presenter.delete();
    }

    @UiHandler("manageButton")
    public void onManageButton(ClickEvent event) {
        presenter.manageUsers();
    }

    @UiHandler("dashboardButton")
    public void onDashboardButton(ClickEvent event) {
        placeGoTo.goTo(TagPlace.DEFAULT);
    }

    @Override
    public void setPresenter(MyUserGroupsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(List<UserGroupInfo> data) {
        // Add the data to the data provider, which automatically pushes it to the
        // widget.
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

    interface MyUserGroupsViewImplUiBinder extends UiBinder<Widget, MyUserGroupsViewImpl> {
    }
}
