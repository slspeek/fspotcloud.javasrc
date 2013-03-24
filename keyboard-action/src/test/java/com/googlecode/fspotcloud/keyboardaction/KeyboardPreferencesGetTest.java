package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.testharness.HomePlace;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class KeyboardPreferencesGetTest {

    public static final String ACTION_ID = "ACTION";
    @Inject
    private
    KeyboardPreferences preferences;

    private PlaceContext homeContext = new PlaceContext(HomePlace.class, Sets.<String>newHashSet());

    @Before
    public void setUp() throws Exception {
        preferences.bind(ACTION_ID, new Relevance().addDefaultKeys(KeyStroke.ESC));
    }

    @Test
    public void testName() throws Exception {
        List<String> actionIds = preferences.get(homeContext, KeyStroke.ESC);
        assertEquals(ACTION_ID, actionIds.get(0));
    }
}
