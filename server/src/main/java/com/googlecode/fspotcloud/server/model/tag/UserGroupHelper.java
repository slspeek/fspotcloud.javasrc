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

package com.googlecode.fspotcloud.server.model.tag;

import com.googlecode.fspotcloud.server.model.api.UserGroup;
import com.googlecode.fspotcloud.server.model.api.UserGroupDao;
import com.googlecode.fspotcloud.user.LoginMetaData;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class UserGroupHelper implements IUserGroupHelper {
    @Inject
    private Provider<LoginMetaData> metaData;
    @Inject
    private UserGroupDao userGroupDao;

    @Override
    public Set<String> getVisibleTagIds() {
        Set<String> grantedTags = getPublicTags();
        Set<Long> userGroupIds = metaData.get().getGrantedUserGroups();

        for (Long id : userGroupIds) {
            UserGroup userGroup = userGroupDao.find(id);

            if (userGroup != null) {
                grantedTags.addAll(userGroup.getApprovedTagIds());
            }
        }

        return grantedTags;
    }

    @Override
    public boolean containsOneOf(Set<String> tagIds) {
        Set<String> visible = getVisibleTagIds();

        for (String id : tagIds) {
            if (visible.contains(id)) {
                return true;
            }
        }

        return false;
    }

    private Set<String> getPublicTags() {
        Set<String> publicTags = newHashSet();
        List<UserGroup> publicUserGroups = userGroupDao.findAllWhere(1000,
                "isPublic = true");

        for (UserGroup pGroup : publicUserGroups) {
            publicTags.addAll(pGroup.getApprovedTagIds());
        }

        return publicTags;
    }
}
