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
import com.googlecode.fspotcloud.server.model.api.UserGroup;
import com.googlecode.fspotcloud.server.model.api.UserGroupDao;
import com.googlecode.fspotcloud.shared.main.GetMyUserGroupsAction;
import com.googlecode.fspotcloud.shared.main.GetMyUserGroupsResult;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class GetMyUserGroupsHandler extends SimpleActionHandler<GetMyUserGroupsAction, GetMyUserGroupsResult> {
    private final UserGroupDao userGroupDao;
    private final UserService userService;

    @Inject
    public GetMyUserGroupsHandler(UserGroupDao userGroupDao,
                                  UserService userService) {
        this.userGroupDao = userGroupDao;
        this.userService = userService;
    }

    @Override
    public GetMyUserGroupsResult execute(GetMyUserGroupsAction action,
                                         ExecutionContext context) throws DispatchException {
        if (userService.isUserLoggedIn()) {
            String userName = userService.getEmail();
            List<UserGroup> result = userGroupDao.findAllWhere(1000,
                    "owner='" + userName + "'");
            ArrayList<UserGroupInfo> data = newArrayList();

            for (UserGroup group : result) {
                data.add(get(group));
            }

            return new GetMyUserGroupsResult(data);
        } else {
            return new GetMyUserGroupsResult();
        }
    }

    private UserGroupInfo get(UserGroup group) {
        UserGroupInfo info = new UserGroupInfo(group.getId(), group.getName(),
                group.getDescription(), group.isPublic());

        return info;
    }
}
