package com.googlecode.fspotcloud.client.useraction.slideshow;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class SlideshowActions {

    private final Resources RESOURCES;


    public final ActionUIDef slideshow_start;
    public final ActionUIDef slideshow_stop;
    public final ActionUIDef slideshow_pause;
    public final ActionUIDef slideshow_slower;
    public final ActionUIDef slideshow_faster;

    @Inject
    public SlideshowActions(Resources resources) {
        RESOURCES = resources;
        slideshow_faster = new ActionUIDef("faster",
                "Faster",
                "Makes the slideshow go faster",
                RESOURCES.fasterIcon());
        slideshow_slower = new ActionUIDef("slower",
                "Slower",
                "Makes the slideshow go slower",
                RESOURCES.slowerIcon());
        slideshow_pause = new ActionUIDef("pause",
                "Pause",
                "Pause slideshow",
                RESOURCES.pauseIcon());
        slideshow_stop = new ActionUIDef("stop",
                "Stop",
                "Stop slideshow",
                RESOURCES.stopIcon());
        slideshow_start = new ActionUIDef("play",
                "Play",
                "Start slideshow",
                RESOURCES.playIcon());
    }
}
