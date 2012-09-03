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

import com.google.gwt.place.shared.PlaceTokenizer;


public class SlideshowPlace extends BasePlace {
    private final float interval;

    public SlideshowPlace(String tagId, String photoId, float interval) {
        super(tagId, photoId);
        this.interval = interval;
    }

    public float getInterval() {
        return interval;
    }

    public boolean equals(Object otherObject) {
        if (otherObject instanceof SlideshowPlace) {
            BasePlace basePlace = (BasePlace) otherObject;
            String tagId = basePlace.getTagId();
            String photoId = basePlace.getPhotoId();

            return equal(getTagId(), tagId) && equal(getPhotoId(), photoId);
        } else {
            return false;
        }
    }

    public class Tokeninzer {
    }

    public static class Tokenizer implements PlaceTokenizer<SlideshowPlace> {
        @Override
        public SlideshowPlace getPlace(String token) {
            TokenizerUtil util = new TokenizerUtil(token);

            return new SlideshowPlace(util.getTagId(), util.getPhotoId(), 0f);
        }

        @Override
        public String getToken(SlideshowPlace place) {
            return place.getTagId() + ":" + place.getPhotoId();
        }
    }
}
