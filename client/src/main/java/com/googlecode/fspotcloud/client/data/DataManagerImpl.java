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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.shared.dashboard.GetAdminTagTreeAction;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

public class DataManagerImpl implements DataManager {
    private final Logger log = Logger.getLogger(DataManagerImpl.class.getName());
    private final IndexingUtil indexingUtil;
    private boolean isCalled = false;
    private final List<Runnable> queue = newArrayList();
    private final Runnable callbackHook = new Runnable() {
        @Override
        public void run() {
            for (Runnable task : queue) {
                task.run();
            }

            queue.clear();
        }
    };

    private List<TagNode> tagTreeData = null;
    private final Map<String, TagNode> tagNodeIndex = newHashMap();
    private final Map<String, TagNode> adminTagNodeIndex = newHashMap();
    private final DispatchAsync dispatchAsync;

    @Inject
    public DataManagerImpl(DispatchAsync dispatchAsync) {
        this.dispatchAsync = dispatchAsync;
        this.indexingUtil = new IndexingUtil();
    }

    public void getTagNode(final String id,
                           final AsyncCallback<TagNode> callback) {
        log.info("getTagNode: " + id);
        if (tagTreeData != null) {
            TagNode node = tagNodeIndex.get(id);
            callback.onSuccess(node);
        } else {
            if (!isCalled) {

                getTagTree(new AsyncCallback<List<TagNode>>() {
                    @Override
                    public void onFailure(Throwable arg0) {
                        callback.onFailure(arg0);
                    }

                    @Override
                    public void onSuccess(List<TagNode> arg0) {
                        callback.onSuccess(tagNodeIndex.get(id));
                    }
                });
            } else {
                queue.add(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(tagNodeIndex.get(id));
                    }
                });
            }
        }
    }

    public void getAdminTagNode(final String id,
                                final AsyncCallback<TagNode> callback) {
        TagNode node = adminTagNodeIndex.get(id);

        if (node != null) {
            callback.onSuccess(node);
        } else {
            getAdminTagTree(new AsyncCallback<List<TagNode>>() {
                @Override
                public void onFailure(Throwable arg0) {
                    callback.onFailure(arg0);
                }

                @Override
                public void onSuccess(List<TagNode> arg0) {
                    callback.onSuccess(adminTagNodeIndex.get(id));
                }
            });
        }
    }

    @Override
    public void reset() {
        isCalled = false;
        tagTreeData = null;
        tagNodeIndex.clear();
    }

    public void getTagTree(final AsyncCallback<List<TagNode>> callback) {
        log.info("getTagTree ");
        if (tagTreeData != null) {
            log.info("getTagTree cached");
            callback.onSuccess(tagTreeData);
        } else {
            queue.add(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(tagTreeData);
                }
            });

            if (!isCalled) {
                isCalled = true;
                log.info("getTagTree CALLING OUT!");
                dispatchAsync.execute(new GetTagTreeAction(),
                        new AsyncCallback<TagTreeResult>() {
                            public void onFailure(Throwable caught) {
                                callback.onFailure(caught);
                            }

                            public void onSuccess(TagTreeResult result) {
                                try {
                                tagTreeData = result.getTree();
                                indexingUtil.rebuildTagNodeIndex(tagNodeIndex,
                                        tagTreeData);
                                callbackHook.run();
                                log.info("Hook ran ! for:" + result.getTree());
                                } catch (Exception e) {
                                    log.log(Level.FINEST, "error", e);
                                }
                            }
                        });
            }
        }
    }

    public void getAdminTagTree(final AsyncCallback<List<TagNode>> callback) {
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
