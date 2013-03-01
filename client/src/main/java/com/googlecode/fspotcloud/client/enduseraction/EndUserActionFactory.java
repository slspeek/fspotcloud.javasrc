package com.googlecode.fspotcloud.client.enduseraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.about.AboutBinder;
import com.googlecode.fspotcloud.client.enduseraction.application.ApplicationBinder;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardBinder;
import com.googlecode.fspotcloud.client.enduseraction.group.GroupBinder;
import com.googlecode.fspotcloud.client.enduseraction.navigation.NavigationBinder;
import com.googlecode.fspotcloud.client.enduseraction.raster.RasterBinder;
import com.googlecode.fspotcloud.client.enduseraction.slideshow.SlideshowBinder;
import com.googlecode.fspotcloud.client.enduseraction.user.UserBinder;
import com.googlecode.fspotcloud.keyboardaction.IModeController;
import com.googlecode.fspotcloud.keyboardaction.UIRegistrationBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EndUserActionFactory implements UIRegistrationBuilder {

    private final IModeController modeController;
    private final Logger log = Logger.getLogger(EndUserActionFactory.class.getName());

    @Inject
    public EndUserActionFactory(AboutBinder aboutBinder,
                                ApplicationBinder applicationBinder,
                                NavigationBinder navigationBinder,
                                RasterBinder rasterBinder,
                                SlideshowBinder slideshowBinder,
                                DashboardBinder dashboardBinder,
                                UserBinder userBinder,
                                GroupBinder groupBinder,
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
        userBinder.build();
        groupBinder.build();
    }

    @Override
    public void build() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
