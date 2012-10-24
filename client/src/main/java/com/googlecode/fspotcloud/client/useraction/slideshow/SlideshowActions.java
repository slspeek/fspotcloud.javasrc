package com.googlecode.fspotcloud.client.useraction.slideshow;

import com.google.gwt.core.client.GWT;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class SlideshowActions {

    private static final Resources RESOURCES = GWT.create(Resources.class);

    public static final ActionDef SLIDESHOW_START = new ActionDef("play",
            "Play",
            "Start slideshow",
            RESOURCES.playIcon());

    public static final ActionDef SLIDESHOW_STOP = new ActionDef("stop",
            "Stop",
            "Stop slideshow",
            RESOURCES.stopIcon());

    public static final ActionDef SLIDESHOW_PAUSE = new ActionDef("pause",
            "Pause",
            "Pause slideshow",
            RESOURCES.pauseIcon());
    public static final ActionDef SLIDESHOW_SLOWER = new ActionDef("slower",
            "Slower",
            "Makes the slideshow go slower",
            RESOURCES.slowerIcon());

    public static final ActionDef SLIDESHOW_FASTER = new ActionDef("faster",
            "Faster",
            "Makes the slideshow go faster",
            RESOURCES.fasterIcon());

}
