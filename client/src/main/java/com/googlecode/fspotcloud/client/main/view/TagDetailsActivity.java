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

package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.gin.Dashboard;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.client.main.view.api.TagDetailsView;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TagDetailsActivity extends AbstractActivity implements TagDetailsView.TagDetailsPresenter {
    private final Logger log = Logger.getLogger(TagDetailsActivity.class.getName());
    private final TagDetailsView tagDetailsView;
    private final DataManager dataManager;
    private final IPlaceController placeController;
    private final StatusView statusView;


    @Inject
    public TagDetailsActivity(TagDetailsView tagDetailsView,
                              DataManager dataManager,
                              IPlaceController placeController,
                              @Dashboard StatusView statusView) {
        super();
        this.tagDetailsView = tagDetailsView;
        this.dataManager = dataManager;
        this.placeController = placeController;
        this.statusView = statusView;
    }

    @Override
    public void init() {
        log.log(Level.FINE, "init");
        tagDetailsView.setPresenter(this);
        populateView();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(tagDetailsView);
    }

    public void populateView() {
        statusView.setStatusText("Retrieving category data");
        TagPlace tagPlace = (TagPlace) placeController.getRawWhere();

        dataManager.getAdminTagNode(tagPlace.getTagId(),
                new AsyncCallback<TagNode>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "Trouble retrieving admin tag tree ",
                                caught);
                        statusView.setStatusText("A server error prevented reloading category data");
                    }

                    @Override
                    public void onSuccess(TagNode result) {
                        populateView(result);
                        statusView.setStatusText("Reloaded information for category: " + result.getTagName());
                    }
                });
    }

    private void populateView(TagNode tag) {
        tagDetailsView.getTagNameValue().setText(tag.getTagName());
        tagDetailsView.getTagDescriptionValue().setText(tag.getDescription());
        tagDetailsView.getTagImageCountValue()
                .setText(String.valueOf(tag.getCount()));
        tagDetailsView.getTagImportIssuedValue()
                .setText(tag.isImportIssued() ? "yes" : "no");
        tagDetailsView.getImportButtonText()
                .setText(tag.isImportIssued() ? "Remove" : "Import");
        tagDetailsView.getTagLoadedImagesCountValue()
                .setText(String.valueOf(tag.getCachedPhotoList()
                        .lastIndex() + 1));
    }
}
