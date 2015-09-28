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

package com.googlecode.fspotcloud.shared.main;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import net.customware.gwt.dispatch.shared.Action;

@GwtCompatible
public class EmailConfirmationAction implements Action<VoidResult> {
	private String email;
	private String secret;

	public EmailConfirmationAction() {
	}

	public EmailConfirmationAction(String email, String secret) {
		this.email = email;
		this.secret = secret;
	}

	public String getEmail() {
		return email;
	}

	public String getSecret() {
		return secret;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("email", email)
				.add("secret", secret).toString();

	}

	@Override
	public int hashCode() {
		return Objects.hashCode(email, secret);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmailConfirmationAction) {
			EmailConfirmationAction other = (EmailConfirmationAction) obj;
			return Objects.equal(email, other.getEmail())
					&& Objects.equal(secret, other.getSecret());
		}
		return false;
	}
}
