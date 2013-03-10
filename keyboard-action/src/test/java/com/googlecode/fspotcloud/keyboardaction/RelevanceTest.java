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
public class RelevanceTest {

    Relevance relevance = new Relevance(newArrayList(KeyStroke.K),
            Lists.<Class<? extends Place>>newArrayList(HomePlace.class));

    PlaceContext placeContext = new PlaceContext(HomePlace.class, Sets.<String>newHashSet());


    @Test
    public void testGetKeys() throws Exception {
        List<KeyStroke> keys = relevance.getKeys(placeContext);
        assertEquals(1, keys.size());
    }
}
