package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ActionDemoEventEqualsTest extends EqualsTest<ActionDemoEvent> {
    @Test
    public void testGetActionId() throws Exception {
        assertEquals("Foo", getOne().getActionId());
    }

    @Test
    public void testGetAssociatedType() throws Exception {
        assertNotNull(getOne().getAssociatedType());
    }

    @Test
    public void testDispatch() throws Exception {
       getOne().dispatch(new IActionDemoHandler() {
           @Override
           public void onEvent(ActionDemoEvent event) {

           }
       });
    }

    @Test
    public void testGetState() throws Exception {
        assertEquals(true,  getOne().getState());
    }

    @Override
    protected ActionDemoEvent getOne() {
        return new ActionDemoEvent("Foo", true);
    }

    @Override
    protected ActionDemoEvent getDifferentOne() {
        return new ActionDemoEvent("Foo", false);
    }
}
