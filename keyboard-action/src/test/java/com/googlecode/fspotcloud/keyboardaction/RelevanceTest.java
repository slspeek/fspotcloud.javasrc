package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Sets;
import com.googlecode.fspotcloud.testharness.HomePlace;
import com.googlecode.fspotcloud.testharness.OutPlace;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class RelevanceTest {

    Relevance relevance = (new Relevance(HomePlace.class)).addDefaultKeys(KeyStroke.K);
    PlaceContext placeContext = new PlaceContext(HomePlace.class, Sets.<String>newHashSet());

    @Test
    public void testGetKeys() throws Exception {
        List<KeyStroke> keys = relevance.getKeys(placeContext);
        assertEquals(1, keys.size());
    }

    @Test
    public void testAddTwoRuleOnSamePlace() throws Exception {
        relevance.addRule(OutPlace.class, KeyStroke.ESC);
        relevance.addRule(OutPlace.class, KeyStroke.ESC);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Relevance{defaultKeys=[], defaultPlaces=[], overrides={}}", new Relevance().toString());

    }
}
