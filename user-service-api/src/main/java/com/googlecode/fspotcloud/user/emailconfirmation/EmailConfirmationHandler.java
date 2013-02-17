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

package com.googlecode.fspotcloud.user.emailconfirmation;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.EmailConfirmationAction;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;


public class EmailConfirmationHandler extends SimpleActionHandler<EmailConfirmationAction, VoidResult> {
    private final UserDao userDao;

    @Inject
    public EmailConfirmationHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public VoidResult execute(EmailConfirmationAction action, ExecutionContext context) throws DispatchException {
        String secret = action.getSecret();
        String email = action.getEmail();
        User user = userDao.find(email);
        final String storedSecret = user.emailVerificationSecret();
        if (secret.equals(storedSecret)) {
            user.setEnabled(true);
            userDao.save(user);

        } else {
            throw new RuntimeException("Verifaction failed");
        }
        return new VoidResult();
    }
}
