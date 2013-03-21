package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionStateEventTest extends EqualsTest<ActionStateEvent> {
    @Test
    public void testGetActionId() throws Exception {
        assertEquals("1", getOne().getActionId());
    }

    @Test
    public void testGetAcceleratorString() throws Exception {
        assertEquals("alt-4", getOne().getAcceleratorString());
    }

    @Test
    public void testGetAssociatedType() throws Exception {
        assertNotNull(getOne().getAssociatedType());
    }

    @Test
    public void testGetState() throws Exception {
        assertTrue(getOne().getState());
    }

    @Override
    protected ActionStateEvent getOne() {
        return new ActionStateEvent("1", true, "alt-4");
    }

    @Override
    protected ActionStateEvent getDifferentOne() {
        return new ActionStateEvent("1", true, "alt-5");
    }
}
