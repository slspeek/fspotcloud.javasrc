package com.googlecode.fspotcloud.server.main.handler;

import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.model.jpa.user.UserEntity;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.main.SendPasswordResetAction;
import com.googlecode.fspotcloud.shared.main.SendPasswordResetResult;
import com.googlecode.fspotcloud.user.emailconfirmation.MailGenerator;
import com.googlecode.fspotcloud.user.emailconfirmation.SecretGenerator;

@RunWith(JukitoRunner.class)
public class SendPasswordResetHandlerTest {

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
	private SendPasswordResetHandler handler;

	private SendPasswordResetAction action = new SendPasswordResetAction(
			RMS_FSF_ORG);

	private User rms = new UserEntity(RMS_FSF_ORG);

	@Test
	public void testNotRegistered() throws Exception {
		SendPasswordResetResult result = handler.execute(action, null);
		Assert.assertEquals(SendPasswordResetResult.Code.NOT_REGISTERED,
				result.getCode());
	}

	@Test
	public void testNotRegistered2() throws Exception {
		when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
		rms.setRegistered(false);
		SendPasswordResetResult result = handler.execute(action, null);
		Assert.assertEquals(SendPasswordResetResult.Code.NOT_REGISTERED,
				result.getCode());
	}

	@Test
	public void testNotVerified() throws Exception {
		when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
		rms.setRegistered(true);
		rms.setEnabled(false);
		SendPasswordResetResult result = handler.execute(action, null);
		Assert.assertEquals(SendPasswordResetResult.Code.NOT_VERIFIED,
				result.getCode());
	}

	@Test
	public void testSuccess() throws Exception {
		when(userDao.find(RMS_FSF_ORG)).thenReturn(rms);
		rms.setRegistered(true);
		rms.setEnabled(true);
		SendPasswordResetResult result = handler.execute(action, null);
		Assert.assertEquals(SendPasswordResetResult.Code.SUCCESS,
				result.getCode());
		assertNull(rms.emailVerificationSecret());
	}

}
