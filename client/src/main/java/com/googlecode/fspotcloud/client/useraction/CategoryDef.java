package com.googlecode.fspotcloud.client.useraction;

import com.google.gwt.dev.About;
import com.googlecode.fspotcloud.keyboardaction.ActionCategory;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class CategoryDef {
    
    public final ActionCategory APPLICATION;
    public final ActionCategory NAVIGATION;
    public final ActionCategory SLIDESHOW;
    public final ActionCategory RASTER;
    public final ActionCategory ABOUT;

    private final ConfigBuilder configBuilder;

    public CategoryDef(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        APPLICATION = configBuilder.createActionCategory("Application");
        NAVIGATION = configBuilder.createActionCategory("Navigation");
        SLIDESHOW = configBuilder.createActionCategory("Slideshow");
        RASTER = configBuilder.createActionCategory("Viewing raster");
        ABOUT = configBuilder.createActionCategory("About");
    }
}
