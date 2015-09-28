package com.googlecode.fspotcloud.client.place;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class MoveActionMapTest {

	@Inject
	private MoveActionMap map;

	@Test
	public void testGetActionId() throws Exception {

		assertEquals(Flags.CAN_GO_END.name(), map.getActionId(new Move(
				Navigator.Direction.FORWARD, Navigator.Unit.BORDER)));

	}
}
