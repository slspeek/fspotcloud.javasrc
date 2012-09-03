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

package com.googlecode.fspotcloud.client.place.api;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;

import java.util.List;


public interface Navigator {
    void goAsync(Direction direction, Unit step);

    void canGoAsync(Direction direction, Unit step,
                    AsyncCallback<Boolean> callback);

    void getPageCountAsync(String tagId, int pageSize,
                           AsyncCallback<Integer> callback);

    void getPageAsync(String tagId, int pageSize, int pageNumber,
                      AsyncCallback<List<PhotoInfo>> callback);

    void getPageAsync(String tagId, String photoId, int pageSize,
                      AsyncCallback<List<PhotoInfo>> callback);

    void getPageRelativePositionAsync(String tagId, String photoId,
                                      int pageSize, AsyncCallback<Integer[]> callback);

    void toggleZoomViewAsync(String tagId, String photoId);

    void goToTag(String otherTagId, PhotoInfoStore store);

    void goToLatestTag();

    void setRasterWidth(int width);

    void setRasterHeight(int height);

    void increaseRasterWidth(int amount);

    void increaseRasterHeight(int amount);

    void toggleRasterView();

    void setRasterDimension(int i, int j);

    void resetRasterSize();

    void fullscreen();

    void slideshow();

    void zoom(Zoom direction);

    void unslideshow();

    enum Direction {
        BACKWARD, FORWARD;
    }

    enum Unit {
        BORDER, PAGE, ROW, SINGLE;
    }

    enum Zoom {
        IN, OUT;
    }
}
