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

public class DashboardPlace extends Place {

	public static DashboardPlace DEFAULT = new DashboardPlace("");

	private final String tagId;

	public DashboardPlace(String tagId) {
		this.tagId = tagId;
	}

	public String getTagId() {
		return tagId;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof DashboardPlace) {
			DashboardPlace basePlace = (DashboardPlace) other;
			String tagId = basePlace.getTagId();
			return Objects.equal(this.tagId, tagId);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(tagId);
	}

	public String toString() {
		return Objects.toStringHelper(this).add("tagId", tagId).toString();
	}

	public static class Tokenizer implements PlaceTokenizer<DashboardPlace> {
		@Override
		public DashboardPlace getPlace(String token) {
			return new DashboardPlace(token);
		}

		@Override
		public String getToken(DashboardPlace place) {
			return place.getTagId();
		}
	}
}
