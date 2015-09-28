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

public class EditUserGroupPlace extends Place {
	private final long userGroupId;

	public EditUserGroupPlace(long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public long getUserGroupId() {
		return userGroupId;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(userGroupId);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof EditUserGroupPlace))
			return false;

		EditUserGroupPlace that = (EditUserGroupPlace) o;

		if (userGroupId != that.userGroupId)
			return false;

		return true;
	}

	public static class Tokenizer implements PlaceTokenizer<EditUserGroupPlace> {
		@Override
		public EditUserGroupPlace getPlace(String token) {
			return new EditUserGroupPlace(Long.parseLong(token));
		}

		@Override
		public String getToken(EditUserGroupPlace place) {
			return String.valueOf(place.getUserGroupId());
		}
	}
}
