package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.OpenNewTab;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class GoRssFeedHandlerTest {

    @Inject
    private IPlaceController placeController;
    @Inject
    private OpenNewTab loader;
    @Inject
    private GoRssFeedHandler handler;
    @Inject
    private NavigationActions navigationActions;
    private BasePlace place = new BasePlace("1", "1000");

    @Before
    public void setUp() throws Exception {
        when(placeController.where()).thenReturn(place);

    }

    @Test
    public void testPerformAction() throws Exception {
        handler.performAction(navigationActions.rss_feed.getId());

        verify(loader).setLocation("/rss?tag=" + place.getTagId());
        verify(placeController).where();
        verifyNoMoreInteractions(loader, placeController);

    }
}
