package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.googlecode.fspotcloud.client.enduseraction.Modes;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.ModesProvider;
import com.googlecode.fspotcloud.keyboardaction.SimpleModesProvider;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(JukitoRunner.class)
public class NavigationActionHandlerRowDownTest {




    @Inject
    private NavigationActionHandler handler;

    @Inject
    private Navigator navigator;


    @Test
    public void testPerformAction() throws Exception {
        handler.performAction(NavigationActions.ROW_DOWN_ID);
        verify(navigator).goAsync(Navigator.Direction.FORWARD, Navigator.Unit.ROW);
        verifyNoMoreInteractions(navigator);
    }
}
