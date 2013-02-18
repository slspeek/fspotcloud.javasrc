package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionCategory;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class CategoryDef {

    public final ActionCategory APPLICATION;
    public final ActionCategory NAVIGATION;
    public final ActionCategory SLIDESHOW;
    public final ActionCategory RASTER;
    public final ActionCategory ABOUT;
    public final ActionCategory DASHBOARD;
    public final ActionCategory USER;

    @Inject
    public CategoryDef(ConfigBuilder configBuilder) {
        APPLICATION = configBuilder.createActionCategory("Application");
        NAVIGATION = configBuilder.createActionCategory("Navigation");
        SLIDESHOW = configBuilder.createActionCategory("Slideshow");
        RASTER = configBuilder.createActionCategory("Viewing raster");
        ABOUT = configBuilder.createActionCategory("About");
        DASHBOARD = configBuilder.createActionCategory("Dashboard");
        USER = configBuilder.createActionCategory("User");
    }
}
