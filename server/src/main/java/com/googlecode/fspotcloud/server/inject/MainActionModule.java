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

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.googlecode.fspotcloud.server.main.handler.ApproveTagHandler;
import com.googlecode.fspotcloud.server.main.handler.AuthenticationHandler;
import com.googlecode.fspotcloud.server.main.handler.DeleteUserGroupHandler;
import com.googlecode.fspotcloud.server.main.handler.GetMyUserGroupsHandler;
import com.googlecode.fspotcloud.server.main.handler.GetTagNodeHandler;
import com.googlecode.fspotcloud.server.main.handler.GetTagTreeHandler;
import com.googlecode.fspotcloud.server.main.handler.GetUserGroupHandler;
import com.googlecode.fspotcloud.server.main.handler.GetUserInfoHandler;
import com.googlecode.fspotcloud.server.main.handler.GrantUserHandler;
import com.googlecode.fspotcloud.server.main.handler.LogoutHandler;
import com.googlecode.fspotcloud.server.main.handler.NewUserGroupHandler;
import com.googlecode.fspotcloud.server.main.handler.RequestFullsizeImageHandler;
import com.googlecode.fspotcloud.server.main.handler.ResetPasswordHandler;
import com.googlecode.fspotcloud.server.main.handler.RevokeTagHandler;
import com.googlecode.fspotcloud.server.main.handler.RevokeUserHandler;
import com.googlecode.fspotcloud.server.main.handler.SaveUserGroupHandler;
import com.googlecode.fspotcloud.server.main.handler.SendConfirmationEmailHandler;
import com.googlecode.fspotcloud.server.main.handler.SendPasswordResetHandler;
import com.googlecode.fspotcloud.server.main.handler.SignUpHandler;
import com.googlecode.fspotcloud.server.main.handler.UpdateUserHandler;
import com.googlecode.fspotcloud.server.model.tag.IUserGroupHelper;
import com.googlecode.fspotcloud.server.model.tag.UserGroupHelper;
import com.googlecode.fspotcloud.shared.main.ApproveTagAction;
import com.googlecode.fspotcloud.shared.main.AuthenticationAction;
import com.googlecode.fspotcloud.shared.main.DeleteGroupAction;
import com.googlecode.fspotcloud.shared.main.EmailConfirmationAction;
import com.googlecode.fspotcloud.shared.main.GetMyUserGroupsAction;
import com.googlecode.fspotcloud.shared.main.GetTagNodeAction;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.GetUserGroupAction;
import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.GrantUserAction;
import com.googlecode.fspotcloud.shared.main.LogoutAction;
import com.googlecode.fspotcloud.shared.main.NewUserGroupAction;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import com.googlecode.fspotcloud.shared.main.ResetPasswordAction;
import com.googlecode.fspotcloud.shared.main.RevokeTagAction;
import com.googlecode.fspotcloud.shared.main.RevokeUserAction;
import com.googlecode.fspotcloud.shared.main.SaveUserGroupAction;
import com.googlecode.fspotcloud.shared.main.SendConfirmationEmailAction;
import com.googlecode.fspotcloud.shared.main.SendPasswordResetAction;
import com.googlecode.fspotcloud.shared.main.SignUpAction;
import com.googlecode.fspotcloud.shared.main.UpdateUserAction;
import com.googlecode.fspotcloud.user.emailconfirmation.EmailConfirmationHandler;

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
		bindHandler(DeleteGroupAction.class, DeleteUserGroupHandler.class);
		bindHandler(GetUserGroupAction.class, GetUserGroupHandler.class);
		bindHandler(SaveUserGroupAction.class, SaveUserGroupHandler.class);
		bindHandler(GrantUserAction.class, GrantUserHandler.class);
		bindHandler(RevokeUserAction.class, RevokeUserHandler.class);
		bindHandler(ApproveTagAction.class, ApproveTagHandler.class);
		bindHandler(RevokeTagAction.class, RevokeTagHandler.class);
		bindHandler(GetTagNodeAction.class, GetTagNodeHandler.class);
		bindHandler(RequestFullsizeImageAction.class,
				RequestFullsizeImageHandler.class);
		bindHandler(UpdateUserAction.class, UpdateUserHandler.class);
		bind(IUserGroupHelper.class).to(UserGroupHelper.class);
		bindHandler(EmailConfirmationAction.class,
				EmailConfirmationHandler.class);
		bindHandler(SendConfirmationEmailAction.class,
				SendConfirmationEmailHandler.class);
		bindHandler(SendPasswordResetAction.class,
				SendPasswordResetHandler.class);
		bindHandler(ResetPasswordAction.class, ResetPasswordHandler.class);
	}
}
