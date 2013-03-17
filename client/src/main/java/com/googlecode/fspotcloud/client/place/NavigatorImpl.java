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

package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.data.LatestTagHelper;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;


public class NavigatorImpl implements Navigator {
    private final Logger log = Logger.getLogger(NavigatorImpl.class.getName());
    private final IPlaceController placeController;
    private final DataManager dataManager;
    private final PlaceManager placeManager;
    private final IClientLoginManager clientLoginManager;
    private final LatestTagHelper latestTagHelper;

    @Inject
    public NavigatorImpl(
            IPlaceController placeController,
            PlaceManager placeManager,
            DataManager dataManager,
            IClientLoginManager clientLoginManager,
            LatestTagHelper latestTagHelper) {
        this.placeController = placeController;
        this.placeManager = placeManager;
        this.dataManager = dataManager;
        this.clientLoginManager = clientLoginManager;
        this.latestTagHelper = latestTagHelper;
    }

    @Override
    public void goAsync(Direction direction, Unit step) {
        final BasePlace where = placeController.where();
        if (where != null) {
            goAsyncImpl(direction, step, where);
        }
    }

    @Override
    public void canGoAsync(final Direction direction, final Unit step,
                           final AsyncCallback<Boolean> callback) {
        final BasePlace place = placeController.where();
        dataManager.getTagNode(place.getTagId(),
                new TagNodeCallback(callback) {
                    @Override
                    public void onSuccess(TagNode result) {
                        PhotoInfoStore store = result.getCachedPhotoList();
                        callback.onSuccess(canGo(direction, step, place, store));
                    }
                });
    }

    private int indexOf(BasePlace place, PhotoInfoStore store) {
        return store.indexOf(place.getPhotoId());
    }

    private void goAsyncImpl(final Direction direction, final Unit step,
                             final BasePlace place) {
        dataManager.getTagNode(place.getTagId(),
                new TagNodeCallback() {
                    @Override
                    public void onSuccess(TagNode result) {
                        PhotoInfoStore store = result.getCachedPhotoList();
                        go(direction, step, place, store);
                    }
                });
    }

    private int stepSize(Unit unit, BasePlace place) {
        int stepSize = 0;

        switch (unit) {
            case SINGLE:
                stepSize = 1;
                break;

            case ROW:
                stepSize = place.getColumnCount();
                break;

            case PAGE:
                stepSize = place.getColumnCount() * place.getRowCount();
                break;
        }

        return stepSize;
    }

    private void go(final Direction direction, final Unit step,
                    BasePlace place, PhotoInfoStore store) {
        if (step == Unit.BORDER) {
            if (!store.isEmpty()) {
                String photoId;
                if (direction == Direction.BACKWARD) {
                    photoId = store.get(0).getId();
                } else {
                    photoId = store.last().getId();
                }
                goToPhoto(place, place.getTagId(), photoId);
            }
        } else {
            int position = indexOf(place, store);
            int stepSize = stepSize(step, place);
            if (canGo(direction, step, place, store)) {
                int nextIndex = position +
                        (stepSize * ((direction == Direction.FORWARD) ? 1 : (-1)));
                String photoId = store.get(nextIndex).getId();
                goToPhoto(place, place.getTagId(), photoId);
            }
        }
    }

