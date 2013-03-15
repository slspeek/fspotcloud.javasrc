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

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Navigator.Zoom;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Holds the current raster size
 *
 * @author steven
 */
public class PlaceManager {
    private final Logger log = Logger.getLogger(PlaceManager.class.getName());
    private boolean autoHide = true;

    private final RasterState rasterState;
    private final IPlaceController placeController;

    @Inject
    public PlaceManager(RasterState rasterState,
                        IPlaceController placeController) {
        this.rasterState = rasterState;
        this.placeController = placeController;
        log.log(Level.FINE, "Created");
    }

    public void goOneByOne() {
        placeController.goTo(getOneByOne());
    }

    public BasePlace getOneByOne() {
        return getOneByOne(placeController.where());
    }

    private BasePlace getOneByOne(BasePlace place) {
        PlaceBuilder converter = new PlaceBuilder(place);
        return converter.setRowCount(1).setColumnCount(1).place();
    }

    public void toggleRasterView() {
        placeController.goTo(toggleRasterView(placeController.where()));
    }

    private BasePlace toggleRasterView(BasePlace place) {
        int width = place.getColumnCount();
        int height = place.getRowCount();
        if ((width * height) > 1) {
            return getOneByOne(place);
        } else {
            return getTabularPlace(place);
        }
    }

    public BasePlace toggleZoomView(BasePlace place, String tagId,
                                    String photoId) {
        PlaceBuilder converter = new PlaceBuilder(place);
        converter.setTagId(tagId);
        converter.setPhotoId(photoId);
        int width = place.getColumnCount();
        int height = place.getRowCount();
        if ((width * height) > 1) {
            // Zoom in
            width = 1;
            height = 1;
        } else {
            // Zoom out
            width = getRasterWidth();
            height = getRasterHeight();
        }
        converter.setColumnCount(width);
        converter.setRowCount(height);
        return converter.place();
    }

    int getRasterHeight() {
        return rasterState.getRowCount();
    }

    int getRasterWidth() {

        return rasterState.getColumnCount();
    }

    public BasePlace getTabularPlace(BasePlace place) {
        return getResizedPlace(place,
                rasterState.getColumnCount(),
                rasterState.getRowCount());
    }

    private BasePlace getResizedPlace(BasePlace place, int width, int height) {
        PlaceBuilder converter = new PlaceBuilder(place);
        converter.setColumnCount(width);
        converter.setRowCount(height);
        return converter.place();
    }
    public BasePlace zoom(BasePlace now, Navigator.Zoom direction) {
        BasePlace dest;
        if (direction == Zoom.IN) {
            int width = now.getColumnCount();
            int height = now.getRowCount();

            if ((width * height) == 1) {
                //cannot zoom further IN
                dest = now;
            } else {
                //try decreasing both width and height
                final int newWidth = width - 1;
                rasterState.setRowCount(height - 1);
                rasterState.setColumnCount(newWidth);


                if (newWidth != rasterState.getColumnCount()) {
                    //width was not decreased
                    // switch to 1x1
                    dest = getOneByOne(now);
                } else {
                    dest = getTabularPlace(now);
                }
            }
        } else {
            //Zooming OUT
            int width = now.getColumnCount();
            int height = now.getRowCount();
            rasterState.setColumnCount(width + 1);
            rasterState.setRowCount(height + 1);
            dest = getTabularPlace(now);
        }
        return dest;
    }

    public void unslideshow() {
        BasePlace fromPlace = placeController.where();
        BasePlace result;
        if (fromPlace instanceof SlideshowPlace) {
            result = getOneByOne(fromPlace);
        } else {
            result = fromPlace;
        }
        placeController.goTo(result);
    }

    public void setAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
    }

    public boolean isAutoHide() {
        return autoHide;
    }
}
