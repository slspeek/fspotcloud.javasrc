package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IModeController;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(JukitoRunner.class)
public class NavigationFlagsHelperTest {

	@Inject
	private ArgumentCaptor<AsyncCallback<Map<Move, Boolean>>> captor;
	@Inject
	private NavigationFlagsHelper helper;
	@Inject
	private Navigator navigator;
	@Inject
	private IModeController modeController;
	@Inject
	private ArgumentCaptor<Map<String, Boolean>> flagsCaptor;

	@Test
	public void testUpdate() throws Exception {
		helper.update();

		verify(navigator).getPossibleMoves(captor.capture());

		final HashMap<Move, Boolean> hashMap = newHashMap();
		hashMap.put(
				new Move(Navigator.Direction.FORWARD, Navigator.Unit.SINGLE),
				true);
		captor.getValue().onSuccess(hashMap);
		verify(modeController).setFlags(flagsCaptor.capture());

		final Map<String, Boolean> flagsCaptorValue = flagsCaptor.getValue();
		assertTrue(flagsCaptorValue.get(Flags.CAN_GO_NEXT_IMAGE.name()));
	}

	@Test
	public void testUpdateFailure() throws Exception {
		helper.update();

		verify(navigator).getPossibleMoves(captor.capture());

		captor.getValue().onFailure(new RuntimeException());
		verifyZeroInteractions(modeController);
	}
}
