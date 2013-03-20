package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.testharness.MainBuilder;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class ActionDemoStepTest {

    private SimpleEventBus simpleEventBus = new SimpleEventBus();
    private ActionDemoStep step = new ActionDemoStep(MainBuilder.LOGIN_DEF, simpleEventBus , 3000, new SafeHtml() {
        @Override
        public String asString() {
            return "";
        }
    });

    @Inject private IActionManager actionManager ;
    @Inject private ArgumentCaptor<KeyboardActionEvent> eventCaptor;

    @Before
    public void setUp() throws Exception {
        simpleEventBus.addHandler(KeyboardActionEvent.TYPE, actionManager);
    }

    @Test
    public void testGetActionId() throws Exception {
       assertEquals(MainBuilder.LOGIN_DEF.getId(), step.getActionId());
    }

    @Test
    public void testGetAction() throws Exception {
        Runnable runnable = step.getAction();
        runnable.run();
        verify(actionManager).onEvent(eventCaptor.capture());
        KeyboardActionEvent event = eventCaptor.getValue();
        assertEquals(MainBuilder.LOGIN_DEF.getId(), event.getActionId());
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
