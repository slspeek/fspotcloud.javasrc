package com.googlecode.fspotcloud.keyboardaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionCategoryEmptyTest {

    private ActionCategory category = new ActionCategory("empty");
    @Test
    public void testGetName() throws Exception {
        assertEquals("empty", category.getName());
    }

    @Test
    public void testGetActions() throws Exception {
        assertEquals(0, category.getActions().size());
    }
}
