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


public class TagApprovalPlace extends Place {
    private String tagId;

    public TagApprovalPlace(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TagApprovalPlace) {
            TagApprovalPlace basePlace = (TagApprovalPlace) other;
            String tagId = basePlace.getTagId();

            return equal(this.tagId, tagId);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;

        if (tagId != null) {
            hash += tagId.hashCode();
        }

        return hash;
    }

    public String toString() {
        String result = getClass().getName() + ": tagId: " + tagId;

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

    public static class Tokenizer implements PlaceTokenizer<TagApprovalPlace> {
        @Override
        public TagApprovalPlace getPlace(String token) {
            return new TagApprovalPlace(token);
        }

        @Override
        public String getToken(TagApprovalPlace place) {
            return place.getTagId();
        }
    }
}
