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
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
public class DataManagerImpl implements DataManager {
    private final Logger log = Logger.getLogger(DataManagerImpl.class.getName());
    private final IndexingUtil indexingUtil = new IndexingUtil();
    private final Map<String, TagNode> tagNodeIndex = newHashMap();
    private final Map<String, TagNode> adminTagNodeIndex = newHashMap();
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
                indexingUtil.rebuildTagNodeIndex(tagNodeIndex, result);
                callback.onSuccess(tagNodeIndex.get(id));
            }
        });

    }

    public void getAdminTagNode(final String id,
                                final AsyncCallback<TagNode> callback) {
        TagNode node = adminTagNodeIndex.get(id);

        if (node != null) {
            callback.onSuccess(node);
        } else {
            getAdminTagTree(new AsyncCallback<TagNode>() {
                @Override
                public void onFailure(Throwable arg0) {
                    callback.onFailure(arg0);
                }

                @Override
                public void onSuccess(TagNode arg0) {
                    callback.onSuccess(adminTagNodeIndex.get(id));
                }
            });
        }
    }

    @Override
    public void reset() {
        getTagTreeMemoProc.reset();
        tagNodeIndex.clear();
    }


    public void getAdminTagTree(final AsyncCallback<TagNode> callback) {
        dispatchAsync.execute(new GetAdminTagTreeAction(),
                new AsyncCallback<TagTreeResult>() {
                    public void onFailure(Throwable caught) {
                        callback.onFailure(caught);
                    }

                    public void onSuccess(TagTreeResult result) {
                        indexingUtil.rebuildTagNodeIndex(adminTagNodeIndex,
                                result.getTree());
                        callback.onSuccess(result.getTree());
                    }
                });
    }
}
