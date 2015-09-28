package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(JukitoRunner.class)
public class ModeControllerProviderTest {

	@Inject
	private ModeControllerProvider provider;

	@Test
	public void testGet() throws Exception {
		IModeController controller = provider.get();
		assertNotNull(controller);

	}
}
