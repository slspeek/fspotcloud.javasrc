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

import com.google.common.base.Objects;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.googlecode.fspotcloud.client.place.api.PhotoInTag;
import com.googlecode.fspotcloud.client.place.api.Raster;


public class BasePlace extends Place implements PhotoInTag, Raster {
    private final String tagId;
    private final String photoId;
    private final int columnCount;
    private final int rowCount;
    private final boolean autoHide;

    public BasePlace() {
        this("latest", "latest");
    }

    public BasePlace(String tagId, String photoId) {
        this(tagId, photoId, 1, 1, true);
    }

    public BasePlace(String tagId, String photoId, int columnCount, int rowCount, boolean autoHide) {
        this.tagId = tagId;
        this.photoId = photoId;
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.autoHide = autoHide;
    }

    public boolean isAutoHide() {
        return autoHide;
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
        return Objects.hashCode(tagId, photoId, rowCount, columnCount, autoHide);
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(BasePlace.class)) {
            BasePlace basePlace = (BasePlace) other;
            String tagId = basePlace.getTagId();
            String photoId = basePlace.getPhotoId();
            int columnCount = basePlace.getColumnCount();
            int rowCount = basePlace.getRowCount();
            boolean autoHide = basePlace.isAutoHide();


            return equal(this.tagId, tagId) && equal(this.photoId, photoId) &&
                    equal(this.rowCount, rowCount) &&
                    equal(this.autoHide, autoHide) &&
                    equal(this.columnCount, columnCount);
        } else {
            return false;
        }
    }

    public String toString() {
        return Objects.toStringHelper(this).add("tagId", tagId)
                .add("photoId", photoId)
                .add("rows", rowCount)
                .add("colums", columnCount).add("autoHide", autoHide).toString();
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
                    util.getColumnCount(), util.getRowCount(), util.isAutoHide());
        }

        @Override
        public String getToken(BasePlace place) {
            return place.getTagId() + ":" +
                    place.getPhotoId() + ":" +
                    place.getColumnCount() + ":" +
                    place.getRowCount() + ":" +
                    place.isAutoHide();
        }
    }
}
