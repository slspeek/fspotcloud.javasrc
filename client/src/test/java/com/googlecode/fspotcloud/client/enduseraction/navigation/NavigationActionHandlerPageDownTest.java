package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(JukitoRunner.class)
public class NavigationActionHandlerPageDownTest {

    @Inject
    private NavigationActionHandler handler;

    @Inject
    private Navigator navigator;


    @Test
    public void testPerformAction() throws Exception {
        handler.performAction(NavigationActions.PAGE_DOWN_ID);
        verify(navigator).goAsync(Navigator.Direction.FORWARD, Navigator.Unit.PAGE);
        verifyNoMoreInteractions(navigator);
    }
}