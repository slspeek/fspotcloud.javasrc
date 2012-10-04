package com.googlecode.fspotcloud.client.useraction;

import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.useraction.handler.application.LogoutHandler;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class ApplicationFactory {

    private final Resources resources;
    private final LogoutHandler logoutHandler;
    private final ConfigBuilder configBuilder;


    public ApplicationFactory(Resources resources, LogoutHandler logoutHandler, ConfigBuilder configBuilder) {
        this.resources = resources;
        this.logoutHandler = logoutHandler;
        this.configBuilder = configBuilder;
    }

    public void build() {


    }
}
