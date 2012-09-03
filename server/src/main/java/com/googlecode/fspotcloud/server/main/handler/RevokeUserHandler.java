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
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.server.model.api.UserGroup;
import com.googlecode.fspotcloud.server.model.api.UserGroupDao;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.RevokeUserAction;
import com.googlecode.fspotcloud.user.IAdminPermission;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import java.util.Set;


public class RevokeUserHandler extends SimpleActionHandler<RevokeUserAction, VoidResult> {
    private final UserGroupDao userGroupDao;
    private final UserDao userDao;
    private final IAdminPermission adminPermission;

    @Inject
    public RevokeUserHandler(UserGroupDao userGroupDao, UserDao userDao,
                             IAdminPermission adminPermission) {
        this.userGroupDao = userGroupDao;
        this.userDao = userDao;
        this.adminPermission = adminPermission;
    }

    @Override
    public VoidResult execute(RevokeUserAction action, ExecutionContext context)
            throws DispatchException {
        adminPermission.checkAdminPermission();

        User user = userDao.findOrNew(action.getEmail());
        Set<Long> grantedToUser = user.getGrantedUserGroups();
        grantedToUser.remove(action.getUserGroupId());
        user.setGrantedUserGroups(grantedToUser);
        userDao.save(user);

        UserGroup group = userGroupDao.find(action.getUserGroupId());

        if (group != null) {
            Set<String> userInGroup = group.getGrantedUsers();
            userInGroup.remove(action.getEmail());
            group.setGrantedUsers(userInGroup);
            userGroupDao.save(group);
        }

        return new VoidResult();
    }
}
