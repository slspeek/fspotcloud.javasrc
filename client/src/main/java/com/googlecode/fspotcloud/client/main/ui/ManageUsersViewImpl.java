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
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.AdminButtonFactory;
import com.googlecode.fspotcloud.client.main.gin.ManageUsers;
import com.googlecode.fspotcloud.client.main.view.api.ManageUsersView;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.client.enduseraction.Modes;
import com.googlecode.fspotcloud.client.enduseraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.client.enduseraction.group.GroupActions;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.IModeController;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ManageUsersViewImpl extends Composite implements ManageUsersView {
    private final Logger log = Logger.getLogger(ManageUsersViewImpl.class.getName());
    private static final ManageUsersViewImplUiBinder uiBinder = GWT.create(ManageUsersViewImplUiBinder.class);
    private ManageUsersView.ManageUsersPresenter presenter;
    private final ListDataProvider<String> dataProvider;
    private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
    private final IModeController modeController;
    @UiField(provided = true)
    CellTable<String> table;
    @UiField(provided = true)
    ActionButton addButton;
    @UiField
    TextBox emailTextBox;
    @UiField(provided = true)
    ActionButton removeButton;
    @UiField(provided = true)
    ActionButton myUsergroupsButton;
    @UiField(provided = true)
    ActionButton dashboardButton;
    @UiField
    Label userGroupName;
    @UiField(provided = true)
    StatusViewImpl status;

    @Inject
    public ManageUsersViewImpl(AdminButtonFactory factory,
                               DashboardActions dashboardActions,
                               GroupActions groupActions,
                               ApplicationActions applicationActions,
                               @ManageUsers StatusView statusView,
                               CellTableResources resources,
                               IModeController modeController) {
        this.modeController = modeController;
        this.table = new CellTable<String>(15, resources);
        this.status = (StatusViewImpl) statusView;
        myUsergroupsButton = factory.getButton(dashboardActions.manageGroups);
        addButton = factory.getButton(groupActions.addUser);
        removeButton = factory.getButton(groupActions.removeUser);
        dashboardButton = factory.getButton(applicationActions.dashboard);
        initWidget(uiBinder.createAndBindUi(this));
        addButton.ensureDebugId("new-button");
        emailTextBox.ensureDebugId("email");
        removeButton.ensureDebugId("delete-button");

        TextColumn<String> nameColumn = new TextColumn<String>() {
            @Override
            public String getValue(String info) {
                return info;
            }
        };
        nameColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        table.addColumn(nameColumn, "Email");
        table.setWidth("100%");
        dataProvider = new ListDataProvider<String>();

        dataProvider.addDataDisplay(table);
        table.setSelectionModel(selectionModel);
        table.setPageSize(25);
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

    @UiHandler("emailTextBox")
    public void onFocus(FocusEvent e) {
        log.log(Level.FINEST, "email field focused");
        modeController.setMode(Modes.MANAGE_USERS);
    }

    @UiHandler("emailTextBox")
    public void onBlur(BlurEvent e) {
        log.log(Level.FINEST, "email field focus was lost");
        modeController.setMode(Modes.MANAGE_USERS_NO_INPUT);
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
    public void setGroupName(String name) {
        userGroupName.setText(name);
    }

    @Override
    public void clearEmail() {
        emailTextBox.setText("");
    }

    @Override
    public void focusEmail() {
        emailTextBox.setFocus(true);
    }

    @Override
    public void focusUsers() {
        table.setFocus(true);
    }

    @Override
    public void setSelected(String item, boolean state) {
        selectionModel.setSelected(item, state);
    }

    interface ManageUsersViewImplUiBinder extends UiBinder<Widget, ManageUsersViewImpl> {
    }
}
