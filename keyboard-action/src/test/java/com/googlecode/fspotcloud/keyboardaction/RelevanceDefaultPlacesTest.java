package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gwt.place.shared.Place;
import com.googlecode.fspotcloud.testharness.HomePlace;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class RelevanceDefaultPlacesTest {

    Relevance relevance = new Relevance(newArrayList(KeyStroke.K),
            Lists.<Class<? extends Place>>newArrayList(HomePlace.class));

    PlaceContext placeContext = new PlaceContext(HomePlace.class, Sets.<String>newHashSet());

    @Test
    public void testGetKeys() throws Exception {
        List<Class<? extends Place>> places = relevance.getDefaultPlaces();
        assertEquals(1, places.size());
    }
}
