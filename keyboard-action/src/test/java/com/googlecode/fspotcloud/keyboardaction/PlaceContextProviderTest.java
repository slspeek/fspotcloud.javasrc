package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class PlaceContextProviderTest {

    @Inject
    private PlaceContextProvider placeContextProvider;

    @Inject
    private IModeController modeController;

    @Inject
    private IPlaceController placeController;

    @Before
    public void setUp() throws Exception {
        when(modeController.getFlags()).thenReturn(newHashSet("foo"));
        when(placeController.getWhere()).thenReturn(Place.NOWHERE);
    }

    @Test
    public void testGet() throws Exception {
        PlaceContext placeContext = placeContextProvider.get();
        assertEquals(Place.NOWHERE.getClass(), placeContext.getPlace());
        assertEquals("foo", placeContext.getFlags().iterator().next());
    }
}
