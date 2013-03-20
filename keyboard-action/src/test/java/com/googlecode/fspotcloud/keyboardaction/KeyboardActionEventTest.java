package com.googlecode.fspotcloud.keyboardaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyboardActionEventTest {

    private KeyboardActionEvent event = new KeyboardActionEvent("foo");
    @Test
    public void testGetActionId() throws Exception {
        final String actionId = event.getActionId();
        assertEquals(actionId, event.getActionId());
    }

    @Test
    public void testGetType() throws Exception {
        final String actionId = event.getActionId();
        assertEquals(KeyboardActionEvent.TYPE, event.getAssociatedType());
    }
}
