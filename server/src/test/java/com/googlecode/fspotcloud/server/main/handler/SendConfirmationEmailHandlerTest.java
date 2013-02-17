package com.googlecode.fspotcloud.server.main.handler;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.model.jpa.user.UserEntity;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.main.SendConfirmationEmailAction;
import com.googlecode.fspotcloud.shared.main.SendConfirmationEmailResult;
import com.googlecode.fspotcloud.user.emailconfirmation.MailGenerator;
import com.googlecode.fspotcloud.user.emailconfirmation.SecretGenerator;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class SendConfirmationEmailHandlerTest {

    public static final String RMS_FSF_ORG = "rms@fsf.org";
    @Inject
    private UserDao userDao;
    @Inject
    private IMail mailer;
    @Inject
    private MailGenerator mailGenerator;
    @Inject
    private Provider<SecretGenerator> secretGeneratorProvider;
    @Inject
    private SendConfirmationEmailHandler handler;
    private SendConfirmationEmailAction action = new SendConfirmationEmailAction(RMS_FSF_ORG);

    private User rms = new UserEntity(RMS_FSF_ORG);

    @Test
    public void testNotRegistered() throws Exception {
        SendConfirmationEmailResult result = handler.execute(action, null);
        Assert.assertEquals(SendConfirmationEmailResult.Code.NOT_REGISTERED, result.getCode());
    }

    @Test
    public void testNotRegistered2() throws Exception {
        when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
        rms.setRegistered(false);
        SendConfirmationEmailResult result = handler.execute(action, null);
        Assert.assertEquals(SendConfirmationEmailResult.Code.NOT_REGISTERED, result.getCode());
    }

    @Test
    public void testSuccess() throws Exception {
        when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
        rms.setRegistered(true);
        rms.setEnabled(true);
        SendConfirmationEmailResult result = handler.execute(action, null);
        Assert.assertEquals(SendConfirmationEmailResult.Code.SUCCESS, result.getCode());
        verify(userDao).save(rms);
        assertNull(rms.emailVerificationSecret());
    }

}
