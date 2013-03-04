package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.googlecode.fspotcloud.client.enduseraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.*;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class NavigationBinderTest {

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {

        }

        @Provides
        ModesProvider get() {
            return new SimpleModesProvider(Modes.ALL_MODES);
        }
    }

    @Inject
    private NavigationBinder binder;

    @Inject
    KeyboardPreferences keyboardPreferences;

    @Test
    public void testBuild() throws Exception {
        binder.build();
        KeyStroke[] keys = keyboardPreferences.getDefaultKeysForAction(NavigationActions.HOME_ID);
        assertEquals(1, keys.length);
        assertEquals(KeyCodes.KEY_HOME, keys[0].getKeyCode());
    }
}
