package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Sets;
import com.google.gwt.place.shared.Place;
import com.googlecode.fspotcloud.testharness.HomePlace;
import com.googlecode.fspotcloud.testharness.MainBuilder;
import com.googlecode.fspotcloud.testharness.OutPlace;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class RelevanceAllPlacesTest {

    Relevance relevance = (new Relevance()).addDefaultKeys(KeyStroke.K);
    PlaceContext outContext = new PlaceContext(OutPlace.class, Sets.<String>newHashSet(MainBuilder.FLAG_LOGGED_ON));
    PlaceContext homeContext = new PlaceContext(HomePlace.class, Sets.<String>newHashSet(MainBuilder.FLAG_LOGGED_ON));
    @Test
    public void testHome() throws Exception {
        List<KeyStroke> keys = relevance.getKeys(homeContext);
        assertEquals(1, keys.size());
    }

    @Test
    public void testOut() throws Exception {
        List<KeyStroke> keys = relevance.getKeys(outContext);
        assertEquals(1, keys.size());
    }
}
