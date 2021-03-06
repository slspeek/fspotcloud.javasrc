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
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.SaveUserGroupAction;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;


public class SaveUserGroupHandler extends SimpleActionHandler<SaveUserGroupAction, VoidResult> {
    private final UserService userService;
    private final UserGroupDao userGroupDao;

    @Inject
    public SaveUserGroupHandler(UserService userService,
                                UserGroupDao userGroupDao) {
        this.userService = userService;
        this.userGroupDao = userGroupDao;
    }

    @Override
    public VoidResult execute(SaveUserGroupAction action,
                              ExecutionContext context) throws DispatchException {
        if (userService.isUserLoggedIn()) {
            String userName = userService.getEmail();
            UserGroup userGroup = userGroupDao.find(action.getInfoToSave()
                    .getId());
            if (userGroup != null) {
                if (userName.equals(userGroup.getOwner())) {
                    userGroup.setName(action.getInfoToSave().getName());
                    userGroup.setDescription(action.getInfoToSave().getDescription());
                    userGroup.setPublic(action.getInfoToSave().isPublic());
                    userGroupDao.save(userGroup);
                } else {
                    throw new UserIsNotOwnerException();
                }
            } else {
                throw new UsergroupNotFoundException();
            }
        } else {
            throw new UserIsNotLoggedException();
        }
        return new VoidResult();
    }
}
