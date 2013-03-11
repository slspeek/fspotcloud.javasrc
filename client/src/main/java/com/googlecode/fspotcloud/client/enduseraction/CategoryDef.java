package com.googlecode.fspotcloud.client.enduseraction;

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
    public final ActionCategory USERGROUP;

    @Inject
    public CategoryDef(ConfigBuilder configBuilder) {
        APPLICATION = configBuilder.createCategory("Application");
        NAVIGATION = configBuilder.createCategory("Navigation");
        SLIDESHOW = configBuilder.createCategory("Slideshow");
        RASTER = configBuilder.createCategory("Viewing raster");
        ABOUT = configBuilder.createCategory("About");
        DASHBOARD = configBuilder.createCategory("Dashboard");
        USER = configBuilder.createCategory("User");
        USERGROUP = configBuilder.createCategory("Usergroup");
    }
}
