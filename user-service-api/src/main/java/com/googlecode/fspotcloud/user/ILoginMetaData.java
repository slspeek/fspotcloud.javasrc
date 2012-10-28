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

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;


/**
 * Created with IntelliJ IDEA.
 * User: steven
 * Date: 14-6-12
 * Time: 18:40
 * To change this template use File | Settings | File Templates.
 */
public interface ILoginMetaData extends Serializable {
    HashSet<Long> getGrantedUserGroups();

    void setGrantedUserGroups(HashSet<Long> grantedUserGroups);

    String getEmail();

    void setEmail(String email);

    Type getLoginType();

    void setLoginType(Type loginType);

    Date getLastTime();

    void setLastTime(Date lastTime);

    public enum Type {
        REGULAR_LOGIN, OPEN_ID_LOGIN, GAE_LOGIN;
    }
}
