package com.googlecode.fspotcloud.client.place;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.enduseraction.navigation.NavigationActions;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import org.junit.Test;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(JukitoRunner.class)
public class MoveActionMapTest {

    @Inject
    private MoveActionMap map;

    @Test
    public void testGetActionId() throws Exception {

        assertEquals(Flags.CAN_GO_END.name(), map.getActionId(new Move(Navigator.Direction.FORWARD, Navigator.Unit.BORDER)));


    }
}
