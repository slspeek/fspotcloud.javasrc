package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.about.AboutBinder;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationBinder;
import com.googlecode.fspotcloud.client.useraction.dashboard.DashboardBinder;
import com.googlecode.fspotcloud.client.useraction.dashboard.DashboardLateBinder;
import com.googlecode.fspotcloud.client.useraction.navigation.NavigationBinder;
import com.googlecode.fspotcloud.client.useraction.raster.RasterBinder;
import com.googlecode.fspotcloud.client.useraction.slideshow.SlideshowBinder;
import com.googlecode.fspotcloud.keyboardaction.IModeController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserActionFactory {

    private final IModeController modeController;
    private final Logger log = Logger.getLogger(UserActionFactory.class.getName());

    @Inject
    public UserActionFactory(AboutBinder aboutBinder,
                             ApplicationBinder applicationBinder,
                             NavigationBinder navigationBinder,
                             RasterBinder rasterBinder,
                             SlideshowBinder slideshowBinder,
                             DashboardBinder dashboardBinder,
                             IModeController modeController) {
        log.log(Level.FINEST, "In constructor before doing anything");
        this.modeController = modeController;
        modeController.setMode(Modes.TAG_VIEW);
        aboutBinder.build();
        applicationBinder.build();
        navigationBinder.build();
        rasterBinder.build();
        slideshowBinder.build();
        dashboardBinder.build();
    }

}
