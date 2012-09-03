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
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageUsersView;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class ManageUsersViewImpl extends Composite implements ManageUsersView {
    private final Logger log = Logger.getLogger(ManageUsersViewImpl.class.getName());
    private static ManageUsersViewImplUiBinder uiBinder = GWT.create(ManageUsersViewImplUiBinder.class);
    private ManageUsersView.ManageUsersPresenter presenter;
    private ListDataProvider<String> dataProvider;
    private SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
    @UiField
    CellTable<String> table;
    @UiField
    PushButton newButton;
    @UiField
    TextBox emailTextBox;
    @UiField
    PushButton deleteButton;
    @UiField
    PushButton myUsergroupsButton;
    @UiField
    Label userGroupName;

    @Inject
    public ManageUsersViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        newButton.ensureDebugId("new-button");
        emailTextBox.ensureDebugId("email");
        deleteButton.ensureDebugId("delete-button");

        // Create name column.
        TextColumn<String> nameColumn = new TextColumn<String>() {
            @Override
            public String getValue(String info) {
                return info;
            }
        };

        table.addColumn(nameColumn, "Email");
        // Create a data provider.
        dataProvider = new ListDataProvider<String>();

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(table);
        table.setSelectionModel(selectionModel);
        table.setPageSize(25);
    }

    @UiHandler("newButton")
    public void onNewButton(ClickEvent event) {
        presenter.newUser();
    }

    @UiHandler("deleteButton")
    public void onDeleteButton(ClickEvent event) {
        presenter.delete();
    }

    @UiHandler("myUsergroupsButton")
    public void onMyUsergroupsButton(ClickEvent event) {
        presenter.myUsergroupsButton();
    }

    @Override
    public void setPresenter(ManageUsersView.ManageUsersPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(Set<String> data) {
        List<String> list = dataProvider.getList();
        list.clear();

        for (String contact : data) {
            list.add(contact);
        }
    }

    @Override
    public String getSelected() {
        return selectionModel.getSelectedObject();
    }

    @Override
    public String getNewEmail() {
        return emailTextBox.getText();
    }

    @Override
    public void setUserGroupName(String name) {
        userGroupName.setText(name);
    }

    interface ManageUsersViewImplUiBinder extends UiBinder<Widget, ManageUsersViewImpl> {
    }
}
