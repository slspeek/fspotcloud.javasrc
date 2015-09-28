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

public class MailFullsizePlace extends Place {

	private String photoId;
	private String tagId;

	public MailFullsizePlace(String tagId, String photoId) {
		this.photoId = photoId;
		this.tagId = tagId;
	}

	public String getPhotoId() {
		return photoId;
	}

	public String getTagId() {
		return tagId;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(photoId, tagId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MailFullsizePlace) {
			MailFullsizePlace mailFullsizePlace = (MailFullsizePlace) obj;
			return Objects.equal(mailFullsizePlace.photoId, photoId)
					&& Objects.equal(mailFullsizePlace.tagId, tagId);
		} else {
			return false;
		}

	}

	public static class Tokenizer implements PlaceTokenizer<MailFullsizePlace> {
		@Override
		public MailFullsizePlace getPlace(String token) {
			String[] splitted = token.split(":");
			return new MailFullsizePlace(splitted[0], splitted[1]);
		}

		@Override
		public String getToken(MailFullsizePlace place) {
			return place.getTagId() + ":" + place.getPhotoId();
		}
	}
}
