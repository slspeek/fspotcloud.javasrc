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

import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Navigator.Zoom;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Holds the current raster size
 *
 * @author steven
 */
public class PlaceCalculator {
    private final Logger log = Logger.getLogger(PlaceCalculator.class.getName());
    public static final int DEFAULT_RASTER_WIDTH = 4;
    public static final int DEFAULT_RASTER_HEIGHT = 4;
    public static final int MINIMUM_RASTER_WIDTH = 2;
    public static final int MINIMUM_RASTER_HEIGHT = 2;
    private int rasterWidth = DEFAULT_RASTER_WIDTH;
    private int rasterHeight = DEFAULT_RASTER_HEIGHT;
    private boolean autoHide = true;

    public PlaceCalculator() {
        log.log(Level.FINE, "Created");
    }

    public BasePlace getOneByOne(BasePlace place) {
        PlaceConverter converter = new PlaceConverter(place);
        converter.setRowCount(1);
        converter.setColumnCount(1);
        BasePlace dest = converter.getNewPlace();
        return dest;
    }

    public BasePlace toggleRasterView(BasePlace place) {
        int width = place.getColumnCount();
        int height = place.getRowCount();
        if ((width * height) > 1) {
            return getOneByOne(place);
        } else {
            return getResizedPlace(place, getRasterWidth(),getRasterHeight());
        }
    }

    public BasePlace toggleZoomView(BasePlace place, String tagId,
                                    String photoId) {
        PlaceConverter converter = new PlaceConverter(place);
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
        return converter.getNewPlace();
    }

    public void setRasterHeight(int rasterHeight) {
        if (rasterHeight >= MINIMUM_RASTER_HEIGHT) {
            this.rasterHeight = rasterHeight;
        }
    }

    public int getRasterHeight() {
        return rasterHeight;
    }

    public void setRasterWidth(int rasterWidth) {
        if (rasterWidth >= MINIMUM_RASTER_WIDTH) {
            this.rasterWidth = rasterWidth;
        }
    }

    public int getRasterWidth() {
        return rasterWidth;
    }

    public BasePlace getTabularPlace(BasePlace place) {
        return getResizedPlace(place, getRasterWidth(), getRasterHeight());
    }

    private BasePlace getResizedPlace(BasePlace place, int width, int height) {
        PlaceConverter converter = new PlaceConverter(place);
        converter.setColumnCount(width);
        converter.setRowCount(height);
        return converter.getNewPlace();
    }
    public BasePlace zoom(BasePlace now, Navigator.Zoom direction) {
        BasePlace dest;

        if (direction == Zoom.IN) {
            int width = now.getColumnCount();
            int height = now.getRowCount();

            if ((width * height) == 1) {
                dest = now;
            } else {
                setRasterWidth(width - 1);
                setRasterHeight(height - 1);

                if ((width - 1) != getRasterWidth()) {
                    // switch to 1x1
                    dest = getOneByOne(now);
                } else {
                    dest = getTabularPlace(now);
                }
            }
        } else {
            int width = now.getColumnCount();
            int height = now.getRowCount();
            setRasterWidth(width + 1);
            setRasterHeight(height + 1);
            dest = getResizedPlace(now, getRasterWidth(), getRasterHeight());
        }
        return dest;
    }

    public BasePlace unslideshow(BasePlace fromPlace) {
        BasePlace result;

        if (fromPlace instanceof SlideshowPlace) {
            result = getOneByOne(fromPlace);
        } else {
            result = fromPlace;
        }

        return result;
    }

    public void setAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
    }

    public boolean isAutoHide() {
        return autoHide;
    }
}
