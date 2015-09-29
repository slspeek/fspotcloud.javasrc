package com.googlecode.fspotcloud.server.main.handler;

import static org.mockito.Mockito.verify;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.shared.main.LogoutAction;
import com.googlecode.fspotcloud.user.ILoginMetaDataUpdater;

@RunWith(JukitoRunner.class)
public class LogoutHandlerTest {

	@Inject
	LogoutHandler handler;

	@Inject
	private ILoginMetaDataUpdater loginMetaDataUpdater;

	@Test
	public void testExecute() throws Exception {
		handler.execute(new LogoutAction(), null);
		verify(loginMetaDataUpdater).clear();
	}
}