    private boolean canGo(final Direction direction, final Unit step,
                          BasePlace fromPlace, PhotoInfoStore store) {
        int position = indexOf(fromPlace, store);
        if (step == Unit.BORDER) {
            if (direction == Direction.FORWARD) {
                if (position != store.lastIndex()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (position == 0) {
                    return false;
                }  else {
                    return true;
                }
            }

        }

        int stepSize = stepSize(step, fromPlace);

        if (direction == Direction.FORWARD) {
            return (position >= 0) && ((position + stepSize) < store.size());
        } else {
            return (position - stepSize) >= 0;
        }
    }

    protected void goToPhoto(BasePlace oldPlace, String tagId, String photoId) {
        BasePlace newPlace;

        if (oldPlace instanceof SlideshowPlace) {
            newPlace = new SlideshowPlace(tagId, photoId);
        } else {
            newPlace = new BasePlace(tagId, photoId, oldPlace.getColumnCount(),
                    oldPlace.getRowCount(), oldPlace.isAutoHide());
        }

        log.log(Level.FINE, "About to go to: " + newPlace + " from: " +
                oldPlace);
        placeController.goTo(newPlace);
    }

    @Override
    public void getPageCountAsync(String tagId, final int pageSize,
                                  final AsyncCallback<Integer> callback) {
        log.log(Level.FINE, "getPageCountAsync for: " + tagId);
        dataManager.getTagNode(tagId,
                new TagNodeCallback(callback) {
                    @Override
                    public void onSuccess(TagNode result) {
                        getPageCount(result, pageSize, callback);
                    }
                });
    }

    private void getPageCount(TagNode node, int pageSize,
                              AsyncCallback<Integer> callback) {
        PhotoInfoStore store = node.getCachedPhotoList();
        int result = store.size() / pageSize;

        if ((store.size() % pageSize) != 0) {
            result++;
        }

        callback.onSuccess(result);
    }

    private abstract class TagNodeCallback implements AsyncCallback<TagNode> {

        private final AsyncCallback<?> callback;

        public TagNodeCallback(AsyncCallback<?> callback) {
            this.callback = callback;

        }

        public TagNodeCallback() {
            this(new AsyncCallback<Object>() {
                @Override
                public void onFailure(Throwable caught) {
                }

                @Override
                public void onSuccess(Object result) {
                }
            });
        }

        @Override
        public void onFailure(Throwable caught) {
            callback.onFailure(caught);
        }
    }

    @Override
    public void getPageAsync(String tagId, final int pageSize,
                             final int pageNumber, final AsyncCallback<List<PhotoInfo>> callback) {
        log.log(Level.FINER, "getPageAsync: " + tagId + " " + pageSize + " " + pageNumber);
        dataManager.getTagNode(tagId,
                new TagNodeCallback(callback) {
                    @Override
                    public void onSuccess(TagNode result) {
                        getPage(result, pageSize, pageNumber, callback);
                    }
                });
    }

    private void getPage(TagNode node, int pageSize, int pageNumber,
                         AsyncCallback<List<PhotoInfo>> callback) {
        PhotoInfoStore store = getSafePhotoInfoStore(node);
        if (pageNumber < 0) {
            return;
        }
        int offset = pageNumber * pageSize;
        List<PhotoInfo> result = new ArrayList<PhotoInfo>();

        for (int i = offset; i < (offset + pageSize); i++) {
            if (i <= store.lastIndex()) {
                result.add(store.get(i));
            } else {
                break;
            }
        }

        callback.onSuccess(result);
    }

    private PhotoInfoStore getSafePhotoInfoStore(TagNode node) {
        PhotoInfoStore store;
        if (node == null) {
            List<PhotoInfo> empty = newArrayList();
            store = new PhotoInfoStore(empty);


        } else {
            store = node.getCachedPhotoList();
        }
        return store;
    }

    @Override
    public void getPageAsync(String tagId, final String photoId,
                             final int pageSize, final AsyncCallback<List<PhotoInfo>> callback) {
        log.log(Level.FINER, "getPageAsync: " + tagId + " " + pageSize + " photoId: " + photoId);
        dataManager.getTagNode(tagId,
                new TagNodeCallback(callback) {
                    @Override
                    public void onSuccess(TagNode result) {
                        PhotoInfoStore store = getSafePhotoInfoStore(result);
                        int pageNumber = 0;
                        int index = store.indexOf(photoId);
                        if (index == -1) {
                            if (!store.isEmpty()) {
                                //FIXME too heavy side effect of getting info
                                PhotoInfo info = store.get(0);
                                BasePlace lastBasePlace = placeController.where();
                                placeController.goTo(new BasePlace(lastBasePlace.getTagId(), info.getId(),
                                        lastBasePlace.getColumnCount(), lastBasePlace.getRowCount(), lastBasePlace.isAutoHide()));

                            }

                        } else {
                            pageNumber = index / pageSize;
                        }
                        getPage(result, pageSize, pageNumber, callback);
                    }
                });
    }

    @Override
    public void goToTag(String otherTagId, PhotoInfoStore store) {
        goToTag(otherTagId, store, Direction.BACKWARD);
    }

    @Override
    public void goToTag(String otherTagId, PhotoInfoStore store, Direction direction) {
        go(direction, Unit.BORDER,
                new BasePlace(otherTagId, null, placeManager.getRasterWidth(),
                        placeManager.getRasterHeight(), placeManager.isAutoHide()), store);
    }

    @Override
    public void goToAllPhotos() {
        BasePlace basePlace = placeController.where();
        BasePlace destPlace;
        if (basePlace != null) {
            PlaceBuilder converter = new PlaceBuilder(basePlace);
            converter.setTagId("all");
            destPlace = converter.place();
        }
        else {
            destPlace = new BasePlace("all", null);
            destPlace = placeManager.getTabularPlace(destPlace);

        }
        placeController.goTo(destPlace);
    }

    @Override
    public void goToLatestTag() {
        goToLatestTag(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                log.log(Level.WARNING, "goToLatestTag failed because: ", caught);
            }

            @Override
            public void onSuccess(String result) {
                //no op
            }
        });
    }

    @Override
    public void goToLatestTag(final AsyncCallback<String> report) {
        dataManager.getTagTree(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
                log.log(Level.WARNING, "goToLatestTag failed because: ", caught);
                report.onFailure(caught);
            }

            @Override
            public void onSuccess(TagNode result) {
                TagNode latestNode = latestTagHelper.getLatestNode(result);
                if (latestNode != null) {
                    goToTag(latestNode.getId(), latestNode.getCachedPhotoList(), Direction.FORWARD);
                    report.onSuccess("Success");
                } else {
                    handleNoTagsPublic(report);
                }
            }
        });
    }

    private void handleNoTagsPublic(final AsyncCallback<String> report) {
        clientLoginManager.getUserInfoAsync(new AsyncCallback<UserInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                log.log(Level.WARNING, "No user info because: ", caught);
                report.onFailure(caught);
            }

            @Override
            public void onSuccess(UserInfo result) {
                if (result.isAdmin()) {
                    placeController.goTo(TagPlace.DEFAULT);
                    report.onSuccess("To dashboard");
                } else if (!result.isLoggedIn()) {
                    clientLoginManager.redirectToLogin();
                    report.onSuccess("Redirect to login");
                } else {
                    report.onSuccess("Sorry no public labels to view yet");
                }
            }
        });
    }

    @Override
    public void fullscreen() {
        BasePlace destination = placeManager.getOneByOne();
        placeController.goTo(destination);
    }

    @Override
    public void zoom(Zoom direction) {
        BasePlace now = placeController.where();
        BasePlace destination = placeManager.zoom(now, direction);
        placeController.goTo(destination);
    }

    @Override
    public void slideshow() {
        BasePlace now = placeController.where();
        SlideshowPlace destination = new SlideshowPlace(now.getTagId(),
                now.getPhotoId());
        placeController.goTo(destination);
    }

    @Override
    public void unslideshow() {
        placeManager.unslideshow();
    }

    @Override
    public void setAutoHide(boolean autoHide) {
        placeManager.setAutoHide(autoHide);
    }

    @Override
    public void getPageRelativePositionAsync(String tagId,
                                             final String photoId, final int pageSize,
                                             final AsyncCallback<Integer[]> callback) {
        dataManager.getTagNode(tagId,
                new TagNodeCallback(callback) {
                    @Override
                    public void onSuccess(TagNode result) {
                        if (result != null) {
                            int total = result.getCount();
                            int photoIndex = result.getCachedPhotoList()
                                    .indexOf(photoId);
                            int noOfPages = (int) Math.ceil((float) total / (float) pageSize);
                            int pageNumber = photoIndex / pageSize;
                            callback.onSuccess(new Integer[]{pageNumber, noOfPages});
                        } else {
                            callback.onFailure(new RuntimeException(
                                    "label not found."));
                            clientLoginManager.redirectToLogin();
                        }
                    }
                });
    }
}
