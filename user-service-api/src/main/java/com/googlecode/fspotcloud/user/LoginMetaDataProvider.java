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

package com.googlecode.fspotcloud.user;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;


public class LoginMetaDataProvider implements Provider<LoginMetaData> {
    public static final String LOGIN_META_DATA = "login-meta-data";
    @Inject
    HttpSession session;

    @Override
    public LoginMetaData get() {
        LoginMetaData stored = (LoginMetaData) session.getAttribute(LOGIN_META_DATA);

        if (stored == null) {
            stored = new LoginMetaData();
            session.setAttribute(LOGIN_META_DATA, stored);
        }

        return stored;
    }
}
