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

package com.googlecode.fspotcloud.client.admin.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.fspotcloud.client.admin.view.api.TagDetailsView;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.place.TagApprovalPlace;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.dashboard.UserImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.UserUnImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.TagNode;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TagDetailsActivity extends AbstractActivity implements TagDetailsView.TagDetailsPresenter {
    private final Logger log = Logger.getLogger(TagDetailsActivity.class.getName());
    private final TagDetailsView tagDetailsView;
    private final TagPlace tagPlace;
    private TagNode tagNode;
    private final DataManager dataManager;
    private final DispatchAsync dispatch;
    private final PlaceGoTo placeGoTo;

    public TagDetailsActivity(TagDetailsView tagDetailsView, TagPlace tagPlace,
                              DataManager dataManager, DispatchAsync dispatch, PlaceGoTo placeGoTo) {
        super();
        this.tagDetailsView = tagDetailsView;
        this.tagPlace = tagPlace;
        this.dataManager = dataManager;
        this.dispatch = dispatch;
        this.placeGoTo = placeGoTo;
    }

    @Override
    public void init() {
        log.info("init");
        populateView();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        tagDetailsView.setPresenter(this);
        panel.setWidget(tagDetailsView);
    }

    @Override
    public void importTag() {
        log.info("TagNode: " + tagNode + tagNode.isImportIssued());

        if ((tagNode != null) && tagNode.isImportIssued()) {
            dispatch.execute(new UserUnImportsTagAction(tagPlace.getTagId()),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            log.log(Level.SEVERE, "Action Exception ", caught);
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            Window.Location.reload();
                        }
                    });
        } else {
            dispatch.execute(new UserImportsTagAction(tagPlace.getTagId()),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            log.log(Level.SEVERE, "Action Exception ", caught);
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            Window.Location.reload();
                        }
                    });
        }
    }

    @Override
    public void manageAccess() {
        placeGoTo.goTo(new TagApprovalPlace(tagNode.getId()));
    }

    private void populateView() {
        String tagId = tagPlace.getTagId();
        dataManager.getAdminTagNode(tagId,
                new AsyncCallback<TagNode>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "Trouble retrieving admin tag tree ",
                                caught);
                    }

                    @Override
                    public void onSuccess(TagNode result) {
                        tagNode = result;
                        populateView(result);
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
        // tagDetailsView.getImportButton().setEnabled(!tag.isImportIssued());
        tagDetailsView.getImportButtonText()
                .setText(tag.isImportIssued() ? "Remove" : "Import");
        tagDetailsView.getTagLoadedImagesCountValue()
                .setText(String.valueOf(tag.getCachedPhotoList()
                        .lastIndex() + 1));
    }
}
