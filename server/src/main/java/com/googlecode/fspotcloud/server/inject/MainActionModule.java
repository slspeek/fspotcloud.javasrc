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

package com.googlecode.fspotcloud.server.inject;

import com.googlecode.fspotcloud.server.main.handler.*;
import com.googlecode.fspotcloud.server.model.tag.IUserGroupHelper;
import com.googlecode.fspotcloud.server.model.tag.UserGroupHelper;
import com.googlecode.fspotcloud.shared.main.*;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;


public class MainActionModule extends ActionHandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(GetUserInfo.class, GetUserInfoHandler.class);
        bindHandler(GetTagTreeAction.class, GetTagTreeHandler.class);
        bindHandler(AuthenticationAction.class, AuthenticationHandler.class);
        bindHandler(SignUpAction.class, SignUpHandler.class);
        bindHandler(LogoutAction.class, LogoutHandler.class);
        bindHandler(GetMyUserGroupsAction.class, GetMyUserGroupsHandler.class);
        bindHandler(NewUserGroupAction.class, NewUserGroupHandler.class);
        bindHandler(DeleteUserGroupAction.class, DeleteUserGroupHandler.class);
        bindHandler(GetUserGroupAction.class, GetUserGroupHandler.class);
        bindHandler(SaveUserGroupAction.class, SaveUserGroupHandler.class);
        bindHandler(GrantUserAction.class, GrantUserHandler.class);
        bindHandler(RevokeUserAction.class, RevokeUserHandler.class);
        bindHandler(ApproveTagAction.class, ApproveTagHandler.class);
        bindHandler(RevokeTagAction.class, RevokeTagHandler.class);
        bindHandler(GetTagNodeAction.class, GetTagNodeHandler.class);
        bindHandler(RequestFullsizeImageAction.class,
                RequestFullsizeImageHandler.class);

        bind(IUserGroupHelper.class).to(UserGroupHelper.class);
    }
}
