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
import com.googlecode.fspotcloud.shared.main.ResetPasswordResult;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Inject;


public class ResetPasswordHandler extends SimpleActionHandler<ResetPasswordAction, ResetPasswordResult> {

    @Inject
    private UserDao userDao;

    @Override
    public ResetPasswordResult execute(ResetPasswordAction action, ExecutionContext context) throws DispatchException {
        User user = userDao.find(action.getEmail());
        if (user != null && user.hasRegistered()) {
            if (user.getEnabled()) {
                if (action.getSecret().equals(user.emailVerificationSecret())) {
                    user.setCredentials(action.getNewPassword());
                    user.setEmailVerificationSecret(null);
                    userDao.save(user);
                    return new ResetPasswordResult(ResetPasswordResult.Code.SUCCESS);
                } else {
                    return new ResetPasswordResult(ResetPasswordResult.Code.WRONG_CODE);
                }
            } else {
                return new ResetPasswordResult(ResetPasswordResult.Code.NOT_VERIFIED);
            }

        } else {
            return new ResetPasswordResult(ResetPasswordResult.Code.NOT_REGISTERED);
        }
    }
}
