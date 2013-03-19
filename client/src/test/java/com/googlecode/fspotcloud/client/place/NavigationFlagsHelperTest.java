package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IModeController;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(JukitoRunner.class)
public class NavigationFlagsHelperTest {

    @Inject
    private ArgumentCaptor<AsyncCallback<Boolean>> booleanCaptor;
    @Inject
    private NavigationFlagsHelper helper;
    @Inject
    private Navigator navigator;
    @Inject
    private IModeController modeController;

    @Test
    public void testUpdate() throws Exception {
        helper.update();

        verify(navigator, times(8)).canGoAsync(
                any(Navigator.Direction.class),
                any(Navigator.Unit.class),
                booleanCaptor.capture());

        List<AsyncCallback<Boolean>> callbacks = booleanCaptor.getAllValues();

        callbacks.get(0).onSuccess(true);
        verify(modeController).setFlag(Flags.CAN_GO_NEXT_IMAGE.name(), true);
    }

    @Test
    public void testUpdateFailure() throws Exception {
        helper.update();

        verify(navigator, times(8)).canGoAsync(
                any(Navigator.Direction.class),
                any(Navigator.Unit.class),
                booleanCaptor.capture());

        List<AsyncCallback<Boolean>> callbacks = booleanCaptor.getAllValues();

        callbacks.get(0).onFailure(new RuntimeException());
        verifyZeroInteractions(modeController);
    }
}
