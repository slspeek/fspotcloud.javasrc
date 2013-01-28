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

import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.main.ResetPasswordAction;
import com.googlecode.fspotcloud.shared.main.UpdateUserAction;
import com.googlecode.fspotcloud.shared.main.UpdateUserResult;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Inject;


public class ResetPasswordHandler extends SimpleActionHandler<ResetPasswordAction, UpdateUserResult> {

    @Inject
    private UserDao userDao;

    @Override
    public UpdateUserResult execute(ResetPasswordAction action, ExecutionContext context) throws DispatchException {
            User user = userDao.find(action.getEmail());
            if (user.hasRegistered()) {
                if (user.getEnabled()) {
                    if (action.getSecret().equals(user.emailVerificationSecret())) {
                        user.setCredentials(action.getNewPassword());
                        user.setEmailVerificationSecret("");
                        userDao.save(user);
                        return new UpdateUserResult(true);
                    } else {
                        return new UpdateUserResult(false);
                    }
                } else {
                    throw new EmailNotVerifiedException();
                }
            } else {
                throw new UserNotRegisteredException();
            }
    }
}
