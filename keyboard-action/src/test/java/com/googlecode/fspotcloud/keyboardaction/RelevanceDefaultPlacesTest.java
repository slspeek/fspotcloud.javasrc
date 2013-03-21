package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;
import com.googlecode.fspotcloud.testharness.HomePlace;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class RelevanceDefaultPlacesTest {

    Relevance relevance = (new Relevance(HomePlace.class)).addDefaultKeys(KeyStroke.K);
    @Test
    public void testGetKeys() throws Exception {
        List<Class<? extends Place>> places = relevance.getDefaultPlaces();
        assertEquals(1, places.size());
    }
}
