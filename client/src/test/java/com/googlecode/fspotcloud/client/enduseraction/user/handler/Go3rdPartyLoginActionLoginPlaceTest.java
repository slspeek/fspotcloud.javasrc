package com.googlecode.fspotcloud.client.enduseraction.user.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class Go3rdPartyLoginActionLoginPlaceTest {

	public static final String FSF_ORG = "fsf.org";
	@Inject
	private Go3rdPartyLoginHandler handler;
	@Inject
	private IClientLoginManager clientLoginManager;
	@Inject
	private IPlaceController placeController;
	@Inject
	private UserActions userActions;

	private LoginPlace loginPlace = new LoginPlace(FSF_ORG);

	@Before
	public void setUp() throws Exception {
		when(placeController.getRawWhere()).thenReturn(loginPlace);
	}

	@Test
	public void testLogin() throws Exception {
		handler.performAction(userActions.otherLogin.getId());
		verify(clientLoginManager).goTo3rdPartyLogin(FSF_ORG);
	}
}
