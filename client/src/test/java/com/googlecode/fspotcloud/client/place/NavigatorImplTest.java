package com.googlecode.fspotcloud.client.place;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class NavigatorImplTest {


    @Inject
    private IPlaceController placeController;
    @Inject
    private  DataManager dataManager;
    @Inject
    private  PlaceCalculator placeCalculator;
    @Inject
    private  IClientLoginManager clientLoginManager;

    @Inject
    private NavigatorImpl navigator;


    @Test
    public void testGoAsync() throws Exception {
        navigator.goAsync(Navigator.Direction.FORWARD, Navigator.Unit.SINGLE);
    }


}
