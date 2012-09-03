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
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.EditUserGroupView;

import java.util.logging.Logger;


public class EditUserGroupViewImpl extends Composite
        implements EditUserGroupView {
    private final Logger log = Logger.getLogger(EditUserGroupViewImpl.class.getName());
    private static EditUserGroupBinder uiBinder = GWT.create(EditUserGroupBinder.class);
    private EditUserGroupPresenter presenter;
    @UiField
    TextBox nameTextBox;
    @UiField
    TextBox descriptionTextBox;
    @UiField
    Label statusLabel;
    @UiField
    PushButton save;
    @UiField
    PushButton cancel;
    @UiField
    CheckBox publicCheckBox;

    @Inject
    public EditUserGroupViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        nameTextBox.ensureDebugId("name");
        descriptionTextBox.ensureDebugId("description");
        publicCheckBox.ensureDebugId("public-checkbox");
        save.ensureDebugId("save");
        log.info("Created " + this);
    }

    @Override
    public void setPresenter(EditUserGroupPresenter presenter) {
        this.presenter = presenter;
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

    @UiHandler("save")
    public void onSaveClicked(ClickEvent e) {
        presenter.save();
    }

    @UiHandler("cancel")
    public void onCancelClicked(ClickEvent e) {
        presenter.cancel();
    }

    interface EditUserGroupBinder extends UiBinder<Widget, EditUserGroupViewImpl> {
    }
}
