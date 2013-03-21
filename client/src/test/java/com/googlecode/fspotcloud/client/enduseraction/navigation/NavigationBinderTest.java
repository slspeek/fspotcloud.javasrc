package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardPreferences;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class NavigationBinderTest {

    @Inject
    private NavigationBinder binder;

    @Inject
    KeyboardPreferences keyboardPreferences;

    @Test
    public void testBuild() throws Exception {
        binder.build();
        List<KeyStroke> keys = keyboardPreferences.getDefaultKeysForAction(NavigationActions.HOME_ID);
        assertEquals(2, keys.size());
    }

}
