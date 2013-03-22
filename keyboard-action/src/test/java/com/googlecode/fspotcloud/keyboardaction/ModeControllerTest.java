package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.testharness.HomePlace;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class ModeControllerTest {

    public static final String FOO = "foo";
    @Inject
    private ModeController modeController;

    @Inject
    private IPlaceController placeController;

    @Inject
    private KeyboardPreferences keyboardPreferences;

    @Inject
    private WidgetRegistry widgetRegistry;

    @Inject
    private ActionWidget actionWidgetA;

    @Inject
    private ArgumentCaptor<ActionStateEvent> eventArgumentCaptor;

    private final String ACTION_ID = FOO;

    private final Place TO_BE = new HomePlace();

    @Before
    public void setUp() throws Exception {
        final Relevance relevance = new Relevance();
        relevance.addDefaultKeys(KeyStroke.K);
        keyboardPreferences.bind(ACTION_ID, relevance);
        widgetRegistry.add(ACTION_ID, actionWidgetA);
        when(placeController.getWhere()).thenReturn(TO_BE);
    }

    @Test
    public void testInitButtonEnableStates() throws Exception {
        modeController.initButtonEnableStates();
        verify(actionWidgetA).onEvent(eventArgumentCaptor.capture());
        final ActionStateEvent actionStateEvent = eventArgumentCaptor.getValue();
        assertTrue(actionStateEvent.getState());
    }

    @Test
    public void testGetFlags() throws Exception {
        modeController.setFlag(FOO);
        assertEquals(newHashSet(FOO), modeController.getFlags());
    }

    @Test
    public void testUnsetFlag() throws Exception {
        testGetFlags();
        modeController.unsetFlag(FOO);
        assertEquals(0, modeController.getFlags().size());
    }

    @Test
    public void testSetFlag() throws Exception {
        modeController.setFlag(FOO, true);
        modeController.setFlag(FOO, false);
        assertEquals(0, modeController.getFlags().size());
    }

    @Test
    public void testClearFlags() throws Exception {
        modeController.setFlag(FOO, true);
        modeController.clearFlags();
        assertEquals(0, modeController.getFlags().size());
    }

    @Test
    public void testSetFlags() throws Exception {
        Map<String, Boolean> flags = newHashMap();
        flags.put("Foo", true);
        flags.put("Bar", true);
        modeController.setFlags(flags);
        assertEquals(2, modeController.getFlags().size());
    }

}
