package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.testharness.HomePlace;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(JukitoRunner.class)
public class KeyboardPreferencesTest {

    public static final String ACTION_ID = "ACTION";

    @Inject
    private KeyboardPreferences preferences;

    private PlaceContext homeContext = new PlaceContext(HomePlace.class, Sets.<String>newHashSet());

    @Test
    public void testIsRelevantShouldBeFalse() throws Exception {
        assertFalse(preferences.isRelevant(ACTION_ID, homeContext));
    }

    @Test
    public void testGetKeysForAction() throws Exception {
        final List<KeyStroke> keysForAction = preferences.getKeysForAction(homeContext, ACTION_ID);
        assertEquals(0, keysForAction.size());
    }

    @Test
    public void testAllActions() throws Exception {
        assertEquals(0, preferences.allActions().size());
    }

    @Test
    public void testGetDefaultKeysForAction() throws Exception {
        assertEquals(0, preferences.getDefaultKeysForAction(ACTION_ID).size());
    }

    @Test
    public void testAllRelevantActions() throws Exception {
        assertEquals(0, preferences.allRelevantActions(homeContext).size());
    }
}
