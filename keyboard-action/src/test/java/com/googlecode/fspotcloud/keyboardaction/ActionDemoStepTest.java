package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.googlecode.fspotcloud.testharness.MainBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionDemoStepTest {

    private ActionDemoStep step = new ActionDemoStep(MainBuilder.LOGIN_DEF, new SimpleEventBus(), 3000, new SafeHtml() {
        @Override
        public String asString() {
            return "";
        }
    });
    @Test
    public void testGetActionId() throws Exception {
       assertEquals(MainBuilder.LOGIN_DEF.getId(), step.getActionId());
    }

    @Test
    public void testGetAction() throws Exception {
        Runnable runnable = step.getAction();
        runnable.run();
    }

    @Test
    public void testPauseTime() throws Exception {
        assertEquals(3000, step.pauseTime());
    }

    @Test
    public void testGetContent() throws Exception {
        assertEquals("", step.getContent().asString());
    }
}
