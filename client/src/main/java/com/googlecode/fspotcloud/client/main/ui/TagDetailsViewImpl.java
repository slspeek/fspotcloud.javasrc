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
import com.googlecode.fspotcloud.client.main.view.api.TagDetailsView;

import java.util.logging.Logger;


public class TagDetailsViewImpl extends Composite implements TagDetailsView {
    private static final TagDetailsViewImplUiBinder uiBinder = GWT.create(TagDetailsViewImplUiBinder.class);
    private static final Logger log = Logger.getLogger(TagDetailsViewImpl.class.getName());
    @UiField
    Label tagNameValueLabel;
    @UiField
    Label tagDescriptionValueLabel;
    @UiField
    Label tagImportIssuedValueLabel;
    @UiField
    Label tagCountValueLabel;
    @UiField
    Label tagLoadedCountValueLabel;
    @UiField
    PushButtonExt importTagButton;
    @UiField
    PushButtonExt manageAccessButton;
    private TagDetailsPresenter presenter;

    private static int count;


    public TagDetailsViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        importTagButton.ensureDebugId("import-tag-button");
        manageAccessButton.ensureDebugId("manage-access-button");
        log.info("TagDetailsView created: " + ++count);
    }

    @Override
    public HasText getTagDescriptionValue() {
        return tagDescriptionValueLabel;
    }

    @Override
    public HasText getTagImageCountValue() {
        return tagCountValueLabel;
    }

    @Override
    public HasText getTagImportIssuedValue() {
        return tagImportIssuedValueLabel;
    }

    @Override
    public HasText getTagLoadedImagesCountValue() {
        return tagLoadedCountValueLabel;
    }

    @Override
    public HasText getTagNameValue() {
        return tagNameValueLabel;
    }

    @UiHandler("importTagButton")
    public void onImportClicked(ClickEvent event) {
        presenter.importTag();
    }

    @UiHandler("manageAccessButton")
    public void onManageAccessClicked(ClickEvent event) {
        presenter.manageAccess();
    }

    @Override
    public void setPresenter(TagDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HasEnabled getImportButton() {
        return importTagButton;
    }

    @Override
    public HasText getImportButtonText() {
        return importTagButton;
    }

    interface TagDetailsViewImplUiBinder extends UiBinder<Widget, TagDetailsViewImpl> {
    }
}