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
package com.googlecode.fspotcloud.client.data;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.shared.dashboard.GetAdminTagTreeAction;
import com.googlecode.fspotcloud.shared.main.*;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
public class DataManagerImpl implements DataManager {
    private final Logger log = Logger.getLogger(DataManagerImpl.class.getName());
    private final IndexingUtil indexingUtil = new IndexingUtil();
    private final AllPhotosHelper allPhotosHelper = new AllPhotosHelper();
    private final Map<String, TagNode> tagNodeIndex = newHashMap();
    private final DispatchAsync dispatchAsync;
    private final GetTagTreeMemoProc getTagTreeMemoProc;

    @Inject
    public DataManagerImpl(DispatchAsync dispatchAsync,
                           GetTagTreeMemoProc getTagTreeMemoProc) {
        this.dispatchAsync = dispatchAsync;
        this.getTagTreeMemoProc = getTagTreeMemoProc;
    }

    public void getTagTree(final AsyncCallback<TagNode> callback) {
        log.log(Level.FINEST, "getTagTree ");
        getTagTreeMemoProc.getAsync(new AsyncCallback<TagTreeResult>() {
            @Override
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            @Override
            public void onSuccess(TagTreeResult result) {
                callback.onSuccess(result.getTree());
            }
        });
    }

    public void getTagNode(final String id,
                           final AsyncCallback<TagNode> callback) {
        log.log(Level.FINEST, "getTagNode: " + id);
        getTagTree(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            @Override
            public void onSuccess(TagNode result) {
                if (id.equals("all")) {
                    getAllPhotos(new AsyncCallback<PhotoInfoStore>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            callback.onFailure(caught);
                        }

                        @Override
                        public void onSuccess(PhotoInfoStore result) {
                            TagNode virtualNode = new TagNode("all");
                            virtualNode.setCachedPhotoList(result);
                            virtualNode.setCount(result.size());
                            callback.onSuccess(virtualNode);
                        }
                    });
                } else {
                    indexingUtil.rebuildTagNodeIndex(tagNodeIndex, result);
                    callback.onSuccess(tagNodeIndex.get(id));
                }
            }
        });

    }

    public void getAdminTagNode(final String id,
                                final AsyncCallback<TagNode> callback) {
        dispatchAsync.execute(new GetTagNodeAction(id), new AsyncCallback<TagNodeResult>() {
            @Override
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            @Override
            public void onSuccess(TagNodeResult result) {
                callback.onSuccess(result.getInfo());
            }
        });
    }

    @Override
    public void reset() {
        getTagTreeMemoProc.reset();
        tagNodeIndex.clear();
    }


    public void getAllPhotos(final AsyncCallback<PhotoInfoStore> storeAsyncCallback) {
        getTagTree(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
                storeAsyncCallback.onFailure(caught);
            }

            @Override
            public void onSuccess(TagNode result) {
                final PhotoInfoStore allPhotos = allPhotosHelper.getAllPhotos(result);
                storeAsyncCallback.onSuccess(allPhotos);
            }
        });
    }


    public void getAdminTagTree(final AsyncCallback<TagNode> callback) {
        dispatchAsync.execute(new GetAdminTagTreeAction(),
                new AsyncCallback<TagTreeResult>() {
                    public void onFailure(Throwable caught) {
                        callback.onFailure(caught);
                    }

                    public void onSuccess(TagTreeResult result) {
                        callback.onSuccess(result.getTree());
                    }
                });
    }
}
