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

public class PlaceBuilder {
    private String tagId;
    private String photoId;
    private int columnCount;
    private int rowCount;
    private boolean autoHide;

    public PlaceBuilder(BasePlace fromPlace) {
        this.tagId = fromPlace.getTagId();
        this.photoId = fromPlace.getPhotoId();
        this.columnCount = fromPlace.getColumnCount();
        this.rowCount = fromPlace.getRowCount();
        this.autoHide = fromPlace.isAutoHide();
    }

    public BasePlace place() {
        return new BasePlace(tagId, photoId, columnCount, rowCount, autoHide);
    }

    public PlaceBuilder setTagId(String tagId) {
        this.tagId = tagId;
        return this;
    }

    public PlaceBuilder setPhotoId(String photoId) {
        this.photoId = photoId;
        return this;
    }

    public PlaceBuilder setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        return this;
    }

    public PlaceBuilder setRowCount(int rowCount) {
        this.rowCount = rowCount;
        return this;
    }

    public PlaceBuilder setAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
        return this;
    }
}
