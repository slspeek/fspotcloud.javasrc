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

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.googlecode.fspotcloud.client.place.api.PhotoInTag;
import com.googlecode.fspotcloud.client.place.api.Raster;


public class BasePlace extends Place implements PhotoInTag, Raster {
    private final String tagId;
    private final String photoId;
    private final int columnCount;
    private final int rowCount;

    public BasePlace(String tagId, String photoId) {
        this(tagId, photoId, 1, 1);
    }

    public BasePlace(String tagId, String photoId, int columnCount, int rowCount) {
        this.tagId = tagId;
        this.photoId = photoId;
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }

    public String getTagId() {
        return tagId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        if (tagId != null) {
            hash += tagId.hashCode();
        }

        if (photoId != null) {
            hash += photoId.hashCode();
        }

        hash += Integer.valueOf(columnCount).hashCode();
        hash += Integer.valueOf(rowCount).hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(BasePlace.class)) {
            BasePlace basePlace = (BasePlace) other;
            String tagId = basePlace.getTagId();
            String photoId = basePlace.getPhotoId();
            int columnCount = basePlace.getColumnCount();
            int rowCount = basePlace.getRowCount();

            return equal(this.tagId, tagId) && equal(this.photoId, photoId) &&
                    equal(this.rowCount, rowCount) &&
                    equal(this.columnCount, columnCount);
        } else {
            return false;
        }
    }

    public String toString() {
        String result = "<<Place tagId: " + tagId + " photoId: " + photoId +
                "(" + columnCount + "x" + rowCount + ")>>";

        return result;
    }

    public static boolean equal(Object a, Object b) {
        if (a == null) {
            if (b == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return a.equals(b);
        }
    }

    public static class Tokenizer implements PlaceTokenizer<BasePlace> {
        @Override
        public BasePlace getPlace(String token) {
            TokenizerUtil util = new TokenizerUtil(token);

            return new BasePlace(util.getTagId(), util.getPhotoId(),
                    util.getColumnCount(), util.getRowCount());
        }

        @Override
        public String getToken(BasePlace place) {
            return place.getTagId() + ":" + place.getPhotoId() + ":" +
                    place.getColumnCount() + ":" + place.getRowCount();
        }
    }
}
