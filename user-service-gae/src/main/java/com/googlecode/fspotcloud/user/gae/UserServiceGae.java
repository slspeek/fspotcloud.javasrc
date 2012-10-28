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

package com.googlecode.fspotcloud.user.gae;

import com.googlecode.fspotcloud.user.UserServiceBase;

import javax.inject.Inject;


public class UserServiceGae extends UserServiceBase {
    @Inject
    com.google.appengine.api.users.UserService delegate;

    @Override
    public String getThirdPartyLoginURL() {
        return delegate.createLoginURL(getPostThirdPartyLoginURL());
    }

    @Override
    public String getThirdPartyLogoutURL() {
        return delegate.createLogoutURL(getPostThirdPartyLogoutURL());
    }

    @Override
    public boolean isUserAdmin() {
        return delegate.isUserLoggedIn() && delegate.isUserAdmin();
    }
}
