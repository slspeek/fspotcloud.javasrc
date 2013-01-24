package com.googlecode.fspotcloud.client.useraction.slideshow;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class SlideshowActions {

    private  final Resources RESOURCES;


    public  final ActionDef slideshow_start;
    public  final ActionDef slideshow_stop;
    public  final ActionDef slideshow_pause;
    public  final ActionDef slideshow_slower;
    public  final ActionDef slideshow_faster;

    @Inject
    public SlideshowActions(Resources resources) {
        RESOURCES = resources;
        slideshow_faster = new ActionDef("faster",
                "Faster",
                "Makes the slideshow go faster",
                RESOURCES.fasterIcon());
        slideshow_slower = new ActionDef("slower",
                "Slower",
                "Makes the slideshow go slower",
                RESOURCES.slowerIcon());
        slideshow_pause = new ActionDef("pause",
                "Pause",
                "Pause slideshow",
                RESOURCES.pauseIcon());
        slideshow_stop = new ActionDef("stop",
                "Stop",
                "Stop slideshow",
                RESOURCES.stopIcon());
        slideshow_start = new ActionDef("play",
                "Play",
                "Start slideshow",
                RESOURCES.playIcon());
    }
}
