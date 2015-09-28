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

public class SendPasswordResetPlace extends Place {

	public SendPasswordResetPlace() {
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SendPasswordResetPlace) {
			return true;
		} else {
			return false;
		}

	}

	public static class Tokenizer
			implements
				PlaceTokenizer<SendPasswordResetPlace> {
		@Override
		public SendPasswordResetPlace getPlace(String token) {
			return new SendPasswordResetPlace();
		}

		@Override
		public String getToken(SendPasswordResetPlace place) {
			return "";
		}
	}
}
