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
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;

import static com.google.common.collect.Sets.newHashSet;

public class LoginMetaData implements ILoginMetaData {
    public static final String GRANTED_GROUPS = "granted-groups";
    public static final String EMAIL_FIELD = "email-field";
    public static final String LOGIN_TYPE = "login-type";
    public static final String LAST_SEEN = "last-seen";
    @Inject
    private HttpSession session;

    @Override
    public HashSet<Long> getGrantedUserGroups() {
        HashSet<Long> result = (HashSet<Long>) session.getAttribute(GRANTED_GROUPS);

        if (result == null) {
            result = newHashSet();
        }

        return result;
    }

    @Override
    public void setGrantedUserGroups(HashSet<Long> grantedUserGroups) {
        session.setAttribute(GRANTED_GROUPS, grantedUserGroups);
    }

    @Override
    public String getEmail() {
        String email = (String) session.getAttribute(EMAIL_FIELD);

        return email;
    }

    @Override
    public void setEmail(String email) {
        session.setAttribute(EMAIL_FIELD, email);
    }

    @Override
    public Type getLoginType() {
        return (Type) session.getAttribute(LOGIN_TYPE);
    }

    @Override
    public void setLoginType(Type loginType) {
        session.setAttribute(LOGIN_TYPE, loginType);
    }

    @Override
    public Date getLastTime() {
        return (Date) session.getAttribute(LAST_SEEN);
    }

    @Override
    public void setLastTime(Date lastTime) {
        session.setAttribute(LAST_SEEN, lastTime);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("LoginMetaData");
        sb.append("{loginType=").append(getLoginType());
        sb.append(", lastTime=").append(getLastTime());
        sb.append(", email='").append(getEmail()).append('\'');
        sb.append(", grantedUserGroups=").append(getGrantedUserGroups());
        sb.append("}@");
        sb.append(super.toString());

        return sb.toString();
    }
}
