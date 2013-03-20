package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class ActionManagerTest {

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(ActionHandlerRegistry.class).in(Singleton.class);
        }
    }


    @Inject private ActionManager actionManager;
    @Inject private ActionHandlerRegistry registry;
    @Inject private IActionHandler handler;

    @Test
    public void testOnEvent() throws Exception {
        registry.putAction("1", handler);
        actionManager.onEvent(new KeyboardActionEvent("1"));
        verify(handler).performAction("1");

    }
}
