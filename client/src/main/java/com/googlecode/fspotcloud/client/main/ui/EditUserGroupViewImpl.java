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
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.client.enduseraction.group.GroupActions;
import com.googlecode.fspotcloud.client.main.gin.AdminButtonFactory;
import com.googlecode.fspotcloud.client.main.view.api.EditUserGroupView;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;

import java.util.logging.Logger;


public class EditUserGroupViewImpl extends Composite
        implements EditUserGroupView {
    private final Logger log = Logger.getLogger(EditUserGroupViewImpl.class.getName());
    private static final EditUserGroupBinder uiBinder = GWT.create(EditUserGroupBinder.class);
    @UiField
    TextBox nameTextBox;
    @UiField
    TextBox descriptionTextBox;
    @UiField
    Label statusLabel;
    @UiField(provided = true)
    ActionButton save;
    @UiField(provided = true)
    ActionButton cancel;
    @UiField
    CheckBox publicCheckBox;

    @Inject
    public EditUserGroupViewImpl(AdminButtonFactory factory,
                                 GroupActions groupActions,
                                 DashboardActions dashboardActions) {
        save = factory.getButton(groupActions.saveGroup);
        cancel = factory.getButton(dashboardActions.manageGroups);
        initWidget(uiBinder.createAndBindUi(this));
        nameTextBox.ensureDebugId("name");
        descriptionTextBox.ensureDebugId("description");
        publicCheckBox.ensureDebugId("public-checkbox");
        save.ensureDebugId("save");
        log.info("Created ");
    }


    @Override
    public void setName(String name) {
        nameTextBox.setText(name);
    }

    @Override
    public String getName() {
        return nameTextBox.getText();
    }

    @Override
    public void setDescription(String description) {
        descriptionTextBox.setText(description);
    }

    @Override
    public String getDescription() {
        return descriptionTextBox.getText();
    }

    @Override
    public void setIsPublic(boolean isPublic) {
        publicCheckBox.setValue(isPublic);
    }

    @Override
    public boolean getIsPublic() {
        return publicCheckBox.getValue();
    }

    @Override
    public void focusNameField() {
        nameTextBox.setFocus(true);
    }

    @Override
    public void setStatusText(String status) {
        statusLabel.setText(status);
    }

    interface EditUserGroupBinder extends UiBinder<Widget, EditUserGroupViewImpl> {
    }
}
