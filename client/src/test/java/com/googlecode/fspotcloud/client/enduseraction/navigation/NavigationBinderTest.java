package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardPreferences;
import com.googlecode.fspotcloud.keyboardaction.PlaceContext;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
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
