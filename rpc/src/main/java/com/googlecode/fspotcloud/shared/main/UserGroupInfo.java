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
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
@GwtCompatible
public class UserGroupInfo implements Result, IsSerializable, Serializable {
    private Long id;
    private String name;
    private String description;
    private HashSet<String> grantedUsers;
    private boolean isPublic;

    public UserGroupInfo(Long id, String name, String description,
                         boolean aPublic) {
        this.name = name;
        this.description = description;
        this.id = id;
        isPublic = aPublic;
    }

    UserGroupInfo() {
    }

    public Long getId() {
        return id;
    }

    public Set<String> getGrantedUsers() {
        return grantedUsers;
    }

    public void setGrantedUsers(Set<String> grantedUsers) {
        this.grantedUsers = newHashSet(grantedUsers);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("UserGroupInfo");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", grantedUsers=").append(grantedUsers);
        sb.append('}');

        return sb.toString();
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
