package com.googlecode.fspotcloud.client.useraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.LoadNewLocation;

public class ProtonHandler extends LoadNewLocationAction {

    @Inject
    private ProtonHandler(LoadNewLocation loader) {
        super(loader, "http://www.protonradio.com/player/live/player.php");
    }
}
