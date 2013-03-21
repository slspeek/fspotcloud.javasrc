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

package com.googlecode.fspotcloud.test;

import com.google.guiceberry.controllable.InjectionController;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;

import static com.googlecode.fspotcloud.server.util.DigestTool.hash;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SendPasswordResetITest {

    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    private SendPasswordResetPage sendPasswordResetPage;
    @Inject
    private InjectionController<IMail> mailInjectionController;

    @Inject
    private UserDao userDao;

    private ArgumentCaptor<String> recipient = ArgumentCaptor.forClass(String.class);
    private ArgumentCaptor<String> subject = ArgumentCaptor.forClass(String.class);
    private ArgumentCaptor<String> body = ArgumentCaptor.forClass(String.class);


    @Test
    public void testReset() throws Exception {
        //emailConfirmationITest.testEmailConfirmation();
        User rms = userDao.findOrNew(ILogin.RMS_FSF_ORG);
        rms.setCredentials(hash(ILogin.RMS_FSF_ORG, ILogin.RMS_CRED));
        rms.setRegistered(true);
        rms.setEnabled(true);
        userDao.save(rms);
        IMail mailerMock = mock(IMail.class);
        mailInjectionController.setOverride(mailerMock);
        sendPasswordResetPage.open();
        sendPasswordResetPage.submitEmail(ILogin.RMS_FSF_ORG);

        verify(mailerMock).send(recipient.capture(), subject.capture(), body.capture());
        Assert.assertEquals(ILogin.RMS_FSF_ORG, recipient.getValue());
    }
}
