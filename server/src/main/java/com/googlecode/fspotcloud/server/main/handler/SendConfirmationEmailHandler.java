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

import com.google.inject.Provider;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.main.SendConfirmationEmailAction;
import com.googlecode.fspotcloud.shared.main.SendConfirmationEmailResult;
import com.googlecode.fspotcloud.user.emailconfirmation.MailGenerator;
import com.googlecode.fspotcloud.user.emailconfirmation.SecretGenerator;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Inject;


public class SendConfirmationEmailHandler extends SimpleActionHandler<SendConfirmationEmailAction, SendConfirmationEmailResult> {
    @Inject
    private UserDao userDao;
    @Inject
    private Provider<IMail> mailerProvider;
    @Inject
    private MailGenerator mailGenerator;
    @Inject
    private Provider<SecretGenerator> secretGeneratorProvider;

    @Override
    public SendConfirmationEmailResult execute(SendConfirmationEmailAction action, ExecutionContext context) throws DispatchException {
        final String email = action.getEmail();
        User mayBeExisted = userDao.find(email);

        if (mayBeExisted != null && mayBeExisted.hasRegistered()) {
            final User registeredUser = mayBeExisted;
            String emailConfirmationSecret = secretGeneratorProvider.get().getSecret(email);
            registeredUser.setEmailVerificationSecret(emailConfirmationSecret);
            userDao.save(registeredUser);

            String confirmationMail = mailGenerator.getConfirmationMailBody(email,
                    emailConfirmationSecret);
            mailerProvider.get().send(email, "F-Spot Cloud email confirmation",
                    confirmationMail);
            return new SendConfirmationEmailResult(SendConfirmationEmailResult.Code.SUCCESS);
        } else {
            return new SendConfirmationEmailResult(SendConfirmationEmailResult.Code.NOT_REGISTERED);
        }
    }
}
