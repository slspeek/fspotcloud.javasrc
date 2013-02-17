package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.model.jpa.user.UserEntity;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.main.ResetPasswordAction;
import com.googlecode.fspotcloud.shared.main.ResetPasswordResult;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class ResetPasswordHandlerTest {

    public static final String RMS_FSF_ORG = "rms@fsf.org";
    public static final String BOO = "boo";
    @Inject
    private UserDao userDao;

    @Inject
    private ResetPasswordHandler handler;
    private User rms = new UserEntity(RMS_FSF_ORG);
    private ResetPasswordAction action = new ResetPasswordAction(RMS_FSF_ORG, BOO, "no_password");

    @Test
    public void testNotRegistered() throws Exception {
        ResetPasswordResult result = handler.execute(action, null);
        Assert.assertEquals(ResetPasswordResult.Code.NOT_REGISTERED, result.getCode());
    }

    @Test
    public void testNotRegistered2() throws Exception {
        when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
        rms.setRegistered(false);
        rms.setEnabled(true);
        ResetPasswordResult result = handler.execute(action, null);
        Assert.assertEquals(ResetPasswordResult.Code.NOT_REGISTERED, result.getCode());
    }

    @Test
    public void testNotVerified() throws Exception {
        when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
        rms.setRegistered(true);
        rms.setEnabled(false);
        ResetPasswordResult result = handler.execute(action, null);
        Assert.assertEquals(ResetPasswordResult.Code.NOT_VERIFIED, result.getCode());
    }

    @Test
    public void testSuccess() throws Exception {
        when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
        rms.setRegistered(true);
        rms.setEnabled(true);
        rms.setEmailVerificationSecret(BOO);
        ResetPasswordResult result = handler.execute(action, null);
        Assert.assertEquals(ResetPasswordResult.Code.SUCCESS, result.getCode());
        verify(userDao).save(rms);
        assertNull(rms.emailVerificationSecret());

    }

    @Test
    public void testWrongCode() throws Exception {
        when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
        rms.setRegistered(true);
        rms.setEnabled(true);
        rms.setEmailVerificationSecret("");
        ResetPasswordResult result = handler.execute(action, null);
        Assert.assertEquals(ResetPasswordResult.Code.WRONG_CODE, result.getCode());
        verify(userDao).find(RMS_FSF_ORG);
        verifyNoMoreInteractions(userDao);
        assertNotNull(rms.emailVerificationSecret());

    }
}
