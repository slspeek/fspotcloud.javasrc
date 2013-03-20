package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(JukitoRunner.class)
public class ActionManagerTest {

    @Inject private ActionManager actionManager;
    @Inject private ActionHandlerRegistry registry;
    @Inject private IActionHandler handler;

    @Test
    public void testOnEvent() throws Exception {
        registry.putAction("1", handler);
        actionManager.onEvent(new KeyboardActionEvent("1"));
        verify(handler).performAction("1");
    }

    @Test
    public void testOnEventNotFound() throws Exception {
        //registry.putAction("1", handler);
        actionManager.onEvent(new KeyboardActionEvent("1"));
        verifyZeroInteractions(handler);
    }
}
