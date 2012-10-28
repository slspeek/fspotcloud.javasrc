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

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.googlecode.fspotcloud.shared.main;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;

import java.io.Serializable;
import java.util.Date;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
@GwtCompatible
public class UserInfo implements Result, IsSerializable, Serializable {
    private String email;
    private boolean isAdmin;
    private boolean isLoggedId;
    private String logoutUrl;
    private String loginUrl;
    private Date lastLoginTime;
    private String loginType;

    public UserInfo(String email, boolean isAdmin, boolean isLoggedId,
                    String loginUrl, String logoutUrl, Date loginTime, String loginType) {
        this.email = email;
        this.isAdmin = isAdmin;
        this.isLoggedId = isLoggedId;
        this.loginUrl = loginUrl;
        this.logoutUrl = logoutUrl;
        lastLoginTime = loginTime;
        this.loginType = loginType;
    }

    UserInfo() {
    }

    public String getLoginType() {
        return loginType;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isLoggedIn() {
        return isLoggedId;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("UserInfo");
        sb.append("{email='").append(email).append('\'');
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", isLoggedId=").append(isLoggedId);
        sb.append(", logoutUrl='").append(logoutUrl).append('\'');
        sb.append(", loginUrl='").append(loginUrl).append('\'');
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", loginType='").append(loginType).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
