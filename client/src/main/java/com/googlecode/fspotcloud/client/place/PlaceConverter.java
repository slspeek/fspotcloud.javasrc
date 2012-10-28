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

public class PlaceConverter {
    private String tagId;
    private String photoId;
    private int columnCount;
    private int rowCount;

    public PlaceConverter(BasePlace delegate) {
        this.tagId = delegate.getTagId();
        this.photoId = delegate.getPhotoId();
        this.columnCount = delegate.getColumnCount();
        this.rowCount = delegate.getRowCount();
    }

    public BasePlace getNewPlace() {
        return new BasePlace(tagId, photoId, columnCount, rowCount);
    }

    void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
