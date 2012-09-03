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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.googlecode.fspotcloud.client.admin.view.api.GlobalActionsView;


public class GlobalActionsViewImpl extends Composite
        implements GlobalActionsView {
    private static GlobalActionsViewImplUiBinder uiBinder = GWT.create(GlobalActionsViewImplUiBinder.class);
    private GlobalActionsPresenter presenter;
    @UiField
    Label peerPhotoCountValueLabel;
    @UiField
    Label lastSeenPeerValueLabel;
    @UiField
    Label tagCountOnPeerValueLabel;
    @UiField
    Label pendingCommandCountValueLabel;
    @UiField
    Button updateButton;
    @UiField
    Button deleteAllTagsButton;
    @UiField
    Button deleteAllCommandsButton;

    public GlobalActionsViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        deleteAllTagsButton.ensureDebugId("delete-all-tags-button");
        tagCountOnPeerValueLabel.ensureDebugId("tag-count-on-peer-label");
        peerPhotoCountValueLabel.ensureDebugId("peer-photo-count-label");
        updateButton.ensureDebugId("update-button");
        deleteAllCommandsButton.ensureDebugId("delete-all-photos-button");
        pendingCommandCountValueLabel.ensureDebugId(
                "pending-command-count-label");
    }

    @UiHandler("deleteAllCommandsButton")
    public void onDeleteAllPhotosButtonClicked(ClickEvent event) {
        presenter.deleteAllCommands();
    }

    @UiHandler("updateButton")
    public void updateButtonClicked(ClickEvent event) {
        presenter.update();
    }

    @UiHandler("deleteAllTagsButton")
    public void deleteAllTagsButtonClicked(ClickEvent event) {
        presenter.deleteAllTags();
    }

    @Override
    public HasText getPhotoCountOnPeerValue() {
        return peerPhotoCountValueLabel;
    }

    @Override
    public HasEnabled getDeleteAllTagsButton() {
        return deleteAllTagsButton;
    }

    @Override
    public HasEnabled getDeleteAllCommandsButton() {
        return deleteAllCommandsButton;
    }

    @Override
    public HasEnabled getUpdateButton() {
        return updateButton;
    }

    @Override
    public boolean confirm(String message) {
        return Window.confirm(message);
    }

    @Override
    public HasText getLastSeenPeerValue() {
        return lastSeenPeerValueLabel;
    }

    @Override
    public void setPresenter(GlobalActionsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HasText getTagCountValue() {
        return tagCountOnPeerValueLabel;
    }

    @Override
    public HasText getPendingCommandCountValue() {
        return pendingCommandCountValueLabel;
    }

    interface GlobalActionsViewImplUiBinder extends UiBinder<Widget, GlobalActionsViewImpl> {
    }
}
