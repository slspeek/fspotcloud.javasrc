package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.inject.Inject;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(JukitoRunner.class)
public class NavigationActionsTest {

    @Inject private NavigationActions navigationActions;

    @Test
    public void testNotNull() throws Exception {
        assertNotNull(navigationActions.back);


    }
}
