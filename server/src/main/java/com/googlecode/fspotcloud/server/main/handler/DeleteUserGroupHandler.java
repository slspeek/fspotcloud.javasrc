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

package com.googlecode.fspotcloud.server.main.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.server.model.api.*;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.DeleteUserGroupAction;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import java.util.Set;


public class DeleteUserGroupHandler extends SimpleActionHandler<DeleteUserGroupAction, VoidResult> {
    @Inject
    private UserGroupDao userGroupDao;
    @Inject
    private UserService userService;
    @Inject
    private UserDao userDao;
    @Inject
    private TagDao tagDao;

    @Override
    public VoidResult execute(DeleteUserGroupAction action,
                              ExecutionContext context) throws DispatchException {
        if (userService.isUserLoggedIn()) {
            String userName = userService.getEmail();
            final Long id = action.getId();
            UserGroup group = userGroupDao.find(id);

            if (userName.equals(group.getOwner())) {
                doDelete(group, id);
            }
        }

        return new VoidResult();
    }

    private void doDelete(UserGroup group, Long id) {
        Set<String> grantedUsers = group.getGrantedUsers();

        for (String userEmail : grantedUsers) {
            User user = userDao.find(userEmail);

            if (user != null) {
                Set<Long> grantedGroups = user.getGrantedUserGroups();
                grantedGroups.remove(id);
                user.setGrantedUserGroups(grantedGroups);
                userDao.save(user);
            }
        }

        Set<String> tags = group.getApprovedTagIds();

        for (String tagId : tags) {
            Tag tag = tagDao.find(tagId);

            if (tag != null) {
                Set<Long> grantedGroups = tag.getApprovedUserGroups();
                grantedGroups.remove(id);
                tag.setApprovedUserGroups(grantedGroups);
                tagDao.save(tag);
            }
        }

        userGroupDao.delete(group);
    }
}
