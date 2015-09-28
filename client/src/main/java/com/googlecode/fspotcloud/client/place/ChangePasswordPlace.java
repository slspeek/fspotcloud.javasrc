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

public class ChangePasswordPlace extends Place {

	private String secret;
	private String email;

	public ChangePasswordPlace(String email, String secret) {
		this.secret = secret;
		this.email = email;
	}

	public String getSecret() {
		return secret;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.secret, this.email);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ChangePasswordPlace) {
			ChangePasswordPlace emailConfirmationPlace = (ChangePasswordPlace) obj;
			return Objects.equal(emailConfirmationPlace.email, email)
					&& Objects.equal(emailConfirmationPlace.secret, secret);
		} else {
			return false;
		}

	}

	public static class Tokenizer
			implements
				PlaceTokenizer<ChangePasswordPlace> {
		@Override
		public ChangePasswordPlace getPlace(String token) {
			String[] arguments = token.split(":");
			return new ChangePasswordPlace(arguments[0], arguments[1]);
		}

		@Override
		public String getToken(ChangePasswordPlace place) {
			return place.getEmail() + ":" + place.getSecret();
		}
	}
}
