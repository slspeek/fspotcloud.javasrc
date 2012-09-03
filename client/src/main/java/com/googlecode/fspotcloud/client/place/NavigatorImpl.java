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
import com.googlecode.fspotcloud.client.data.IndexingUtil;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NavigatorImpl implements Navigator {
    private final Logger log = Logger.getLogger(NavigatorImpl.class.getName());
    private final PlaceGoTo placeGoTo;
    private final PlaceWhere placeWhere;
    private final DataManager dataManager;
    private final PlaceCalculator placeCalculator;

    @Inject
    public NavigatorImpl(PlaceWhere placeWhere, PlaceGoTo placeGoTo,
                         PlaceCalculator placeCalculator, DataManager dataManager) {
        this.placeGoTo = placeGoTo;
        this.placeWhere = placeWhere;
        this.placeCalculator = placeCalculator;
        this.dataManager = dataManager;
    }

    @Override
    public void goAsync(Direction direction, Unit step) {
        goAsync(direction, step, placeWhere.where());
    }

    @Override
    public void canGoAsync(final Direction direction, final Unit step,
                           final AsyncCallback<Boolean> callback) {
        final BasePlace place = placeWhere.where();
        dataManager.getTagNode(place.getTagId(),
                new AsyncCallback<TagNode>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.warning("getTagNode failed " + caught);
                    }

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

    private void goAsync(final Direction direction, final Unit step,
                         final BasePlace place) {
        dataManager.getTagNode(place.getTagId(),
                new AsyncCallback<TagNode>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.warning("getTagNode failed " + caught);
                    }

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

    protected int pageOf(BasePlace place, PhotoInfoStore store, int pageSize) {
        int result;
        int offset = indexOf(place, store);
        result = offset / pageSize;

        return result;
    }

    protected int pageCount(PhotoInfoStore store, int pageSize) {
        int result = store.size() / pageSize;

        if ((store.size() % pageSize) != 0) {
            result++;
        }

        return result;
    }

    private boolean canGo(final Direction direction, final Unit step,
                          BasePlace place, PhotoInfoStore store) {
        if (step == Unit.BORDER) {
            return true;
        }

        int position = indexOf(place, store);
        int stepSize = stepSize(step, place);

        if (direction == Direction.FORWARD) {
            return (position >= 0) && ((position + stepSize) < store.size());
        } else {
            return (position - stepSize) >= 0;
        }
    }

    protected void goToPhoto(BasePlace place, String tagId, String photoId) {
        BasePlace newPlace;

        if (place instanceof SlideshowPlace) {
            newPlace = new SlideshowPlace(tagId, photoId, 0f);
        } else {
            newPlace = new BasePlace(tagId, photoId, place.getColumnCount(),
                    place.getRowCount());
        }

        log.info("About to go to: " + this + " : " + newPlace + " from: " +
                place);
        placeGoTo.goTo(newPlace);
    }

    @Override
    public void getPageCountAsync(String tagId, final int pageSize,
                                  final AsyncCallback<Integer> callback) {
        dataManager.getTagNode(tagId,
                new AsyncCallback<TagNode>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.WARNING, "getTagNode on datamanager failed",
                                caught);
                    }

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

    @Override
    public void getPageAsync(String tagId, final int pageSize,
                             final int pageNumber, final AsyncCallback<List<PhotoInfo>> callback) {
        dataManager.getTagNode(tagId,
                new AsyncCallback<TagNode>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.WARNING, "getTagNode on datamanager failed",
                                caught);
                        callback.onFailure(caught);
                    }

                    @Override
                    public void onSuccess(TagNode result) {
                        getPage(result, pageSize, pageNumber, callback);
                    }
                });
    }

    private void getPage(TagNode node, int pageSize, int pageNumber,
                         AsyncCallback<List<PhotoInfo>> callback) {
        PhotoInfoStore store = node.getCachedPhotoList();
        log.info("Store: " + store);

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

    @Override
    public void getPageAsync(String tagId, final String photoId,
                             final int pageSize, final AsyncCallback<List<PhotoInfo>> callback) {
        dataManager.getTagNode(tagId,
                new AsyncCallback<TagNode>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.WARNING, "getTagNode on datamanager failed",
                                caught);
                        callback.onFailure(caught);
                    }

                    @Override
                    public void onSuccess(TagNode result) {
                        PhotoInfoStore store = result.getCachedPhotoList();
                        int index = store.indexOf(photoId);
                        int pageNumber = index / pageSize;
                        getPage(result, pageSize, pageNumber, callback);
                    }
                });
    }

    @Override
    public void toggleZoomViewAsync(String tagId, String photoId) {
        BasePlace newPlace = placeCalculator.toggleZoomView(placeWhere.where(),
                tagId, photoId);
        placeGoTo.goTo(newPlace);
    }

    @Override
    public void goToTag(String otherTagId, PhotoInfoStore store) {
        go(Direction.BACKWARD, Unit.BORDER,
                new BasePlace(otherTagId, null, placeCalculator.getRasterWidth(),
                        placeCalculator.getRasterHeight()), store);
    }

    @Override
    public void goToLatestTag() {
        dataManager.getTagTree(new AsyncCallback<List<TagNode>>() {
            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onSuccess(List<TagNode> result) {
                Date latest = new Date(0);
                TagNode latestNode = null;
                IndexingUtil util = new IndexingUtil();
                Map<String, TagNode> tagNodeIndex = new HashMap<String, TagNode>();
                util.rebuildTagNodeIndex(tagNodeIndex, result);

                for (String tagId : tagNodeIndex.keySet()) {
                    TagNode node = tagNodeIndex.get(tagId);
                    PhotoInfoStore store = node.getCachedPhotoList();

                    if ((store != null) && !store.isEmpty()) {
                        PhotoInfo info = store.last();
                        Date lastDate = info.getDate();

                        if (lastDate.after(latest)) {
                            latest = lastDate;
                            latestNode = node;
                        }
                    }
                }

                goToTag(latestNode.getId(), latestNode.getCachedPhotoList());
            }
        });
    }

    @Override
    public void setRasterWidth(int width) {
        placeCalculator.setRasterWidth(width);
    }

    @Override
    public void setRasterHeight(int height) {
        placeCalculator.setRasterHeight(height);
    }

    @Override
    public void increaseRasterWidth(int amount) {
        placeCalculator.setRasterWidth(placeCalculator.getRasterWidth() +
                amount);
        reloadCurrentPlaceOnNewSize();
    }

    @Override
    public void increaseRasterHeight(int amount) {
        placeCalculator.setRasterHeight(placeCalculator.getRasterHeight() +
                amount);
        reloadCurrentPlaceOnNewSize();
    }

    @Override
    public void setRasterDimension(int i, int j) {
        placeCalculator.setRasterWidth(i);
        placeCalculator.setRasterHeight(j);
        reloadCurrentPlaceOnNewSize();
    }

    private void reloadCurrentPlaceOnNewSize() {
        BasePlace now = placeWhere.where();
        BasePlace destination = placeCalculator.getTabularPlace(now);
        placeGoTo.goTo(destination);
    }

    @Override
    public void toggleRasterView() {
        BasePlace now = placeWhere.where();
        BasePlace destination = placeCalculator.toggleRasterView(now);
        placeGoTo.goTo(destination);
    }

    @Override
    public void resetRasterSize() {
        setRasterDimension(PlaceCalculator.DEFAULT_RASTER_WIDTH,
                PlaceCalculator.DEFAULT_RASTER_HEIGHT);
    }

    @Override
    public void fullscreen() {
        BasePlace now = placeWhere.where();
        BasePlace destination = placeCalculator.getFullscreen(now);
        placeGoTo.goTo(destination);
    }

    @Override
    public void zoom(Zoom direction) {
        BasePlace now = placeWhere.where();
        BasePlace destination = placeCalculator.zoom(now, direction);
        placeGoTo.goTo(destination);
    }

    @Override
    public void slideshow() {
        BasePlace now = placeWhere.where();
        SlideshowPlace destination = new SlideshowPlace(now.getTagId(),
                now.getPhotoId(), 0f);
        placeGoTo.goTo(destination);
    }

    @Override
    public void unslideshow() {
        BasePlace now = placeWhere.where();
        BasePlace destination = placeCalculator.unslideshow(now);
        placeGoTo.goTo(destination);
    }

    @Override
    public void getPageRelativePositionAsync(String tagId,
                                             final String photoId, final int pageSize,
                                             final AsyncCallback<Integer[]> callback) {
        dataManager.getTagNode(tagId,
                new AsyncCallback<TagNode>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.WARNING, "getTagNode on datamanager failed",
                                caught);
                        callback.onFailure(caught);
                    }

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
                        }
                    }
                });
    }
}
